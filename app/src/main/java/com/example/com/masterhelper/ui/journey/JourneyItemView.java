package com.example.com.masterhelper.ui.journey;

import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import com.example.com.masterhelper.core.appconfig.GlobalApplication;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.appconfig.models.JourneyModel;
import com.example.com.masterhelper.core.factorys.DBAdapters.AdaptersType;
import com.example.com.masterhelper.core.factorys.DBAdapters.DBAdapterFactory;
import com.example.com.masterhelper.core.factorys.DBAdapters.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.core.factorys.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factorys.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.ui.scene.Scene;
import com.example.com.masterhelper.core.factorys.list.CustomListItemsEnum;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.core.factorys.list.ListFactory;
import com.example.com.masterhelper.core.appconfig.models.SceneModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.factorys.DBAdapters.adapters.SceneDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.LinkedHashMap;

import static android.content.DialogInterface.BUTTON_POSITIVE;


public class JourneyItemView extends AppCompatActivity implements ICommonItemEvents {
  /** кнопка создания новой сцены */
  FloatingActionButton createNewSceneBtn;

  /** хелпер для управления данными по прикобчениям в базе */
  JourneyDBAdapter journeyDBAdapter = (JourneyDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.journey);

  /** хелпер для управления данными по сценам в базе */
  SceneDBAdapter sceneDBAdapter = (SceneDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.scene);

  JourneyModel currentJourney;

  /** временный кеш по списку сцен */
  LinkedHashMap<Integer, SceneModel> scenesList = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project_view_screen);
    int journeyId = getIntent().getIntExtra("id", -1);
    if(journeyId == -1){
      setResult(RESULT_CANCELED);
      finish();
    }
    ActionBar toolbar = getSupportActionBar();
    currentJourney = journeyDBAdapter.get(journeyId);
    // получаем указатель на тулбар активированного в главном компоненте
    if(toolbar != null && currentJourney != null){
      toolbar.setTitle(currentJourney.getTitle());
      toolbar.setSubtitle(R.string.scene_list);
    }
    

    createNewSceneBtn = findViewById(R.id.CREATE_NEW_SCENE_BTN_ID);
    createNewSceneBtn.setOnClickListener(v -> onCreateButtonPressed());
  }

  @Override
  protected void onStart() {
    super.onStart();
    updateScenesList();
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onResume() {
    super.onResume();
    GlobalApplication.getBackgroundMediaPlayer().stopMediaList();
  }

  /** обновить вьюху по списку сцен */
  void updateScenesList(){
    scenesList = sceneDBAdapter.getListByParentId(currentJourney.getId());
    FragmentManager fm = getSupportFragmentManager();
    ListFactory lsf = (ListFactory) fm.findFragmentById(R.id.SCREEN_FRAGMENT_ID);

    if(lsf != null && lsf.getView() != null){
      lsf.updateListAdapter(scenesList, CustomListItemsEnum.scene);
    }
  }

  /** вызвать диалог создания сцены */
  public void onCreateButtonPressed() {
    Intent intent = new Intent(this, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.TITLE, R.string.scene_create_title);
    startActivityForResult(intent, 1);
  }

  /** вызвать диалог редактирования сцены */
  public void onUpdateScreenNameButtonPressed(int id) {
    SceneModel scene = sceneDBAdapter.get(id);
    if(scene != null){
      Intent intent = new Intent(this, CreateNewItemDialog.class);
      intent.putExtra(CreateNewItemDialog.TITLE, R.string.screen_name_scene_update);
      intent.putExtra(CreateNewItemDialog.IS_UPDATE, 1);
      intent.putExtra(CreateNewItemDialog.ID, id);
      intent.putExtra(CreateNewItemDialog.OLD_NAME, scene.getTitle());
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, scene.getDescription());
      startActivityForResult(intent, 2);
    }
  }

  /** метод обработки результата от диалогов создания и редактирования сцен */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);
    if(resultCode != RESULT_OK){
      return;
    }
    String newName = result.getStringExtra(CreateNewItemDialog.NAME);
    String newDescription = result.getStringExtra(CreateNewItemDialog.DESCRIPTION);

    int id = result.getIntExtra(CreateNewItemDialog.ID, 0);
    if(newName != null && newName.trim().length() == 0){
      return;
    }
    SceneModel scene;
    if(id > 0) {
      scene = sceneDBAdapter.get(id);

    } else {
      scene = new SceneModel(newName);
    }
    scene.setTitle(newName);
    scene.setDescription(newDescription);

    switch (requestCode){
      case 1:
        sceneDBAdapter.add(scene, currentJourney.getId());
        break;
      case 2:
        sceneDBAdapter.update(scene);
        break;
    }
    updateScenesList();
  }

  /** обработчик кнопок сцены */
  @Override
  public void onClick(View elementFiredAction, int position) {
    SceneModel currentData = (SceneModel) scenesList.values().toArray()[position];
    int btnId = elementFiredAction.getId();
    switch (btnId){
      case R.id.SCENE_START_BTN_ID:
        Intent intent = new Intent(this, Scene.class);
        intent.putExtra("sceneId", currentData.getId());
        intent.putExtra("sceneName", currentData.getTitle());
        intent.putExtra("journeyName", currentJourney.getTitle());
        startActivity(intent);
        break;
      case R.id.SCENE_DELETE_BTN_ID:
        CommonDialog dialog = DialogsFactory.createDialog(DialogTypes.delete);
        if(dialog != null){
          dialog.setOnResolveListener((dialogInterface, id) -> {
            if(id == BUTTON_POSITIVE){
              sceneDBAdapter.delete(currentData.getId());
              updateScenesList();
            }
          });
          dialog.show(this);
        }
        break;
      case R.id.SCENE_EDIT_BTN_ID:
          onUpdateScreenNameButtonPressed(currentData.getId());
    }
  }
}

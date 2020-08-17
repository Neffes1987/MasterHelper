package com.example.com.masterhelper.journey.ui;

import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.factories.dialogs.ui.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.example.com.masterhelper.journey.models.JourneyModel;
import com.example.com.masterhelper.core.factories.DBAdapters.AdaptersType;
import com.example.com.masterhelper.core.factories.DBAdapters.DBAdapterFactory;
import com.example.com.masterhelper.journey.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.core.factories.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factories.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factories.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.scene.Scene;
import com.example.com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.listFactory.ListFactory;
import com.example.com.masterhelper.core.models.SceneModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.factories.DBAdapters.adapters.SceneDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.example.com.masterhelper.core.factories.dialogs.DialogTypes.withDescription;


public class JourneyItemView extends AppCompatActivity implements ICommonItemEvents {
  /** кнопка создания новой сцены */
  FloatingActionButton createNewSceneBtn;

  /** хелпер для управления данными по прикобчениям в базе */
  JourneyDBAdapter journeyDBAdapter = new JourneyDBAdapter();

  /** хелпер для управления данными по сценам в базе */
  SceneDBAdapter sceneDBAdapter = (SceneDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.scene);

  JourneyModel currentJourney;

  /** временный кеш по списку сцен */
  ModelList scenesList = new ModelList();

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
      toolbar.setTitle(currentJourney.getName());
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
    }
  }

  /** вызвать диалог создания сцены */
  public void onCreateButtonPressed() {
    CommonDialog dialog = DialogsFactory.createDialog(withDescription);
    if(dialog != null){
      dialog.setTitle(R.string.scene_create_title);
      dialog.show(this, null);
    }
  }

  /** вызвать диалог редактирования сцены */
  public void onUpdateScreenNameButtonPressed(int id) {
    SceneModel scene = sceneDBAdapter.get(id);
    CommonDialog dialog = DialogsFactory.createDialog(withDescription);
    if(dialog != null){
      dialog.setTitle(R.string.screen_name_scene_update);
      dialog.show(this, scene);
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
    scene.setName(newName);
    scene.setDescription(newDescription);

    switch (requestCode){
      case CommonDialog.DIALOG_CREATE_ACTIVITY_RESULT:
        sceneDBAdapter.add(scene, currentJourney.getId());
        break;
      case CommonDialog.DIALOG_UPDATE_ACTIVITY_RESULT:
        sceneDBAdapter.update(scene);
        break;
    }
    updateScenesList();
  }

  /** обработчик кнопок сцены */
  @Override
  public void onClick(View elementFiredAction, int position) {
    SceneModel currentData = (SceneModel) scenesList.getItemByPosition(position);
    int btnId = elementFiredAction.getId();
    switch (btnId){
      case R.id.SCENE_START_BTN_ID:
        Intent intent = new Intent(this, Scene.class);
        intent.putExtra("sceneId", currentData.getId());
        intent.putExtra("sceneName", currentData.getName());
        intent.putExtra("journeyName", currentJourney.getName());
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

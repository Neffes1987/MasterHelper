package com.example.masterhelper.ui.journey;

import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import com.example.masterhelper.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.masterhelper.dialogsFactory.DialogTypes;
import com.masterhelper.dialogsFactory.DialogsFactory;
import com.masterhelper.dialogsFactory.dialogs.CommonDialog;
import com.masterhelper.mediaworker.BackgroundMediaPlayer;
import com.example.masterhelper.ui.scene.Scene;
import com.masterhelper.listFactory.CustomListItemsEnum;
import com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.masterhelper.listFactory.ListFactory;
import com.example.masterhelper.models.SceneRecycleDataModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.scene.SceneDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.LinkedHashMap;

import static android.content.DialogInterface.BUTTON_POSITIVE;


public class JourneyItemView extends AppCompatActivity implements ICommonItemEvents {
  /** кнопка создания новой сцены */
  FloatingActionButton createNewSceneBtn;

  /** хелпер для управления данными по прикобчениям в базе */
  JourneyDBAdapter journeyDBAdapter;

  /** хелпер для управления данными по сценам в базе */
  SceneDBAdapter sceneDBAdapter;

  /** временный кеш по списку сцен */
  LinkedHashMap<Integer, SceneRecycleDataModel> scenesList = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project_view_screen);
    int journeyId = getIntent().getIntExtra("id", -1);
    if(journeyId == -1){
      setResult(RESULT_CANCELED);
      finish();
    }
    journeyDBAdapter  = new JourneyDBAdapter(journeyId);
    sceneDBAdapter  = new SceneDBAdapter();
    ActionBar toolbar = getSupportActionBar();

    // получаем указатель на тулбар активированного в главном компоненте
    if(toolbar != null){
      toolbar.setTitle(journeyDBAdapter.getJourneyTitle());
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
    BackgroundMediaPlayer.getInstance().stopMediaList();
  }

  /** обновить вьюху по списку сцен */
  void updateScenesList(){
    scenesList = sceneDBAdapter.getScenesList(journeyDBAdapter.getJourneyId());
    FragmentManager fm = getSupportFragmentManager();
    ListFactory<SceneRecycleDataModel> lsf = (ListFactory<SceneRecycleDataModel>) fm.findFragmentById(R.id.SCREEN_FRAGMENT_ID);

    if(lsf != null && lsf.getView() != null){
      lsf.setItemType(CustomListItemsEnum.scene);
      lsf.updateListAdapter(scenesList);
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
    SceneRecycleDataModel scene = sceneDBAdapter.getSceneById(id);
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
    SceneRecycleDataModel scene;
    if(id > 0) {
      scene = sceneDBAdapter.getSceneById(id);

    } else {
      scene = new SceneRecycleDataModel(newName);
    }
    scene.setTitle(newName);
    scene.setDescription(newDescription);

    switch (requestCode){
      case 1:
        sceneDBAdapter.addNewScene(scene, journeyDBAdapter.getJourneyId());
        break;
      case 2:
        sceneDBAdapter.updateScene(scene, id);
        break;
    }
    updateScenesList();
  }

  /** обработчик кнопок сцены */
  @Override
  public void onClick(View elementFiredAction, int position) {
    SceneRecycleDataModel currentData = (SceneRecycleDataModel) scenesList.values().toArray()[position];
    int btnId = elementFiredAction.getId();
    switch (btnId){
      case R.id.SCENE_START_BTN_ID:
        Intent intent = new Intent(this, Scene.class);
        intent.putExtra("sceneId", currentData.getId());
        intent.putExtra("sceneName", currentData.getTitle());
        intent.putExtra("journeyName", journeyDBAdapter.getJourneyTitle());
        startActivity(intent);
        break;
      case R.id.SCENE_DELETE_BTN_ID:
        CommonDialog dialog = DialogsFactory.createDialog(DialogTypes.delete);
        if(dialog != null){
          dialog.setOnResolveListener((dialogInterface, id) -> {
            if(id == BUTTON_POSITIVE){
              sceneDBAdapter.deleteScene(currentData.getId());
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

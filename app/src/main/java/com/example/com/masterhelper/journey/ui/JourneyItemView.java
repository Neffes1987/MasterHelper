package com.example.com.masterhelper.journey.ui;

import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.components.buttons.ComponentFloatButtonPrimary;
import com.example.com.masterhelper.core.components.dialogs.dialogs.DeleteDialog;
import com.example.com.masterhelper.core.components.dialogs.dialogs.InputDialog;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.CommonItem;
import com.example.com.masterhelper.scene.ui.SceneItem;
import com.example.masterhelper.R;
import com.example.com.masterhelper.journey.models.JourneyModel;
import com.example.com.masterhelper.journey.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.scene.ui.Scene;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.core.listFactory.ListFactory;
import com.example.com.masterhelper.scene.models.SceneModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.scene.adapters.SceneDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class JourneyItemView extends AppCompatActivity implements ICommonItemEvents {
  /** кнопка создания новой сцены */
  ComponentFloatButtonPrimary createNewSceneBtn;

  /** хелпер для управления данными по прикобчениям в базе */
  JourneyDBAdapter journeyDBAdapter = new JourneyDBAdapter();

  /** хелпер для управления данными по сценам в базе */
  SceneDBAdapter sceneDBAdapter = new SceneDBAdapter();

  JourneyModel currentJourney;

  CommonAdapter listAdapter;

  InputDialog inputDialog;
  DeleteDialog deleteDialog;

  private void setDialogs(){
    inputDialog = new InputDialog(this, getSupportFragmentManager());
    deleteDialog = new DeleteDialog(this);
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_project_view_screen);
    int journeyId = getIntent().getIntExtra("id", -1);
    if (journeyId == -1) {
      setResult(RESULT_CANCELED);
      finish();
    }
    ActionBar toolbar = getSupportActionBar();
    currentJourney = journeyDBAdapter.get(journeyId);
    // получаем указатель на тулбар активированного в главном компоненте
    if (toolbar != null && currentJourney != null) {
      toolbar.setTitle(currentJourney.getName());
      toolbar.setSubtitle(R.string.scene_list);
    }


    View createNewSceneBtnWrapper = findViewById(R.id.CREATE_NEW_SCENE_BTN_ID);
    createNewSceneBtn = new ComponentFloatButtonPrimary(createNewSceneBtnWrapper);
    createNewSceneBtn.setListener(v -> onCreateButtonPressed());
    setDialogs();
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

  public CommonItem getCommonItemInstance(CommonAdapter adapter) {
    SceneItem item = new SceneItem();
    item.attachAdapter(adapter);
    return item;
  }

  /** обновить вьюху по списку сцен */
  void updateScenesList(){
    FragmentManager fm = getSupportFragmentManager();
    listAdapter = new CommonAdapter(sceneDBAdapter.getListByParentId(currentJourney.getId()), R.layout.fragment_view_list_item_scene_list, this);
    ListFactory lsf = (ListFactory) fm.findFragmentById(R.id.SCREEN_FRAGMENT_ID);
    listAdapter.setCommonItemInstanceGetter(this::getCommonItemInstance);
    if(lsf != null && lsf.getView() != null){
      lsf.setAdapter(listAdapter);
    }
  }

  /** вызвать диалог создания сцены */
  public void onCreateButtonPressed() {
    inputDialog.setOnResolveListener((dialog, whitch) -> {
      SceneModel scene = new SceneModel(inputDialog.getName());
      scene.setDescription(inputDialog.getDescription());
      int itemId = sceneDBAdapter.add(scene, currentJourney.getId());
      scene.setId(itemId);
      listAdapter.addItem(scene,true);
    });
    inputDialog.setTitle(R.string.scene_create_title);
    inputDialog.show();
  }

  /** вызвать диалог редактирования сцены */
  public void onUpdateScreenNameButtonPressed(int id) {
    SceneModel scene = sceneDBAdapter.get(id);
    inputDialog.setOnResolveListener((dialog, whitch) -> {
      scene.setName(inputDialog.getName());
      scene.setDescription(inputDialog.getDescription());
      sceneDBAdapter.update(scene);
      listAdapter.updateItem(scene);
    });
    inputDialog.setTitle(R.string.scene_create_title);
    inputDialog.show(scene);
  }

  /** обработчик кнопок сцены */
  @Override
  public void onClick(View elementFiredAction, int itemId) {
    SceneModel currentData = (SceneModel) listAdapter.getItemById(itemId);
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
        deleteDialog.setOnResolveListener((dialogInterface, id) -> {
          if(id == BUTTON_POSITIVE){
            sceneDBAdapter.delete(currentData.getId());
            listAdapter.deleteItem(currentData.getId());
          }
        });
        deleteDialog.show(this);
        break;
      case R.id.SCENE_EDIT_BTN_ID:
          onUpdateScreenNameButtonPressed(currentData.getId());
    }
  }
}

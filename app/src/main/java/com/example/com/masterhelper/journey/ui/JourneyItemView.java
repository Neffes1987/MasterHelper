package com.example.com.masterhelper.journey.ui;

import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.components.dialogs.ui.CreateNewItemDialog;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.CommonItem;
import com.example.com.masterhelper.scene.ui.SceneItem;
import com.example.masterhelper.R;
import com.example.com.masterhelper.journey.models.JourneyModel;
import com.example.com.masterhelper.journey.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.core.components.dialogs.DialogTypes;
import com.example.com.masterhelper.core.components.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.components.dialogs.dialogs.CommonDialog;
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
import static com.example.com.masterhelper.core.components.dialogs.DialogTypes.withDescription;


public class JourneyItemView extends AppCompatActivity implements ICommonItemEvents {
  /** кнопка создания новой сцены */
  FloatingActionButton createNewSceneBtn;

  /** хелпер для управления данными по прикобчениям в базе */
  JourneyDBAdapter journeyDBAdapter = new JourneyDBAdapter();

  /** хелпер для управления данными по сценам в базе */
  SceneDBAdapter sceneDBAdapter = new SceneDBAdapter();

  JourneyModel currentJourney;

  CommonAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_project_view_screen);
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
        int itemId = sceneDBAdapter.add(scene, currentJourney.getId());
        scene.setId(itemId);
        listAdapter.addItem(scene,true);
        break;
      case CommonDialog.DIALOG_UPDATE_ACTIVITY_RESULT:
        sceneDBAdapter.update(scene);
        listAdapter.updateItem(scene);
        break;
    }
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
        CommonDialog dialog = DialogsFactory.createDialog(DialogTypes.delete);
        if(dialog != null){
          dialog.setOnResolveListener((dialogInterface, id) -> {
            if(id == BUTTON_POSITIVE){
              sceneDBAdapter.delete(currentData.getId());
              listAdapter.deleteItem(currentData.getId());
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

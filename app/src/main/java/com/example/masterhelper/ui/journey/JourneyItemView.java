package com.example.masterhelper.ui.journey;

import android.view.View;
import com.example.masterhelper.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.scene.Scene;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.ui.recyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.models.SceneRecycleDataModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.scene.SceneDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.LinkedHashMap;
import java.util.Objects;


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
    journeyDBAdapter  = new JourneyDBAdapter(this, journeyId);
    sceneDBAdapter  = new SceneDBAdapter(this);

    // получаем указатель на тулбар активированного в главном компоненте
    Objects.requireNonNull(getSupportActionBar()).setTitle(journeyDBAdapter.getJourneyTitle());

    createNewSceneBtn = findViewById(R.id.CREATE_NEW_SCENE_BTN_ID);
    createNewSceneBtn.setOnClickListener(v -> onCreateButtonPressed());

    updateScenesList();
  }

  /** обновить вьюху по списку сцен */
  void updateScenesList(){
    scenesList = sceneDBAdapter.getScenesList(journeyDBAdapter.getJourneyId());
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(R.id.SCREEN_FRAGMENT_ID);

    if(lsf != null && lsf.getView() != null){
      lsf.updateSceneListAdapter(scenesList);
    }
  }

  /** вызвать диалог создания сцены */
  public void onCreateButtonPressed() {
    Intent intent = new Intent(this, CreateNewItemDialog.class);
    intent.putExtra("title", R.string.scene_create_title);
    startActivityForResult(intent, 1);
  }

  /** вызвать диалог редактирования сцены */
  public void onUpdateScreenNameButtonPressed(int id) {
    SceneRecycleDataModel scene = sceneDBAdapter.getSceneById(id);
    if(scene != null){
      Intent intent = new Intent(this, CreateNewItemDialog.class);
      intent.putExtra("title", R.string.screen_name_scene_update);
      intent.putExtra("id", id);
      intent.putExtra("oldName", scene.getTitle());
      startActivityForResult(intent, 2);
    }
  }

  /** метод обработки результата от от диалогов создания и редактирования сцен */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);
    if(resultCode != RESULT_OK){
      return;
    }
    String newName = result.getStringExtra("name");
    int id = result.getIntExtra("id", 0);
    if(newName != null && newName.trim().length() == 0){
      return;
    }
    SceneRecycleDataModel item = new SceneRecycleDataModel(newName);
    switch (requestCode){
      case 1:
        sceneDBAdapter.addNewScene(item, journeyDBAdapter.getJourneyId());
        break;
      case 2:
        sceneDBAdapter.updateScene(item, id);
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
        startActivity(intent);
        break;
      case R.id.SCENE_DELETE_BTN_ID:
        sceneDBAdapter.deleteScene(currentData.getId());
        updateScenesList();
        break;
      case R.id.SCENE_EDIT_BTN_ID:
          onUpdateScreenNameButtonPressed(currentData.getId());
    }
  }
}

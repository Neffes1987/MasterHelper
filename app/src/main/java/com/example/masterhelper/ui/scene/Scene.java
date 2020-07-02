package com.example.masterhelper.ui.scene;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.enemies.EnemiesListView;
import com.example.masterhelper.ui.scripts.ScriptTextDescriptionScreen;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.ui.recyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.models.ScriptRecycleDataModel;
import com.example.masterhelper.ui.scripts.ScriptDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedHashMap;
import java.util.Objects;

public class Scene extends AppCompatActivity implements ICommonItemEvents {
  /** хелпер для работы с таблицей скриптов в бд */
  ScriptDBAdapter scriptDBAdapter;

  /** указатель на кнопку создания нового скрипта сцены */
  FloatingActionButton newScriptBtn;

  /** ид выбранной сцены */
  int sceneId;

  /** временный кеш для списка скриптов */
  public LinkedHashMap<Integer, ScriptRecycleDataModel> scriptsList = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_scene);

    newScriptBtn = findViewById(R.id.SCRIPT_CREATE_NEW_BTN_ID);
    newScriptBtn.setOnClickListener(v -> {
      Intent intent = new Intent(this, CreateNewItemDialog.class);
      intent.putExtra("title", R.string.script_create_title);
      intent.putExtra("isScript", 1);
      startActivityForResult(intent, 1);
    });

    String title = getIntent().getStringExtra("sceneName");
    sceneId = getIntent().getIntExtra("sceneId", 0);

    scriptDBAdapter  = new ScriptDBAdapter(this);
    setListData();
    // получаем указатель на тулбар активированного в главном компоненте
    Objects.requireNonNull(getSupportActionBar()).setTitle(title);
  }


  /** обновление списка скриптов */
  void setListData(){
    scriptsList = scriptDBAdapter.getScriptsList(sceneId);
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(R.id.SCREEN_SCRIPTS_LIST_ID);

    if(lsf != null && lsf.getView() != null){
      lsf.updateScriptListAdapter(scriptsList);
    }
  }

  /** обновить имя текущего скрипта  */
  public void onUpdateScriptNameButtonPressed(int id) {
    ScriptRecycleDataModel scriptRecycleDataModel = scriptsList.get(id);
    Intent intent = new Intent(this, CreateNewItemDialog.class);
    intent.putExtra("title", R.string.script_update_title);
    intent.putExtra("id", id);
    if(scriptRecycleDataModel != null){
      intent.putExtra("oldName", scriptRecycleDataModel.getTitle());
      intent.putExtra("hasBattleSceneValue", scriptRecycleDataModel.hasBattleActionIcon ? 1 : 0);
      intent.putExtra("description", scriptRecycleDataModel.getDescription());
      intent.putExtra("isScript", 1);
      startActivityForResult(intent, 2);
    }
  }

  /** обработчик результата работы дочерних активностей по созданию или редактированию скриптов */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);
    if(resultCode != RESULT_OK){
      return;
    }
    String newName = result.getStringExtra("name");
    int hasBattleSceneValue = result.getIntExtra("hasBattleSceneValue", 0);
    String description = result.getStringExtra("description");
    int id = result.getIntExtra("id", 0);
    if(newName != null && newName.trim().length() == 0){
      return;
    }
    ScriptRecycleDataModel item = new ScriptRecycleDataModel(newName, id, description, hasBattleSceneValue == 1, false);
    switch (requestCode){
      case 1:
        scriptDBAdapter.addNewScript(item, sceneId);
        break;
      case 2:
        scriptDBAdapter.updateScript(item, id);
        break;
    }
    setListData();
  }

  /** обработчик нажатия на кнопки управления скриптом */
  @Override
  public void onClick(View elementFiredAction, int position) {
    ScriptRecycleDataModel currentData = (ScriptRecycleDataModel) scriptsList.values().toArray()[position];
    int btnId = elementFiredAction.getId();
    int currId = currentData.getId();
    switch (btnId){
      case R.id.SCRIPT_START_BTN_ID:
        if(currentData.hasBattleActionIcon){
          Intent intent = new Intent(this, EnemiesListView.class);
          intent.putExtra("scriptId", currId);
          startActivity(intent);
        } else {
          Intent intent = new Intent(this, ScriptTextDescriptionScreen.class);
          startActivity(intent);
        }
        break;
      case  R.id.SCRIPT_EDIT_BTN_ID:
        onUpdateScriptNameButtonPressed(currId);
        break;
      case R.id.SCRIPT_DELETE_BTN_ID:
        scriptDBAdapter.deleteScript(currentData.getId());
        setListData();
        break;
    }
  }
}

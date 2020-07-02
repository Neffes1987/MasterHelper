package com.example.masterhelper.ui.scene;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.CreateNewItemDialog;
import com.example.masterhelper.DialogPopup;
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

import static android.content.DialogInterface.BUTTON_POSITIVE;

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
      intent.putExtra(CreateNewItemDialog.TITLE, R.string.script_create_title);
      intent.putExtra(CreateNewItemDialog.IS_SCRIPT, 1);
      startActivityForResult(intent, 1);
    });

    String title = getIntent().getStringExtra("sceneName");
    sceneId = getIntent().getIntExtra("sceneId", 0);

    scriptDBAdapter  = new ScriptDBAdapter(this);
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

  @Override
  protected void onStart() {
    super.onStart();
    setListData();
  }

  /** обновить текущий скрипт  */
  public void onUpdateScriptNameButtonPressed(int id) {
    ScriptRecycleDataModel scriptRecycleDataModel = scriptsList.get(id);
    Intent intent = new Intent(this, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.TITLE, R.string.script_update_title);
    intent.putExtra(CreateNewItemDialog.IS_UPDATE, 1);
    intent.putExtra(CreateNewItemDialog.ID, id);
    if(scriptRecycleDataModel != null){
      intent.putExtra(CreateNewItemDialog.OLD_NAME, scriptRecycleDataModel.getTitle());
      intent.putExtra(CreateNewItemDialog.IS_BATTLE, scriptRecycleDataModel.hasBattleActionIcon ? 1 : 0);
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, scriptRecycleDataModel.getDescription());
      intent.putExtra(CreateNewItemDialog.IS_SCRIPT, 1);
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
    String newName = result.getStringExtra(CreateNewItemDialog.NAME);
    int hasBattleSceneValue = result.getIntExtra(CreateNewItemDialog.IS_BATTLE, 0);
    String description = result.getStringExtra(CreateNewItemDialog.DESCRIPTION);
    int id = result.getIntExtra(CreateNewItemDialog.ID, 0);

    if(newName != null && newName.trim().length() == 0){
      return;
    }

    ScriptRecycleDataModel item;

    if(id > 0){
      item = scriptDBAdapter.getScriptById(id);
      item.setTitle(newName);
      item.setDescription(description);
      item.hasBattleActionIcon = hasBattleSceneValue == 1;
    } else {
      item = new ScriptRecycleDataModel(newName, id, description, hasBattleSceneValue == 1, false);
    }
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
        DialogPopup dialogPopup = new DialogPopup(getSupportFragmentManager());
        dialogPopup.setClickListener((dialogInterface, id) -> {
          if(id == BUTTON_POSITIVE){
            scriptDBAdapter.deleteScript(currentData.getId());
            setListData();
          }
        });
        dialogPopup.show();
        break;
      case R.id.SCRIPT_BTN_DONE_ID:
        boolean isFinished = !currentData.isFinished;
        currentData.setFinished(isFinished);
        scriptDBAdapter.updateScript(currentData, currentData.getId());
        setListData();
        break;
    }
  }
}

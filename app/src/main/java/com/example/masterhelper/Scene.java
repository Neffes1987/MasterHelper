package com.example.masterhelper;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.SceneContract;
import com.example.masterhelper.data.contracts.ScriptsContract;
import com.example.masterhelper.models.SceneRecycleDataModel;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.models.ScriptRecycleDataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedHashMap;
import java.util.Objects;

public class Scene extends AppCompatActivity implements ICommonItemEvents {
  /** */
  DbHelpers dbHelpers;

  /** */
  int activityScreenViewSceneLayout = R.layout.activity_screen_view_scene;

  /** */
  int newScriptBtnId = R.id.SCRIPT_CREATE_NEW_BTN_ID;
  FloatingActionButton newScriptBtn;

  /** */
  int screenScriptsListId = R.id.SCREEN_SCRIPTS_LIST_ID;

  int sceneId;

  public LinkedHashMap<Integer, ScriptRecycleDataModel> data = new LinkedHashMap<>();



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScreenViewSceneLayout);

    newScriptBtn = findViewById(newScriptBtnId);
    newScriptBtn.setOnClickListener(onCreateListener);

    String title = getIntent().getStringExtra("sceneName");
    sceneId = getIntent().getIntExtra("sceneId", 0);

    dbHelpers  = new DbHelpers(this);
    getScriptsList(sceneId);
    setListData(data);
    // получаем указатель на тулбар активированного в главном компоненте
    Objects.requireNonNull(getSupportActionBar()).setTitle(title);
  }

  private void getScriptsList(int sceneId){
    String sqlQuery = ScriptsContract.getListQuery(ScriptsContract.TABLE_NAME, null, ScriptsContract.COLUMN_SCENE_ID+"="+ sceneId, ScriptsContract._ID + " DESC", 0);
    LinkedHashMap<Integer, ScriptRecycleDataModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(ScriptsContract._ID);
      int isFinishedColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_IS_FINISHED);
      int isBattleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_HAS_BATTLE_EVENT);

      ScriptRecycleDataModel scriptRecycleDataModel = new ScriptRecycleDataModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        queryResult.getString(isBattleColumnIndex).equals("true"),
        queryResult.getString(isFinishedColumnIndex).equals("true")
      );
      result.put(scriptRecycleDataModel.getId(), scriptRecycleDataModel);
    }
    queryResult.close();
    data = result;
  }


  void setListData(LinkedHashMap<Integer, ScriptRecycleDataModel> data){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(screenScriptsListId);

    if(lsf != null && lsf.getView() != null){
      lsf.updateScriptListAdapter(data);
    }
  }

  View.OnClickListener onCreateListener = v -> {
    Intent intent = new Intent(this, CreateNewItem.class);
    intent.putExtra("title", R.string.script_create_title);
    intent.putExtra("isScript", 1);
    startActivityForResult(intent, 1);
  };

  public void onUpdateScriptNameButtonPressed(int id) {
    ScriptRecycleDataModel scriptRecycleDataModel = data.get(id);
    Intent intent = new Intent(this, CreateNewItem.class);
    intent.putExtra("title", R.string.screen_name_scene_update);
    intent.putExtra("id", id);
    assert scriptRecycleDataModel != null;
    intent.putExtra("oldName", scriptRecycleDataModel.getTitle());
    intent.putExtra("hasBattleSceneValue", scriptRecycleDataModel.hasBattleActionIcon ? 1 : 0);
    intent.putExtra("description", scriptRecycleDataModel.getDescription());
    intent.putExtra("isScript", 1);
    startActivityForResult(intent, 2);
  }

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
        addNewScript(item);
        break;
      case 2:
        updateScript(item, id);
        break;
    }
  }


  private void addNewScript(ScriptRecycleDataModel newItem){
    String sqlQuery = ScriptsContract.addItemQuery(newItem, sceneId);
    dbHelpers.addNewItem(sqlQuery);
    getScriptsList(sceneId);
    setListData(data);
  }

  private void updateScript(ScriptRecycleDataModel newItem, int itemId){
    String sqlQuery = ScriptsContract.updateItemQuery(itemId, newItem);
    dbHelpers.updateItem(sqlQuery);
    getScriptsList(sceneId);
    setListData(data);
  }

  private void deleteScript(int itemId){
    String sqlQuery = ScriptsContract.deleteItemQuery(itemId);
    dbHelpers.deleteItem(sqlQuery);
    getScriptsList(sceneId);
    setListData(data);
  }


  @Override
  public void onClick(View elementFiredAction, int position) {
    ScriptRecycleDataModel currentData = (ScriptRecycleDataModel) data.values().toArray()[position];
    int btnId = elementFiredAction.getId();
    int currId = currentData.getId();
    switch (btnId){
      case R.id.SCRIPT_START_BTN_ID:
        if(currentData.hasBattleActionIcon){
          Intent intent = new Intent(this, Script.class);
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
        deleteScript(currentData.getId());
        break;
      default: return;
    }
  }
}

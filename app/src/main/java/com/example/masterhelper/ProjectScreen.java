package com.example.masterhelper;

import android.database.Cursor;
import android.view.View;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.JourneysContract;
import com.example.masterhelper.data.contracts.SceneContract;
import com.example.masterhelper.models.JourneyModel;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.models.SceneRecycleDataModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.LinkedHashMap;
import java.util.Objects;


public class ProjectScreen extends AppCompatActivity implements ICommonItemEvents {
  JourneyModel currentJourney;

  DbHelpers dbHelpers;

  /** */
  int activityProjectViewScreenLayout = R.layout.activity_project_view_screen;

  /** */
  int screenFragmentId = R.id.SCREEN_FRAGMENT_ID;

  /** */
  int createNewSceneBtnId = R.id.CREATE_NEW_SCENE_BTN_ID;
  FloatingActionButton createNewSceneBtn;

  public LinkedHashMap<Integer, SceneRecycleDataModel> data = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityProjectViewScreenLayout);
    int journeyId = getIntent().getIntExtra("id", -1);
    if(journeyId == -1){
      setResult(RESULT_CANCELED);
      finish();
    }
    dbHelpers  = new DbHelpers(this);
    currentJourney = getJourney(journeyId);

    // получаем указатель на тулбар активированного в главном компоненте
    Objects.requireNonNull(getSupportActionBar()).setTitle(currentJourney.getTitle());

    createNewSceneBtn = findViewById(createNewSceneBtnId);
    createNewSceneBtn.setOnClickListener(onCreateBtnListener);
    getScenesList(currentJourney.getId());
    setListData(data);
  }

  private JourneyModel getJourney(int id){
    String sqlQuery = JourneysContract.getListQuery(JourneysContract.TABLE_NAME, null, JourneysContract._ID+"="+ id, null, 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    queryResult.moveToNext();
    int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
    int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
    return new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
  }

  private LinkedHashMap<Integer, SceneRecycleDataModel> getScenesList(int journeyId){
    String sqlQuery = SceneContract.getListQuery(SceneContract.TABLE_NAME, null, SceneContract.COLUMN_JOURNEY_ID+"="+ journeyId, SceneContract._ID + " DESC", 0);
    LinkedHashMap<Integer, SceneRecycleDataModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(SceneContract._ID);

      SceneRecycleDataModel sceneRecycleDataModel = new SceneRecycleDataModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        0,
        0,
        false,
        false
      );
      result.put(sceneRecycleDataModel.getId(), sceneRecycleDataModel);
    }
    queryResult.close();
    data = result;
    return result;
  }

  private void addNewScene(SceneRecycleDataModel newItem){
    String sqlQuery = SceneContract.addItemQuery(newItem, currentJourney.getId());
    dbHelpers.addNewItem(sqlQuery);
    getScenesList(currentJourney.getId());
    setListData(data);
  }

  private void updateScene(SceneRecycleDataModel newItem, int itemId){
    String sqlQuery = SceneContract.updateItemQuery(itemId, newItem);
    dbHelpers.updateItem(sqlQuery);
    getScenesList(currentJourney.getId());
    setListData(data);
  }

  private void deleteScene(int itemId){
    String sqlQuery = SceneContract.deleteItemQuery(itemId);
    dbHelpers.deleteItem(sqlQuery);
    getScenesList(currentJourney.getId());
    setListData(data);
  }



  void setListData(LinkedHashMap<Integer, SceneRecycleDataModel> data){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(screenFragmentId);

    if(lsf != null && lsf.getView() != null){
      lsf.updateSceneListAdapter(data);
    }
  }

  View.OnClickListener onCreateBtnListener = v -> onCreateButtonPressed();

  public void onCreateButtonPressed() {
    Intent intent = new Intent(this, CreateNewItem.class);
    intent.putExtra("title", R.string.scene_create_title);
    startActivityForResult(intent, 1);
  }

  public void onUpdateScreenNameButtonPressed(int id) {
    Intent intent = new Intent(this, CreateNewItem.class);
    intent.putExtra("title", R.string.screen_name_scene_update);
    intent.putExtra("id", id);
    intent.putExtra("oldName", Objects.requireNonNull(data.get(id)).getTitle());
    startActivityForResult(intent, 2);
  }

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
        addNewScene(item);
        break;
      case 2:
        updateScene(item, id);
        break;
    }
  }

  @Override
  public void onClick(View elementFiredAction, int position) {
    SceneRecycleDataModel currentData = (SceneRecycleDataModel) data.values().toArray()[position];
    int btnId = elementFiredAction.getId();
    switch (btnId){
      case R.id.SCENE_START_BTN_ID:
        Intent intent = new Intent(this, Scene.class);
        startActivity(intent);
        break;
      case R.id.SCENE_DELETE_BTN_ID:
        deleteScene(currentData.getId());
        break;
      case R.id.SCENE_EDIT_BTN_ID:
          onUpdateScreenNameButtonPressed(currentData.getId());
      default: return;
    }
  }
}

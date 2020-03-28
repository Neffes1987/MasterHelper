package com.example.masterhelper;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerAccordionEvents;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.ui.RecyclerViewFragment.models.script.ScriptRecycleDataModel;

import java.util.HashMap;

public class Scene extends AppCompatActivity implements IRecycleAdapter {

  /** */
  int activityScreenViewSceneLayout = R.layout.activity_screen_view_scene;

  /** */
  int screenScriptsListId = R.id.SCREEN_SCRIPTS_LIST_ID;

  public HashMap<Integer, ScriptRecycleDataModel> data = new HashMap<>();

  public ScriptRecycleDataModel item = new ScriptRecycleDataModel("Событие сцены", "Text", true, false);
  public ScriptRecycleDataModel item1 = new ScriptRecycleDataModel("Событие сцены", "Text", false, false);


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScreenViewSceneLayout);
    data.put(0, item);
    data.put(1, item1);
    data.put(2, item);
    data.put(3, item1);
    setListData(data);
    // получаем указатель на тулбар активированного в главном компоненте
    getSupportActionBar().setTitle("Имя сцены");
  }


  void setListData(HashMap<Integer, ScriptRecycleDataModel> data){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(screenScriptsListId);

    if(lsf != null && lsf.getView() != null){
      lsf.updateScriptListAdapter(data);
    }
  }

  @Override
  public void onChangeItem(int position, RecyclerAccordionEvents fieldName, String newValue) {
    ScriptRecycleDataModel currentData = data.get(position);

    switch (fieldName){
      case start:
        assert currentData != null;
        if(currentData.hasBattleActionIcon){
          Intent intent = new Intent(this, Script.class);
          startActivity(intent);
        } else {
          Intent intent = new Intent(this, ScriptTextDescriptionScreen.class);
          startActivity(intent);
        }
        break;
      default: return;
    }
  }
}

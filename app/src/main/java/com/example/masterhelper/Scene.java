package com.example.masterhelper;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.models.ScriptRecycleDataModel;

import java.util.LinkedHashMap;

public class Scene extends AppCompatActivity implements ICommonItemEvents {

  /** */
  int activityScreenViewSceneLayout = R.layout.activity_screen_view_scene;

  /** */
  int screenScriptsListId = R.id.SCREEN_SCRIPTS_LIST_ID;

  public LinkedHashMap<Integer, ScriptRecycleDataModel> data = new LinkedHashMap<>();

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


  void setListData(LinkedHashMap<Integer, ScriptRecycleDataModel> data){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(screenScriptsListId);

    if(lsf != null && lsf.getView() != null){
      lsf.updateScriptListAdapter(data);
    }
  }

  @Override
  public void onClick(View elementFiredAction, int position) {
    ScriptRecycleDataModel currentData = data.get(position);
    int btnId = elementFiredAction.getId();
    switch (btnId){
      case R.id.SCRIPT_START_BTN_ID:
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

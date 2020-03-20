package com.example.masterhelper;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.RecyclerViewFragment.RecyclerAccordionEvents;
import com.example.masterhelper.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.RecyclerViewFragment.models.script.ScriptRecycleDataModel;

import java.util.HashMap;

public class Scene extends AppCompatActivity implements IRecycleAdapter {
  public HashMap<Integer, ScriptRecycleDataModel> data = new HashMap<>();

  public ScriptRecycleDataModel item = new ScriptRecycleDataModel("Событие сцены", "Text", true, false);


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_scene);
    data.put(0, item);
    data.put(1, item);
    data.put(2, item);
    data.put(3, item);
    setListData(data);
    // получаем указатель на тулбар активированного в главном компоненте
    getSupportActionBar().setTitle("Имя сцены");
  }


  void setListData(HashMap<Integer, ScriptRecycleDataModel> data){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(R.id.screenScriptsList);

    if(lsf != null && lsf.getView() != null){
      lsf.updateScriptListAdapter(data);
    }
  }

  @Override
  public void onChangeItem(int position, RecyclerAccordionEvents fieldName, String newValue) {
    switch (fieldName){
      case start:
        Intent intent = new Intent(this, Script.class);
        startActivity(intent);
        break;
      default: return;
    }
  }
}

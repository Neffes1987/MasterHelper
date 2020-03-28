package com.example.masterhelper;

import com.example.masterhelper.ui.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerAccordionEvents;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.ui.RecyclerViewFragment.models.scene.SceneRecycleDataModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;


public class ProjectScreen extends AppCompatActivity implements IRecycleAdapter {
  /** */
  int activityProjectViewScreenLayout = R.layout.activity_project_view_screen;

  /** */
  int screenFragmentId = R.id.SCREEN_FRAGMENT_ID;

  public HashMap<Integer, SceneRecycleDataModel> data = new HashMap<>();

  public SceneRecycleDataModel item = new SceneRecycleDataModel("Item", "Text", 19, 20, true, true);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityProjectViewScreenLayout);
    data.put(0, item);
    data.put(1, item);
    data.put(2, item);
    data.put(3, item);
    setListData(data);

    // получаем указатель на тулбар активированного в главном компоненте
    getSupportActionBar().setTitle("Имя приключения");
  }

  void setListData(HashMap<Integer, SceneRecycleDataModel> data){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(screenFragmentId);

    if(lsf != null && lsf.getView() != null){
      lsf.updateSceneListAdapter(data);
    }
  }

  @Override
  public void onChangeItem(int position, RecyclerAccordionEvents fieldName, String newValue) {
    switch (fieldName){
      case start:
        Intent intent = new Intent(this, Scene.class);
        startActivity(intent);
        break;
      default: return;
    }
  }
}

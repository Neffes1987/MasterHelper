package com.example.masterhelper;

import android.view.View;
import com.example.masterhelper.ui.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerAccordionEvents;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.ui.RecyclerViewFragment.models.scene.SceneRecycleDataModel;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Objects;


public class ProjectScreen extends AppCompatActivity implements IRecycleAdapter {
  String TITLE = "Имя приключения";

  /** */
  int activityProjectViewScreenLayout = R.layout.activity_project_view_screen;

  /** */
  int screenFragmentId = R.id.SCREEN_FRAGMENT_ID;

  /** */
  int createNewSceneBtnId = R.id.CREATE_NEW_SCENE_BTN_ID;
  FloatingActionButton createNewSceneBtn;

  public HashMap<Integer, SceneRecycleDataModel> data = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityProjectViewScreenLayout);

    // получаем указатель на тулбар активированного в главном компоненте
    Objects.requireNonNull(getSupportActionBar()).setTitle(TITLE);

    createNewSceneBtn = findViewById(createNewSceneBtnId);
    createNewSceneBtn.setOnClickListener(onCreateBtnListener);

  }

  void setListData(HashMap<Integer, SceneRecycleDataModel> data){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(screenFragmentId);

    if(lsf != null && lsf.getView() != null){
      lsf.updateSceneListAdapter(data);
    }
  }

  View.OnClickListener onCreateBtnListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      onCreateButtonPressed();
    }
  };

  public void onCreateButtonPressed() {
    Intent intent = new Intent(this, CreateNewItem.class);
    intent.putExtra("title", R.string.scene_create_title);
    startActivityForResult(intent, 1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);

    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String newName = result.getStringExtra("name");
        if(newName != null && newName.trim().length() == 0){
          return;
        }
         SceneRecycleDataModel item = new SceneRecycleDataModel(newName);
        data.put(data.size(), item);
        setListData(data);
      }
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

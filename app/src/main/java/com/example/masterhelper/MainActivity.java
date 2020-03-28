package com.example.masterhelper;

import com.example.masterhelper.ui.ListFragment.IListFragmentInterface;
import com.example.masterhelper.ui.ListFragment.ListScreenFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements IListFragmentInterface {
  /** */
  int journeysScreenId = R.id.JOURNEYS_ID;

  /** */
  int activityScreenViewProjectsLayout = R.layout.activity_screen_view_projects;

  /** */
  int toolbarId = R.id.TOOLBAR_ID;
  Toolbar toolbar;

  /** */
  int ScreenTitleStringString = R.string.SCREEN_NAME_JOURNEYS_TEXT;

  public String[] data = new String[]{"Проект1", "Проект2", "Проект3"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScreenViewProjectsLayout);
    toolbar = findViewById(toolbarId);
    toolbar.setTitle(ScreenTitleStringString);
    setSupportActionBar(toolbar);
    setListData(data);
  }

  @Override
  public void onCreateButtonPressed() {
    Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onItemButtonPressed(String id) {
    Intent intent = new Intent(MainActivity.this, ProjectScreen.class);
    startActivity(intent);
  }

  @Override
  public void onSearchStringChanged(String str) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
  }

  void setListData(String[] data){
    FragmentManager fm = getSupportFragmentManager();
    ListScreenFragment lsf = (ListScreenFragment) fm.findFragmentById(journeysScreenId);
    if(lsf != null && lsf.getView() != null){
      lsf.updateListValues(lsf.getView(), data);
    }
  }
}

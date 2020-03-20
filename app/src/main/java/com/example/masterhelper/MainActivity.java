package com.example.masterhelper;

import com.example.masterhelper.ListFragment.IListFragmentInterface;
import com.example.masterhelper.ListFragment.ListScreenFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements IListFragmentInterface {
  Toolbar toolbar;
  public String[] data = new String[]{"Проект1", "Проект2", "Проект3"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_projects);
    toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.screen_name_journeys);
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
    ListScreenFragment lsf = (ListScreenFragment) fm.findFragmentById(R.id.journeys);
    if(lsf != null && lsf.getView() != null){
      lsf.updateListValues(lsf.getView(), data);
    }
  }
}

package com.example.masterhelper;

import ListFragment.IListFragmentInterface;
import ListFragment.ListScreenFragment;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

public class ProjectScreen extends AppCompatActivity implements IListFragmentInterface {
  public String[] data = new String[]{"Сцена1", "Сцена2", "Сцена3"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project_screen);

    setListData(data);

    // получаем указатель на тулбар активированного в главном компоненте
    getSupportActionBar().setTitle("Имя приключения");
  }

  @Override
  public void onCreateButtonPressed() {

  }

  @Override
  public void onItemButtonPressed(String id) {
    Intent intent = new Intent(this, Scene.class);
    startActivity(intent);
  }

  @Override
  public void onSearchStringChanged(String str) {

  }

  void setListData(String[] data){
    FragmentManager fm = getSupportFragmentManager();
    ListScreenFragment lsf = (ListScreenFragment) fm.findFragmentById(R.id.journeys);
    if(lsf != null && lsf.getView() != null){
      lsf.updateListValues(lsf.getView(), data);
    }
  }
}
package com.example.masterhelper;

import com.example.masterhelper.ListFragment.IListFragmentInterface;
import com.example.masterhelper.ListFragment.ListScreenFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

public class Scene extends AppCompatActivity implements IListFragmentInterface {
  public String[] data = new String[]{"Шаг1", "Шаг2", "Шаг3"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_scene);
    setListData(data);
    // получаем указатель на тулбар активированного в главном компоненте
    getSupportActionBar().setTitle("Имя сцены");
  }


  void setListData(String[] data){
    FragmentManager fm = getSupportFragmentManager();
    ListScreenFragment lsf = (ListScreenFragment) fm.findFragmentById(R.id.journeys);
    if(lsf != null && lsf.getView() != null){
      lsf.updateListValues(lsf.getView(), data);
    }
  }

  @Override
  public void onCreateButtonPressed() {

  }

  @Override
  public void onItemButtonPressed(String id) {

  }

  @Override
  public void onSearchStringChanged(String str) {

  }
}

package com.example.masterhelper;

import android.util.Log;
import android.view.MenuItem;
import com.example.masterhelper.ui.AppBarFragment.IAppBarFragment;
import com.example.masterhelper.ui.ListFragment.IListFragmentInterface;
import com.example.masterhelper.ui.ListFragment.ListScreenFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import java.util.HashSet;

import static com.example.masterhelper.utils.Utils.convertToArray;

public class MainActivity extends AppCompatActivity implements IListFragmentInterface, IAppBarFragment {
  /** */
  int journeysScreenId = R.id.JOURNEYS_ID;

  /** */
  int activityScreenViewProjectsLayout = R.layout.activity_screen_view_projects;

  /** */
  int toolbarId = R.id.TOOLBAR_ID;
  Toolbar toolbar;

  /** */
  int ScreenTitleStringString = R.string.SCREEN_NAME_JOURNEYS_TEXT;

  public HashSet<String> data = new HashSet<>();

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
    Intent intent = new Intent(MainActivity.this, CreateNewItem.class);
    intent.putExtra("title", R.string.journey_create_title);
    startActivityForResult(intent, 1);
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

  void setListData(HashSet<String> data){
    String[] dataArray = convertToArray(data);
    FragmentManager fm = getSupportFragmentManager();
    ListScreenFragment lsf = (ListScreenFragment) fm.findFragmentById(journeysScreenId);
    if(lsf != null && lsf.getView() != null){
      lsf.updateListValues(lsf.getView(), dataArray);
    }
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
        data.add(newName);
        setListData(data);
      }
    }
  }

  @Override
  public void onItemSelected(MenuItem selectedView) {
    Intent intent = new Intent(MainActivity.this, MusicSettingsScreen.class);
    startActivity(intent);
  }
}

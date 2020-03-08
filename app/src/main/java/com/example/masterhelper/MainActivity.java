package com.example.masterhelper;

import ListFragment.IListFragmentInterface;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements IListFragmentInterface {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.screen_name_journeys);
    setSupportActionBar(toolbar);
  }

  @Override
  public void onCreateButtonPressed() {
    Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onItemButtonPressed(String id) {
    Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onSearchStringChanged(String str) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
  }
}

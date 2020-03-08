package com.example.masterhelper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class ProjectScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project_screen);
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("'Имя проекта'");
    setSupportActionBar(toolbar);
  }
}

package com.example.com.masterhelper.ui.forces;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.masterhelper.R;

public class ScreenJourneyForcesList extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_journey_forces_list);
    ActionBar bar = getSupportActionBar();
    if(bar != null){
      bar.setTitle(R.string.force_menu_item_title);
    }
  }
}

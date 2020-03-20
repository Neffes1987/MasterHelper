package com.example.masterhelper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

public class Script extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_script);
    TableLayout tableLayout = findViewById(R.id.enemies_grid);

    TableRow tableRow = new TableRow(this);
    tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
    tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View enemy_icon1 = inflater.inflate(R.layout.fragment_view_enemy_icon, null);
    View enemy_icon2 = inflater.inflate(R.layout.fragment_view_enemy_icon, null);
    View enemy_icon3 = inflater.inflate(R.layout.fragment_view_enemy_icon, null);
    View enemy_icon4 = inflater.inflate(R.layout.fragment_view_enemy_icon, null);
    tableRow.addView(enemy_icon1);
    tableRow.addView(enemy_icon2);
    tableRow.addView(enemy_icon3);
    tableRow.addView(enemy_icon4);

    tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
  }
}

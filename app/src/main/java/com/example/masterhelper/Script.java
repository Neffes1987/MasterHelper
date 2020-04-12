package com.example.masterhelper;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.commonAdapter.item.CustomListItemsEnum;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.models.EnemyModel;

import java.util.HashMap;

public class Script extends AppCompatActivity implements ICommonItemEvents {
  /** */
  int activityScreenViewScriptLayout =  R.layout.activity_screen_view_script;

  /** */
  int tableId = R.id.ENEMIES_GRID_ID;

  int NUMBER_OF_CELLS = 5;

  /** */
  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScreenViewScriptLayout);


    recyclerView = findViewById(tableId);

    EnemyModel enemy1 = new EnemyModel("", 20, 10, 5, 1);
    EnemyModel enemy2 = new EnemyModel("", 20, 20, 5, 2);
    EnemyModel enemy3 = new EnemyModel("", 20, 5, 5, 3);
    EnemyModel enemy4 = new EnemyModel("", 20, 15, 5, 4);
    EnemyModel enemy5 = new EnemyModel("", 20, 15, 5, 5);

    HashMap<Integer, EnemyModel> enemies = new HashMap<>();
    enemies.put(0, enemy1);
    enemies.put(1, enemy2);
    enemies.put(2, enemy3);
    enemies.put(3, enemy4);
    enemies.put(4, enemy5);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_CELLS);
    recyclerView.setLayoutManager(gridLayoutManager);

    CommonAdapter<EnemyModel> mAdapter = new CommonAdapter<>(enemies, R.layout.fragment_view_enemy_icon, CustomListItemsEnum.enemyIcon, this);
    recyclerView.setAdapter(mAdapter);
  }


   protected void openEnemyDetails(long id){
     Intent intent = new Intent(this, ScriptAboutView.class);
     intent.putExtra("id", id);
     startActivity(intent);
   }

  @Override
  public void onClick(View elementFiredAction, int position) {
    openEnemyDetails(position);
  }
}

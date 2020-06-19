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
import com.example.masterhelper.models.ACHIEVE_CONST_TAGS;
import com.example.masterhelper.models.AchieveModel;
import com.example.masterhelper.models.EnemyModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedHashMap;

public class Script extends AppCompatActivity implements ICommonItemEvents {
  /** */
  int activityScreenViewScriptLayout =  R.layout.activity_screen_view_script;

  /** */
  int tableId = R.id.ENEMIES_GRID_ID;

  /** */
  int createNewEnemyId = R.id.CREATE_NEW_ENEMY_ID;
  FloatingActionButton createNewEnemy;

  int NUMBER_OF_CELLS = 5;

  /** */
  RecyclerView recyclerView;

  /**
   *
   */
  private final LinkedHashMap<Integer, AchieveModel> mockAchieves = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScreenViewScriptLayout);
    mockAchieves.put(0, new AchieveModel(0, "Здоровье", 10, ACHIEVE_CONST_TAGS.HEALTH));
    mockAchieves.put(1, new AchieveModel(1, "Урон", 10  ));
    mockAchieves.put(2, new AchieveModel(2, "Инициатива", 10 ));

    recyclerView = findViewById(tableId);
    createNewEnemy = findViewById(createNewEnemyId);
    createNewEnemy.setOnClickListener(onCreateBtn);

    EnemyModel enemy1 = new EnemyModel(0, "", "test", mockAchieves, 20);
    EnemyModel enemy2 = new EnemyModel(1, "", "test", mockAchieves, 20);
    EnemyModel enemy3 = new EnemyModel(2, "", "test", mockAchieves, 20);
    EnemyModel enemy4 = new EnemyModel(3, "", "test", mockAchieves, 20);
    EnemyModel enemy5 = new EnemyModel(4,  "", "test", mockAchieves, 20);

    LinkedHashMap<Integer, EnemyModel> enemies = new LinkedHashMap<>();
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

   protected void editEnemyDetails(long id){
     Intent intent = new Intent(this, EditEnemy.class);
     intent.putExtra("id", id);
     startActivity(intent);
   }

  @Override
  public void onClick(View elementFiredAction, int position) {
    openEnemyDetails(position);
  }

  public View.OnClickListener onCreateBtn = v -> {
    int btnId = v.getId();
    if (btnId == R.id.CREATE_NEW_ENEMY_ID) {
      editEnemyDetails(0);
    }
  };
}

package com.example.masterhelper.ui.enemies;

import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.models.EnemyModel;
import com.example.masterhelper.ui.recyclerViewFragment.RecyclerViewFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedHashMap;

public class EnemiesListView extends AppCompatActivity implements ICommonItemEvents {

  int CREATE_NEW_ENEMY_CODE = 1;
  int EDIT_ENEMY_CODE = 2;

  /** */
  FloatingActionButton createNewEnemy;


  int scriptId;

  EnemyDBAdapter enemyDBAdapter;


  RecyclerViewFragment lsf;

  LinkedHashMap<Integer, EnemyModel> enemies = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_script);

    scriptId = getIntent().getIntExtra("scriptId", 0);

    enemyDBAdapter = new EnemyDBAdapter();

    createNewEnemy = findViewById(R.id.CREATE_NEW_ENEMY_ID);

    createNewEnemy.setOnClickListener(v -> {
      int btnId = v.getId();
      if (btnId == R.id.CREATE_NEW_ENEMY_ID) {
        editEnemyDetails(scriptId);
      }
    });

    FragmentManager fm = getSupportFragmentManager();
    lsf = (RecyclerViewFragment) fm.findFragmentById(R.id.ENEMIES_GRID_ID);

    updateEnemiesList();
  }

  void updateEnemiesList(){
    enemies = enemyDBAdapter.getEnemiesByScriptId(scriptId);
    lsf.updateEnemiesListAdapter(enemies);
  }

   /**  */
   protected void openEnemyDetails(int enemyId){
     Intent intent = new Intent(this, EditEnemy.class);
     intent.putExtra("scriptId", scriptId);
     intent.putExtra("enemyId", enemyId);
     startActivityForResult(intent, EDIT_ENEMY_CODE);
   }

   /**  */
   protected void editEnemyDetails(int scriptId){
     Intent intent = new Intent(this, EditEnemy.class);
     intent.putExtra("scriptId", scriptId);
     intent.putExtra("enemyId", 0);
     startActivityForResult(intent, CREATE_NEW_ENEMY_CODE);
   }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK){
      if(requestCode == CREATE_NEW_ENEMY_CODE){
        updateEnemiesList();
      }
      if(requestCode == EDIT_ENEMY_CODE){
        updateEnemiesList();
      }
    }
  }

  /**  */
  @Override
  public void onClick(View elementFiredAction, int position) {
    EnemyModel currentData = (EnemyModel) enemies.values().toArray()[position];
    openEnemyDetails(currentData.getId());
  }

}

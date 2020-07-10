package com.masterhelper.ui.enemies;

import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.R;
import com.masterhelper.ui.appBarFragment.IAppBarFragment;
import com.masterhelper.dbAdaptersFactory.AdaptersType;
import com.masterhelper.dbAdaptersFactory.DBAdapterFactory;
import com.masterhelper.dbAdaptersFactory.adapters.EnemyDBAdapter;
import com.masterhelper.listFactory.CustomListItemsEnum;
import com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.masterhelper.mediaworker.BackgroundMediaPlayer;
import com.masterhelper.appconfig.models.EnemyModel;
import com.masterhelper.ui.appBarFragment.MusicSettingsScreen;
import com.masterhelper.listFactory.ListFactory;
import com.masterhelper.dbAdaptersFactory.adapters.ScriptDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RequiresApi(api = Build.VERSION_CODES.M)
public class EnemiesListView extends AppCompatActivity implements ICommonItemEvents, IAppBarFragment {

  int CREATE_NEW_ENEMY_CODE = 1;
  int EDIT_ENEMY_CODE = 2;

  private final int ADD_MUSIC_TO_SCRIPT_CODE = 3;

  /** указатель на кнопку проигрывания музыки */
  FloatingActionButton musicControl;

  BackgroundMediaPlayer backgroundMediaPlayer = BackgroundMediaPlayer.getInstance();

  /** */
  FloatingActionButton createNewEnemy;

  boolean scriptMusicStarted = false;

  public void setScriptMusicStarted(boolean scriptMusicStarted) {
    this.scriptMusicStarted = scriptMusicStarted;
  }

  int scriptId;

  EnemyDBAdapter enemyDBAdapter = (EnemyDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.enemy);
  ScriptDBAdapter scriptDBAdapter = (ScriptDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.script);


  ListFactory lsf;

  LinkedHashMap<Integer, EnemyModel> enemies = new LinkedHashMap<>();

  private String getMediaList(){
    HashMap<String, Integer> mediaList = scriptDBAdapter.getMediaForScript(scriptId);
    return mediaList.size() > 0 ? mediaList.keySet().toString().replaceAll("\\[|]|\\s", "") : "";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_script);

    scriptId = getIntent().getIntExtra("scriptId", 0);

    createNewEnemy = findViewById(R.id.CREATE_NEW_ENEMY_ID);

    musicControl = findViewById(R.id.SCRIPT_START_MUSIC_ID);

    createNewEnemy.setOnClickListener(v -> {
      int btnId = v.getId();
      if (btnId == R.id.CREATE_NEW_ENEMY_ID) {
        editEnemyDetails(scriptId);
      }
    });

    Toolbar toolbar = findViewById(R.id.TOOLBAR_ID);
    toolbar.setTitle(R.string.screen_name_scene_step);
    setSupportActionBar(toolbar);

    musicControl.setOnLongClickListener(v -> {
      Intent intent = new Intent(this, MusicSettingsScreen.class);
      intent.putExtra(MusicSettingsScreen.IS_GENERAL, 0);
      intent.putExtra(MusicSettingsScreen.SELECTED_LIST, getMediaList());
      startActivityForResult(intent, ADD_MUSIC_TO_SCRIPT_CODE);
      return true;
    });

    musicControl.setOnClickListener(v -> {
      boolean newSceneMusicState = !scriptMusicStarted;
      setScriptMusicStarted(newSceneMusicState);
      if(newSceneMusicState){
        backgroundMediaPlayer.setMediaList(getMediaList());
        backgroundMediaPlayer.startMediaList();
        musicControl.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorMusicStartedFloatTint));
      } else {
        backgroundMediaPlayer.stopMediaList();
        musicControl.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorCommonFloatTint));
      }
    });

    FragmentManager fm = getSupportFragmentManager();
    lsf = (ListFactory) fm.findFragmentById(R.id.ENEMIES_GRID_ID);

    updateEnemiesList();
  }

  void updateEnemiesList(){
    enemies = enemyDBAdapter.getListByParentId(scriptId);
    lsf.updateListAdapter(enemies, CustomListItemsEnum.enemyIcon);
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
      if(requestCode ==  ADD_MUSIC_TO_SCRIPT_CODE && data != null ){
        String selectedPaths = data.getStringExtra(MusicSettingsScreen.SELECTED_LIST);
        scriptDBAdapter.updateScriptMedia(selectedPaths, scriptId);
      }
    }
  }

  /**  */
  @Override
  public void onClick(View elementFiredAction, int position) {
    EnemyModel currentData = (EnemyModel) enemies.values().toArray()[position];
    openEnemyDetails(currentData.getId());
  }

  @Override
  public void onItemSelected(MenuItem selectedView) {}
}
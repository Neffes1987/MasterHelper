package com.example.com.masterhelper.enemies;

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
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.media.adapters.SettingsAdapterType;
import com.example.com.masterhelper.media.SettingsMediaFactory;
import com.example.com.masterhelper.media.adapters.MediaSettings;
import com.example.com.masterhelper.media.ui.MusicSettingsScreen;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.appbar.IAppBarFragment;
import com.example.com.masterhelper.core.factories.DBAdapters.AdaptersType;
import com.example.com.masterhelper.core.factories.DBAdapters.DBAdapterFactory;
import com.example.com.masterhelper.core.factories.DBAdapters.adapters.EnemyDBAdapter;
import com.example.com.masterhelper.listFactory.CustomListItemsEnum;
import com.example.com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.media.mediaworker.BackgroundMediaPlayer;
import com.example.com.masterhelper.core.models.EnemyModel;
import com.example.com.masterhelper.listFactory.ListFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

@RequiresApi(api = Build.VERSION_CODES.M)
public class EnemiesListView extends AppCompatActivity implements ICommonItemEvents, IAppBarFragment {

  int CREATE_NEW_ENEMY_CODE = 1;
  int EDIT_ENEMY_CODE = 2;

  private final int ADD_MUSIC_TO_SCRIPT_CODE = 3;

  /** указатель на кнопку проигрывания музыки */
  FloatingActionButton musicControl;

  BackgroundMediaPlayer backgroundMediaPlayer = GlobalApplication.getBackgroundMediaPlayer();
  MediaSettings scriptMediaSettings = SettingsMediaFactory.getAdapter(SettingsAdapterType.script);

  /** */
  FloatingActionButton createNewEnemy;

  boolean scriptMusicStarted = false;

  public void setScriptMusicStarted(boolean scriptMusicStarted) {
    this.scriptMusicStarted = scriptMusicStarted;
  }

  int scriptId;

  EnemyDBAdapter enemyDBAdapter = (EnemyDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.enemy);


  ListFactory lsf;

  ModelList enemies = new ModelList();

  private String[] getMediaList(){
    ModelList mediaList = scriptMediaSettings.get(scriptId);
    return scriptMediaSettings.pathsToArray(mediaList);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_script);

    scriptId = getIntent().getIntExtra("scriptId", 0);
    String scriptName = getIntent().getStringExtra("scriptName");

    createNewEnemy = findViewById(R.id.CREATE_NEW_ENEMY_ID);

    musicControl = findViewById(R.id.SCRIPT_START_MUSIC_ID);

    createNewEnemy.setOnClickListener(v -> {
      int btnId = v.getId();
      if (btnId == R.id.CREATE_NEW_ENEMY_ID) {
        editEnemyDetails(scriptId);
      }
    });

    Toolbar toolbar = findViewById(R.id.TOOLBAR_ID);
    toolbar.setSubtitle(R.string.screen_name_scene_step);
    toolbar.setTitle(scriptName);
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
        String[] selectedPaths = data.getStringArrayExtra(MusicSettingsScreen.SELECTED_LIST);
        scriptMediaSettings.update(selectedPaths, scriptId);
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

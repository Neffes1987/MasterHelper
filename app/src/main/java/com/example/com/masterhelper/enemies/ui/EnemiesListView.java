package com.example.com.masterhelper.enemies.ui;

import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.components.buttons.ComponentFloatButtonPrimary;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.media.adapters.SettingsAdapterType;
import com.example.com.masterhelper.media.SettingsMediaFactory;
import com.example.com.masterhelper.media.adapters.MediaSettings;
import com.example.com.masterhelper.media.ui.MusicSettingsScreen;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.appbar.IAppBarFragment;
import com.example.com.masterhelper.enemies.adapters.EnemyDBAdapter;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.media.mediaworker.BackgroundMediaPlayer;
import com.example.com.masterhelper.core.listFactory.ListFactory;

@RequiresApi(api = Build.VERSION_CODES.M)
public class EnemiesListView extends AppCompatActivity implements ICommonItemEvents, IAppBarFragment {

  int CREATE_NEW_ENEMY_CODE = 1;
  int EDIT_ENEMY_CODE = 2;

  private final int ADD_MUSIC_TO_SCRIPT_CODE = 3;

  /** указатель на кнопку проигрывания музыки */
  ComponentFloatButtonPrimary musicControl;

  BackgroundMediaPlayer backgroundMediaPlayer = GlobalApplication.getBackgroundMediaPlayer();
  MediaSettings scriptMediaSettings = SettingsMediaFactory.getAdapter(SettingsAdapterType.script);

  /** */
  ComponentFloatButtonPrimary createNewEnemy;

  boolean scriptMusicStarted = false;

  public void setScriptMusicStarted(boolean scriptMusicStarted) {
    this.scriptMusicStarted = scriptMusicStarted;
  }

  int scriptId;

  EnemyDBAdapter enemyDBAdapter = new EnemyDBAdapter();

  ListFactory lsf;

  CommonAdapter adapter;

  private String[] getMediaList(){
    ModelList mediaList = scriptMediaSettings.get(scriptId);
    return scriptMediaSettings.pathsToArray(mediaList);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_screen_view_script);

    scriptId = getIntent().getIntExtra("scriptId", 0);
    String scriptName = getIntent().getStringExtra("scriptName");

    View createNewEnemyWrapper = findViewById(R.id.CREATE_NEW_ENEMY_ID);

    createNewEnemy = new ComponentFloatButtonPrimary(createNewEnemyWrapper);

    View musicControlWrapper = findViewById(R.id.SCRIPT_START_MUSIC_ID);
    musicControl = new ComponentFloatButtonPrimary(musicControlWrapper);
    musicControl.setIconResource(R.mipmap.baseline_music_note_black_18dp);

    createNewEnemy.setListener(v -> {
      int btnId = v.getId();
      if (btnId == R.id.CREATE_NEW_ENEMY_ID) {
        editEnemyDetails(scriptId);
      }
    });

    Toolbar toolbar = findViewById(R.id.TOOLBAR_ID);
    toolbar.setSubtitle(R.string.screen_name_scene_step);
    toolbar.setTitle(scriptName);
    setSupportActionBar(toolbar);

    musicControl.setLongListener(v -> {
      Intent intent = new Intent(this, MusicSettingsScreen.class);
      intent.putExtra(MusicSettingsScreen.IS_GENERAL, 0);
      intent.putExtra(MusicSettingsScreen.SELECTED_LIST, getMediaList());
      startActivityForResult(intent, ADD_MUSIC_TO_SCRIPT_CODE);
      return true;
    });

    musicControl.setListener(v -> {
      boolean newSceneMusicState = !scriptMusicStarted;
      setScriptMusicStarted(newSceneMusicState);
      if(newSceneMusicState){
        backgroundMediaPlayer.setMediaList(getMediaList());
        backgroundMediaPlayer.startMediaList();
        musicControl.setColorResource(R.color.colorMusicStartedFloatTint);
      } else {
        backgroundMediaPlayer.stopMediaList();
        musicControl.setColorResource(R.color.colorCommonFloatTint);
      }
    });

    adapter = new CommonAdapter(enemyDBAdapter.getListByParentId(scriptId), R.layout.fragment_view_list_item_enemy_icon, this);
    adapter.setCommonItemInstanceGetter(curAdapter -> {
      EnemyIconItem item = new EnemyIconItem();
      item.attachAdapter(curAdapter);
      return item;
    });
    FragmentManager fm = getSupportFragmentManager();
    lsf = (ListFactory) fm.findFragmentById(R.id.ENEMIES_GRID_ID);
    if(lsf != null){
      lsf.setAdapter(adapter);
    }
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
    DataModel enemy = null;
    boolean isDeleted = false;
    int enemyId = 0;
    if(data != null){
      enemyId = data.getIntExtra("enemyId", 0);
      isDeleted = data.getBooleanExtra("deleted", false);
      enemy = !isDeleted ? enemyDBAdapter.getListByParentId(scriptId).get(enemyId) : null;
    }
    if(resultCode == RESULT_OK){
      if(requestCode == CREATE_NEW_ENEMY_CODE  && enemy != null){
        adapter.addItem(enemy, true);
      }
      if(requestCode == EDIT_ENEMY_CODE){
        if(isDeleted){
          adapter.deleteItem(enemyId);
        } else if(enemy != null) {
          adapter.updateItem(enemy);
        }
      }
      if(requestCode ==  ADD_MUSIC_TO_SCRIPT_CODE && data != null ){
        String[] selectedPaths = data.getStringArrayExtra(MusicSettingsScreen.SELECTED_LIST);
        scriptMediaSettings.update(selectedPaths, scriptId);
      }
    }
  }

  /**  */
  @Override
  public void onClick(View elementFiredAction, int id) {
    openEnemyDetails(id);
  }

  @Override
  public void onItemSelected(MenuItem selectedView) {}
}

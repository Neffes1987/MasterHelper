package com.example.com.masterhelper.scene.ui;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.components.dialogs.dialogs.DeleteDialog;
import com.example.com.masterhelper.core.components.dialogs.dialogs.InputDialog;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.media.adapters.MediaSettings;
import com.example.com.masterhelper.media.adapters.SettingsAdapterType;
import com.example.com.masterhelper.media.SettingsMediaFactory;
import com.example.com.masterhelper.media.ui.MusicSettingsScreen;
import com.example.com.masterhelper.enemies.ui.EnemiesListView;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.masterhelper.R;
import com.example.com.masterhelper.media.mediaworker.BackgroundMediaPlayer;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.core.listFactory.ListFactory;
import com.example.com.masterhelper.scene.models.ScriptModel;
import com.example.com.masterhelper.scene.adapters.ScriptDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.DialogInterface.BUTTON_POSITIVE;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Scene extends AppCompatActivity implements ICommonItemEvents {
  /** хелпер для работы с таблицей скриптов в бд */
  ScriptDBAdapter scriptDBAdapter = new ScriptDBAdapter();

  MediaSettings sceneMediaDBAdapter = SettingsMediaFactory.getAdapter(SettingsAdapterType.scene);

  /** указатель на кнопку создания нового скрипта сцены */
  FloatingActionButton newScriptBtn;

  /** указатель на кнопку проигрывания музыки */
  FloatingActionButton musicControl;

  ListFactory scriptsViewList;

  BackgroundMediaPlayer backgroundMediaPlayer = GlobalApplication.getBackgroundMediaPlayer();

  /** ид выбранной сцены */
  int sceneId;

  /** фоновая музыка проигрывается */
  boolean sceneMusicStarted = false;

  private final int ADD_MUSIC_TO_SCENE_CODE = 3;

  /** временный кеш для списка скриптов */
  private CommonAdapter adapter;

  DeleteDialog deleteDialog;
  InputDialog scriptDialog;

  private void setDialogs(){
    scriptDialog = new InputDialog(this, getSupportFragmentManager());
    scriptDialog.setCheckboxLabel(R.string.script_has_battle);
    deleteDialog = new DeleteDialog(this);
  }

  public void setSceneMusicStarted(boolean sceneMusicStarted) {
    this.sceneMusicStarted = sceneMusicStarted;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_screen_view_scene);

    String journeyName = getIntent().getStringExtra("journeyName");
    String sceneName = getIntent().getStringExtra("sceneName");
    sceneId = getIntent().getIntExtra("sceneId", 0);

    newScriptBtn = findViewById(R.id.SCRIPT_CREATE_NEW_BTN_ID);
    musicControl = findViewById(R.id.SCENE_MUSIC_START_ID);

    ActionBar bar = getSupportActionBar();


    newScriptBtn.setOnClickListener(v -> {
      scriptDialog.setTitle(R.string.script_create_title);
      scriptDialog.setOnResolveListener((d,w) -> {
        String name = scriptDialog.getName();
        String description = scriptDialog.getDescription();
        boolean isBattle = scriptDialog.getCheckboxValue();
        ScriptModel item = new ScriptModel(name, 0, description, isBattle, false);
        scriptDBAdapter.add(item, sceneId);
        adapter.addItem(item, true);
      });
      scriptDialog.show();
    });


    musicControl.setOnLongClickListener(v -> {
      Intent intent = new Intent(this, MusicSettingsScreen.class);
      intent.putExtra(MusicSettingsScreen.IS_GENERAL, 0);
      intent.putExtra(MusicSettingsScreen.SELECTED_LIST, getMediaList());
      startActivityForResult(intent, ADD_MUSIC_TO_SCENE_CODE);
      return true;
    });

    musicControl.setOnClickListener(v -> {
      boolean newSceneMusicState = !sceneMusicStarted;
      setSceneMusicStarted(newSceneMusicState);
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
    scriptsViewList = (ListFactory) fm.findFragmentById(R.id.SCREEN_SCRIPTS_LIST_ID);


    if(bar != null){
      bar.setSubtitle(R.string.screen_steps);
      bar.setTitle(journeyName + " -> " + sceneName);
    }
    setDialogs();
  }

  private String[] getMediaList(){
    ModelList mediaList = sceneMediaDBAdapter.get(sceneId);
    return sceneMediaDBAdapter.pathsToArray(mediaList);
  }

  /** обновление списка скриптов */
  void setListData(){
    adapter = new CommonAdapter(scriptDBAdapter.getListByParentId(sceneId), R.layout.fragment_view_list_item_script, this );
    adapter.setCommonItemInstanceGetter(currentAdapter -> {
      ScriptItem item = new ScriptItem();
      item.attachAdapter(currentAdapter);
      return item;
    });
    if(scriptsViewList != null && scriptsViewList.getView() != null){
      scriptsViewList.setAdapter(adapter);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    setListData();
  }

  /** обновить текущий скрипт  */
  public void onUpdateScriptNameButtonPressed(int id) {
    ScriptModel scriptModel = (ScriptModel) adapter.getItemById(id);
    scriptDialog.setTitle(R.string.script_update_title);
    scriptDialog.setOnResolveListener((d,w) -> {
      String name = scriptDialog.getName();
      String description = scriptDialog.getDescription();
      scriptModel.hasBattleActionIcon = scriptDialog.getCheckboxValue();
      scriptModel.setDescription(description);
      scriptModel.setName(name);
      scriptDBAdapter.update(scriptModel);
      adapter.updateItem(scriptModel);
    });
    scriptDialog.show(scriptModel);
    scriptDialog.setCheckboxValue(scriptModel.hasBattleActionIcon);
  }

  /** обработчик результата работы дочерних активностей по созданию или редактированию скриптов */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);
    if(resultCode != RESULT_OK){
      return;
    }
    if (requestCode == ADD_MUSIC_TO_SCENE_CODE) {
      String[] selectedPaths = result.getStringArrayExtra(MusicSettingsScreen.SELECTED_LIST);
      sceneMediaDBAdapter.update(selectedPaths, sceneId);
    }
  }

  /** обработчик нажатия на кнопки управления скриптом */
  @Override
  public void onClick(View elementFiredAction, int itemId) {
    ScriptModel currentData = (ScriptModel) adapter.getItemById(itemId);
    int btnId = elementFiredAction.getId();
    int currId = currentData.getId();
    switch (btnId){
      case R.id.SCRIPT_START_BTN_ID:
        if(currentData.hasBattleActionIcon){
          Intent intent = new Intent(this, EnemiesListView.class);
          intent.putExtra("scriptId", currId);
          intent.putExtra("scriptName", currentData.getName());
          startActivity(intent);
        }
        break;
      case  R.id.SCRIPT_EDIT_BTN_ID:
        onUpdateScriptNameButtonPressed(currId);
        break;
      case R.id.SCRIPT_DELETE_BTN_ID:
        deleteDialog.setOnResolveListener((dialogInterface, id) -> {
          if(id == BUTTON_POSITIVE){
            scriptDBAdapter.delete(currentData.getId());
            adapter.deleteItem(currentData.getId());
          }
        });
        deleteDialog.show();

        break;
      case R.id.SCRIPT_BTN_DONE_ID:
        boolean isFinished = !currentData.isFinished;
        currentData.setFinished(isFinished);
        scriptDBAdapter.update(currentData);
        adapter.updateItem(currentData);
        break;
    }
  }
}

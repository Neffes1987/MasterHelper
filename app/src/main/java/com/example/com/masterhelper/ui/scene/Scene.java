package com.example.com.masterhelper.ui.scene;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.ui.appBarFragment.MusicSettingsScreen;
import com.example.com.masterhelper.ui.enemies.EnemiesListView;
import com.example.com.masterhelper.core.appconfig.GlobalApplication;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.factorys.DBAdapters.AdaptersType;
import com.example.com.masterhelper.core.factorys.DBAdapters.DBAdapterFactory;
import com.example.com.masterhelper.core.factorys.DBAdapters.adapters.SceneDBAdapter;
import com.example.com.masterhelper.core.factorys.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factorys.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.mediaworker.BackgroundMediaPlayer;
import com.example.com.masterhelper.core.factorys.list.CustomListItemsEnum;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.core.factorys.list.ListFactory;
import com.example.com.masterhelper.core.appconfig.models.ScriptModel;
import com.example.com.masterhelper.core.factorys.DBAdapters.adapters.ScriptDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.DialogInterface.BUTTON_POSITIVE;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Scene extends AppCompatActivity implements ICommonItemEvents {
  /** хелпер для работы с таблицей скриптов в бд */
  ScriptDBAdapter scriptDBAdapter = (ScriptDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.script);

  /** хелпер для работы с таблицей сцен в бд */
  SceneDBAdapter sceneDBAdapter = (SceneDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.scene);

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

  private final int CREATE_NEW_SCRIPT_CODE = 1;
  private final int UPDATE_SCRIPT_CODE = 2;
  private final int ADD_MUSIC_TO_SCENE_CODE = 3;

  /** временный кеш для списка скриптов */
  public LinkedHashMap<Integer, ScriptModel> scriptsList = new LinkedHashMap<>();

  public void setSceneMusicStarted(boolean sceneMusicStarted) {
    this.sceneMusicStarted = sceneMusicStarted;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_scene);

    String journeyName = getIntent().getStringExtra("journeyName");
    String sceneName = getIntent().getStringExtra("sceneName");
    sceneId = getIntent().getIntExtra("sceneId", 0);

    newScriptBtn = findViewById(R.id.SCRIPT_CREATE_NEW_BTN_ID);
    musicControl = findViewById(R.id.SCENE_MUSIC_START_ID);

    ActionBar bar = getSupportActionBar();


    newScriptBtn.setOnClickListener(v -> {
      Intent intent = new Intent(this, CreateNewItemDialog.class);
      intent.putExtra(CreateNewItemDialog.TITLE, R.string.script_create_title);
      intent.putExtra(CreateNewItemDialog.IS_SCRIPT, 1);
      startActivityForResult(intent, CREATE_NEW_SCRIPT_CODE);
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
  }

  private String[] getMediaList(){
    HashMap<String, Integer> mediaList = sceneDBAdapter.getMediaForScene(sceneId);
    return mediaList.keySet().toArray(new String[0]);
  }

  /** обновление списка скриптов */
  void setListData(){
    scriptsList = scriptDBAdapter.getListByParentId(sceneId);
    if(scriptsViewList != null && scriptsViewList.getView() != null){
      scriptsViewList.updateListAdapter(scriptsList, CustomListItemsEnum.script);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    setListData();
  }

  /** обновить текущий скрипт  */
  public void onUpdateScriptNameButtonPressed(int id) {
    ScriptModel scriptModel = scriptsList.get(id);
    Intent intent = new Intent(this, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.TITLE, R.string.script_update_title);
    intent.putExtra(CreateNewItemDialog.IS_UPDATE, 1);
    intent.putExtra(CreateNewItemDialog.ID, id);
    if(scriptModel != null){
      intent.putExtra(CreateNewItemDialog.OLD_NAME, scriptModel.getTitle());
      intent.putExtra(CreateNewItemDialog.IS_BATTLE, scriptModel.hasBattleActionIcon ? 1 : 0);
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, scriptModel.getDescription());
      intent.putExtra(CreateNewItemDialog.IS_SCRIPT, 1);
      startActivityForResult(intent, UPDATE_SCRIPT_CODE);
    }
  }

  /** обработчик результата работы дочерних активностей по созданию или редактированию скриптов */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);
    if(resultCode != RESULT_OK){
      return;
    }
    String newName = result.getStringExtra(CreateNewItemDialog.NAME);
    int hasBattleSceneValue = result.getIntExtra(CreateNewItemDialog.IS_BATTLE, 0);
    String description = result.getStringExtra(CreateNewItemDialog.DESCRIPTION);
    int id = result.getIntExtra(CreateNewItemDialog.ID, 0);

    if(newName != null && newName.trim().length() == 0){
      return;
    }

    ScriptModel item;

    if(id > 0){
      item = scriptDBAdapter.get(id);
      item.setTitle(newName);
      item.setDescription(description);
      item.hasBattleActionIcon = hasBattleSceneValue == 1;
    } else {
      item = new ScriptModel(newName, id, description, hasBattleSceneValue == 1, false);
    }
    switch (requestCode){
      case CREATE_NEW_SCRIPT_CODE:
        scriptDBAdapter.add(item, sceneId);
        break;
      case UPDATE_SCRIPT_CODE:
        scriptDBAdapter.update(item);
        break;
      case ADD_MUSIC_TO_SCENE_CODE:
        String[] selectedPaths = result.getStringArrayExtra(MusicSettingsScreen.SELECTED_LIST);
        sceneDBAdapter.updateSceneMedia(selectedPaths, sceneId);
        break;
    }
    setListData();
  }

  /** обработчик нажатия на кнопки управления скриптом */
  @Override
  public void onClick(View elementFiredAction, int position) {
    ScriptModel currentData = (ScriptModel) scriptsList.values().toArray()[position];
    int btnId = elementFiredAction.getId();
    int currId = currentData.getId();
    switch (btnId){
      case R.id.SCRIPT_START_BTN_ID:
        if(currentData.hasBattleActionIcon){
          Intent intent = new Intent(this, EnemiesListView.class);
          intent.putExtra("scriptId", currId);
          intent.putExtra("scriptName", currentData.getTitle());
          startActivity(intent);
        }
        break;
      case  R.id.SCRIPT_EDIT_BTN_ID:
        onUpdateScriptNameButtonPressed(currId);
        break;
      case R.id.SCRIPT_DELETE_BTN_ID:
        CommonDialog dialog = DialogsFactory.createDialog(DialogTypes.delete);
        if(dialog != null){
          dialog.setOnResolveListener((dialogInterface, id) -> {
            if(id == BUTTON_POSITIVE){
              scriptDBAdapter.delete(currentData.getId());
              setListData();
            }
          });
          dialog.show(this);
        }

        break;
      case R.id.SCRIPT_BTN_DONE_ID:
        boolean isFinished = !currentData.isFinished;
        currentData.setFinished(isFinished);
        scriptDBAdapter.update(currentData);
        setListData();
        break;
    }
  }
}

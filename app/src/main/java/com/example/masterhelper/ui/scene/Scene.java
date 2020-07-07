package com.example.masterhelper.ui.scene;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.CreateNewItemDialog;
import com.example.masterhelper.DialogPopup;
import com.example.masterhelper.R;
import com.masterhelper.mediaworker.BackgroundMediaPlayer;
import com.example.masterhelper.ui.app.settings.MusicSettingsScreen;
import com.example.masterhelper.ui.enemies.EnemiesListView;
import com.masterhelper.listFactory.CustomListItemsEnum;
import com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.masterhelper.listFactory.ListFactory;
import com.example.masterhelper.models.ScriptRecycleDataModel;
import com.example.masterhelper.ui.scripts.ScriptDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.DialogInterface.BUTTON_POSITIVE;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Scene extends AppCompatActivity implements ICommonItemEvents {
  /** хелпер для работы с таблицей скриптов в бд */
  ScriptDBAdapter scriptDBAdapter;

  /** хелпер для работы с таблицей сцен в бд */
  SceneDBAdapter sceneDBAdapter;

  /** указатель на кнопку создания нового скрипта сцены */
  FloatingActionButton newScriptBtn;

  /** указатель на кнопку проигрывания музыки */
  FloatingActionButton musicControl;

  ListFactory<ScriptRecycleDataModel> scriptsViewList;

  BackgroundMediaPlayer backgroundMediaPlayer = BackgroundMediaPlayer.getInstance();

  /** ид выбранной сцены */
  int sceneId;

  /** фоновая музыка проигрывается */
  boolean sceneMusicStarted = false;

  private final int CREATE_NEW_SCRIPT_CODE = 1;
  private final int UPDATE_SCRIPT_CODE = 2;
  private final int ADD_MUSIC_TO_SCENE_CODE = 3;

  /** временный кеш для списка скриптов */
  public LinkedHashMap<Integer, ScriptRecycleDataModel> scriptsList = new LinkedHashMap<>();

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

    sceneDBAdapter  = new SceneDBAdapter();


    newScriptBtn = findViewById(R.id.SCRIPT_CREATE_NEW_BTN_ID);
    musicControl = findViewById(R.id.SCENE_MUSIC_START_ID);

    scriptDBAdapter  = new ScriptDBAdapter();
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
    scriptsViewList = (ListFactory<ScriptRecycleDataModel>) fm.findFragmentById(R.id.SCREEN_SCRIPTS_LIST_ID);


    if(bar != null){
      bar.setSubtitle(journeyName);
      bar.setTitle(sceneName);
    }
  }

  private String getMediaList(){
    HashMap<String, Integer> mediaList = sceneDBAdapter.getMediaForScene(sceneId);
    return mediaList.size() > 0 ? mediaList.keySet().toString().replaceAll("\\[|]|\\s", "") : "";
  }

  /** обновление списка скриптов */
  void setListData(){
    scriptsList = scriptDBAdapter.getScriptsList(sceneId);
    if(scriptsViewList != null && scriptsViewList.getView() != null){
      scriptsViewList.setItemType(CustomListItemsEnum.script);
      scriptsViewList.updateListAdapter(scriptsList);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    setListData();
  }

  /** обновить текущий скрипт  */
  public void onUpdateScriptNameButtonPressed(int id) {
    ScriptRecycleDataModel scriptRecycleDataModel = scriptsList.get(id);
    Intent intent = new Intent(this, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.TITLE, R.string.script_update_title);
    intent.putExtra(CreateNewItemDialog.IS_UPDATE, 1);
    intent.putExtra(CreateNewItemDialog.ID, id);
    if(scriptRecycleDataModel != null){
      intent.putExtra(CreateNewItemDialog.OLD_NAME, scriptRecycleDataModel.getTitle());
      intent.putExtra(CreateNewItemDialog.IS_BATTLE, scriptRecycleDataModel.hasBattleActionIcon ? 1 : 0);
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, scriptRecycleDataModel.getDescription());
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

    ScriptRecycleDataModel item;

    if(id > 0){
      item = scriptDBAdapter.getScriptById(id);
      item.setTitle(newName);
      item.setDescription(description);
      item.hasBattleActionIcon = hasBattleSceneValue == 1;
    } else {
      item = new ScriptRecycleDataModel(newName, id, description, hasBattleSceneValue == 1, false);
    }
    switch (requestCode){
      case CREATE_NEW_SCRIPT_CODE:
        scriptDBAdapter.addNewScript(item, sceneId);
        break;
      case UPDATE_SCRIPT_CODE:
        scriptDBAdapter.updateScript(item, id);
        break;
      case ADD_MUSIC_TO_SCENE_CODE:
        String selectedPaths = result.getStringExtra(MusicSettingsScreen.SELECTED_LIST);
        sceneDBAdapter.updateSceneMedia(selectedPaths, sceneId);
        break;
    }
    setListData();
  }

  /** обработчик нажатия на кнопки управления скриптом */
  @Override
  public void onClick(View elementFiredAction, int position) {
    ScriptRecycleDataModel currentData = (ScriptRecycleDataModel) scriptsList.values().toArray()[position];
    int btnId = elementFiredAction.getId();
    int currId = currentData.getId();
    switch (btnId){
      case R.id.SCRIPT_START_BTN_ID:
        if(currentData.hasBattleActionIcon){
          Intent intent = new Intent(this, EnemiesListView.class);
          intent.putExtra("scriptId", currId);
          startActivity(intent);
        }
        break;
      case  R.id.SCRIPT_EDIT_BTN_ID:
        onUpdateScriptNameButtonPressed(currId);
        break;
      case R.id.SCRIPT_DELETE_BTN_ID:
        DialogPopup dialogPopup = new DialogPopup(getSupportFragmentManager());
        dialogPopup.setClickListener((dialogInterface, id) -> {
          if(id == BUTTON_POSITIVE){
            scriptDBAdapter.deleteScript(currentData.getId());
            setListData();
          }
        });
        dialogPopup.show();
        break;
      case R.id.SCRIPT_BTN_DONE_ID:
        boolean isFinished = !currentData.isFinished;
        currentData.setFinished(isFinished);
        scriptDBAdapter.updateScript(currentData, currentData.getId());
        setListData();
        break;
    }
  }
}

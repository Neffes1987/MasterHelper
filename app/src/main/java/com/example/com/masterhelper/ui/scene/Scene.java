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
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.media.adapters.MediaSettings;
import com.example.com.masterhelper.media.adapters.SettingsAdapterType;
import com.example.com.masterhelper.media.SettingsMediaFactory;
import com.example.com.masterhelper.media.ui.MusicSettingsScreen;
import com.example.com.masterhelper.ui.enemies.EnemiesListView;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.factories.dialogs.ui.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.factories.DBAdapters.AdaptersType;
import com.example.com.masterhelper.core.factories.DBAdapters.DBAdapterFactory;
import com.example.com.masterhelper.core.factories.DBAdapters.adapters.SceneDBAdapter;
import com.example.com.masterhelper.core.factories.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factories.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factories.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.media.mediaworker.BackgroundMediaPlayer;
import com.example.com.masterhelper.listFactory.CustomListItemsEnum;
import com.example.com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.listFactory.ListFactory;
import com.example.com.masterhelper.core.models.ScriptModel;
import com.example.com.masterhelper.core.factories.DBAdapters.adapters.ScriptDBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.example.com.masterhelper.core.factories.dialogs.DialogTypes.scriptDialog;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Scene extends AppCompatActivity implements ICommonItemEvents {
  /** хелпер для работы с таблицей скриптов в бд */
  ScriptDBAdapter scriptDBAdapter = (ScriptDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.script);

  /** хелпер для работы с таблицей сцен в бд */
  SceneDBAdapter sceneDBAdapter = (SceneDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.scene);
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

  private final int CREATE_NEW_SCRIPT_CODE = 1;
  private final int UPDATE_SCRIPT_CODE = 2;
  private final int ADD_MUSIC_TO_SCENE_CODE = 3;

  /** временный кеш для списка скриптов */
  public ModelList scriptsList = new ModelList();

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
      CommonDialog dialog = DialogsFactory.createDialog(scriptDialog);
      if(dialog != null){
        dialog.setTitle(R.string.script_create_title);
        dialog.show(this, null);
      }
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
    ModelList mediaList = sceneMediaDBAdapter.get(sceneId);
    return sceneMediaDBAdapter.pathsToArray(mediaList);
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
    ScriptModel scriptModel = (ScriptModel) scriptsList.get(id);
    CommonDialog dialog = DialogsFactory.createDialog(scriptDialog);
    if(dialog != null){
      dialog.setTitle(R.string.script_update_title);
      dialog.show(this, scriptModel);
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
      item.setName(newName);
      item.setDescription(description);
      item.hasBattleActionIcon = hasBattleSceneValue == 1;
    } else {
      item = new ScriptModel(newName, id, description, hasBattleSceneValue == 1, false);
    }
    switch (requestCode){
      case CommonDialog.DIALOG_CREATE_ACTIVITY_RESULT:
        scriptDBAdapter.add(item, sceneId);
        break;
      case CommonDialog.DIALOG_UPDATE_ACTIVITY_RESULT:
        scriptDBAdapter.update(item);
        break;
      case ADD_MUSIC_TO_SCENE_CODE:
        String[] selectedPaths = result.getStringArrayExtra(MusicSettingsScreen.SELECTED_LIST);
        sceneMediaDBAdapter.update(selectedPaths, sceneId);
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
          intent.putExtra("scriptName", currentData.getName());
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
          dialog.show(this, null);
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

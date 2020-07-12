package com.example.com.masterhelper.ui.appBarFragment;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.factorys.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factorys.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.core.mediaworker.MediaFiles;
import com.example.com.masterhelper.ui.soundsList.SoundsList;
import com.example.com.masterhelper.core.appconfig.models.SoundFileModel;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class MusicSettingsScreen extends AppCompatActivity implements ICommonItemEvents {
  private static final int PICK_AUDIO_FILE = 1;
  private static final String PICK_AUDIO_TYPE = "audio/*";

  public static final String SELECTED_LIST = "selectedList";
  public static final String IS_GENERAL = "isGeneral";
  private boolean isGeneral;
  private int EMPTY_SOUND_POSITION = -1;
  private int currentPlayedMusic = EMPTY_SOUND_POSITION;
  private SoundsList soundListFragment;
  private HashSet<String> selectedList = new HashSet<>();

  public void setSelectedList() {
    selectedList.clear();
    String[] listIds = getIntent().getStringArrayExtra(SELECTED_LIST);
    if(listIds != null){
      Collections.addAll(selectedList, listIds);
    }
  }

  public void setGeneral() {
    isGeneral = getIntent().getIntExtra(IS_GENERAL, 1) == 1;
  }

  /** утилита для работы с медиафайлами */
  private MediaFiles mediaFiles;

  /** набор пермишенов которые надо явно запросить у пользователя чтобы загружать в прилагу файлы */
  String[] permissions = new String[] {
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_music_settings_screen);
    mediaFiles = new MediaFiles();
    setGeneral();
    setSelectedList();
    updateViewList();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mediaFiles.stopMediaRecord();
  }

  /** запустить внешний экран выбора файла для добавления в галерею */
  @Override
  public void onClick(View elementFiredAction, int position) {
    if(position == SoundsList.ADD_MUSIC_BTN){
      if(Build.VERSION.SDK_INT>22){
        requestPermissions(permissions, 1);
        return;
      }
      StartFilePickerIntent();
    } else if(position == SoundsList.ATTACH_MUSIC_BTN){
      Intent intent = new Intent();
      intent.putExtra(SELECTED_LIST, selectedList.toArray(new String[0]));
      setResult(RESULT_OK, intent);
      finish();
    } else {
      switch (elementFiredAction.getId()){
        case R.id.RUN_MUSIC_FILE_ID:
          if(currentPlayedMusic != EMPTY_SOUND_POSITION){
            soundListFragment.updateAdapterItem(currentPlayedMusic);
          }
          currentPlayedMusic = position;
          mediaFiles.startMediaRecord(position);
          break;
          case R.id.STOP_MUSIC_FILE_ID:
            currentPlayedMusic = EMPTY_SOUND_POSITION;
            mediaFiles.stopMediaRecord();
          break;
        case R.id.MUSIC_DELETE_ROW_ID:
          CommonDialog dialog = DialogsFactory.createDialog(DialogTypes.delete);
          if(dialog != null){
            dialog.setOnResolveListener((dialogInterface, id) -> {
              if(id == BUTTON_POSITIVE){
                mediaFiles.deleteMedeaRecord(position);
                updateViewList();
              }
            });
            dialog.show(this);
          }
          break;
        case R.id.FILE_NAME_SELECTOR_ID:
          String selectedFilePath = mediaFiles.getFileByPosition(position).getPath();
          if(selectedList.contains(selectedFilePath)){
            selectedList.remove(selectedFilePath);
          } else {
            selectedList.add(selectedFilePath);
          }
          break;
      }
    }
  }

  /** проверка пермигенов для добавления файлов из области загрузки */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == 1) {
      if (!(grantResults.length > 0
        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
        Toast.makeText(this, "Permission denied to access your location.", Toast.LENGTH_SHORT).show();
        return;
      }
      StartFilePickerIntent();
    }
  }

  /** метод запуска экрана выбора файлов из активити приложения */
  private void StartFilePickerIntent(){
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType(PICK_AUDIO_TYPE);
    startActivityForResult(intent, PICK_AUDIO_FILE);
  }

  /** получаем список файлов, которые пользователь пожелал добавить в галлерею */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    HashSet<Uri> selectedFilesPaths = new HashSet<>();
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode != RESULT_OK){
      return;
    }

    if(requestCode == PICK_AUDIO_FILE){
      assert data != null;
      ClipData clipData = data.getClipData();
      if(clipData == null){
        selectedFilesPaths.add(data.getData());
      } else {
        for(int i=0; i<clipData.getItemCount(); i++){
          ClipData.Item item = clipData.getItemAt(i);
          Uri uri = item.getUri();
          selectedFilesPaths.add(uri);
        }
      }
      mediaFiles.addFilesToLibrary(selectedFilesPaths);
      updateViewList();
    }
  }

  /** метод обновляет список добавленных данных на вьюхе */
  private void updateViewList() {
    ModelList newData = new ModelList();

    mediaFiles.getFilesList().forEach(fileInList -> {
      boolean isSelected = selectedList.contains(fileInList.getPath());
      newData.addToList(new SoundFileModel(fileInList.getName(), fileInList.lastModified(), fileInList.getPath(), newData.size(), isSelected)
      );
    });

    FragmentManager fragmentManager = getSupportFragmentManager();
    soundListFragment = (SoundsList) fragmentManager.findFragmentById(R.id.SOUND_LIST_FRAGMENT_ID);
    assert soundListFragment != null;
    soundListFragment.updateListAdapter(newData, isGeneral);
  }

}


package com.example.masterhelper.ui.app.settings;

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
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.mediaworker.MediaFiles;
import com.example.masterhelper.ui.soundsList.SoundsList;
import com.example.masterhelper.models.SoundFileModel;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class MusicSettingsScreen extends AppCompatActivity implements ICommonItemEvents {
  private static final int PICK_AUDIO_FILE = 1;
  private static final String PICK_AUDIO_TYPE = "audio/*";
  private static final int MAX_AUDIO_STREAMS = 1;
  private static final int STARTED_AUDIO_PRIORITY = 1;

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
    mediaFiles = new MediaFiles(this, MAX_AUDIO_STREAMS);
    updateViewList();
  }

  /** запустить внешний экран выбора файла для добавления в галерею */
  @Override
  public void onClick(View elementFiredAction, int position) {
    if(position == -1){
      if(Build.VERSION.SDK_INT>22){
        requestPermissions(permissions, 1);
        return;
      }
      StartFilePickerIntent();
    } else {
      if (elementFiredAction.getId() == R.id.RUN_MUSIC_FILE_ID){
        mediaFiles.startMediaRecord(position, STARTED_AUDIO_PRIORITY);
      }

      if (elementFiredAction.getId() == R.id.MUSIC_DELETE_ROW_ID){
        mediaFiles.deleteMedeaRecord(position);
        updateViewList();
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
    LinkedHashMap<Integer, SoundFileModel> newData = new LinkedHashMap<>();

    mediaFiles.getFilesList().forEach(fileInList -> newData.put(newData.size(),
      new SoundFileModel(fileInList.getName(), fileInList.lastModified(), fileInList.getPath(), newData.size())
    ));

    FragmentManager fragmentManager = getSupportFragmentManager();
    SoundsList soundListFragment = (SoundsList) fragmentManager.findFragmentById(R.id.SOUND_LIST_FRAGMENT_ID);
    assert soundListFragment != null;
    soundListFragment.updateListAdapter(newData, true);
  }

}


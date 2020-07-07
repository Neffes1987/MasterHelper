package com.masterhelper.mediaworker;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.Toast;
import com.example.masterhelper.GlobalApplication;

import java.io.*;
import java.util.HashSet;

public class MediaFiles {
  /** путь до деректории с медиафайлами приложения */
  private File currentFilesDir;

  /** список медиа файлов  */
  HashSet<File> filesList = new HashSet<>();

  /** указатель на контекст активити */
  Context context = GlobalApplication.getAppContext();

  /** указатель на утилиту проигрывания медиа файла */
  private static MediaPlayerAdapter mediaPlayer;

  /** конструктор утилиты */
  public MediaFiles(){
     currentFilesDir = context.getFilesDir();
     mediaPlayer = new MediaPlayerAdapter(true);
  }

  /** обновить список файлов в деректории */
  public void updateFilesList() {
    File[] list = currentFilesDir.listFiles();
    HashSet<File> audioList = new HashSet<>();
    assert list != null;
    int listlength = list.length - 1;
    int pos = 0;
    while (pos < listlength){
      pos += 1;
      audioList.add(list[pos]);
    }
    this.filesList = audioList;
  }

  /** получить имя файла через ури */
  private String getOriginalFileName(Uri path){
   Cursor cursor = context.getContentResolver()
     .query(path, null, null, null, null, null);
   String displayName = "file";
   try {
     if (cursor != null && cursor.moveToFirst()) {
       displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
     }
   } catch (Exception e) {
     e.printStackTrace();
   }
    assert cursor != null;
    cursor.close();
    return displayName;
 }

  /** копирует файл по переданному указателю в медиа деректорию приложения */
 private void addSingleFileToLibrary(Uri path) {
   // берем имя файла
   String fileName = getOriginalFileName(path);

   // формируем путь для файла в дериктории приложения
   File appFile =  new File(currentFilesDir.getPath() + "/" + fileName);

   // получаем хелпер для работы с файлами
   ContentResolver cr = context.getContentResolver();
   try (InputStream in = cr.openInputStream(path)) {
     try (OutputStream out = new FileOutputStream(appFile)) {
       byte[] buf = new byte[1024];
       int len;
       while ((len = in.read(buf)) > 0) {
         out.write(buf, 0, len);
       }
     }
   } catch (IOException e) {
     e.printStackTrace();
   }
 }

  /** сохранить все переденнаые из активити файлы в дерикторию приложения */
  public void addFilesToLibrary(HashSet<Uri> filesPaths) {
     if(filesPaths == null){
       return;
     }
    for (Uri path : filesPaths) {
      addSingleFileToLibrary(path);
    }
     updateFilesList();
  }

  /** отдать фктуальный список файлов в дериктории приложения */
  public HashSet<File> getFilesList() {
    updateFilesList();
    return filesList;
  }

  public File getFileByPosition(int position){
    return (File) filesList.toArray()[position];
  }

  public void startMediaRecord(int position) {
    File file = getFileByPosition(position);
    mediaPlayer.startMediaRecord(file);
  }

  public void stopMediaRecord(){
    mediaPlayer.stopMediaList();
  }

  public void deleteMedeaRecord(int position) {
    File file = getFileByPosition(position);
    filesList.remove(file);
    mediaPlayer.stopMediaList();
    if(file.delete()){
      context.deleteFile(file.getName());
      Toast.makeText(context, "Файл Удален", Toast.LENGTH_LONG).show();
      updateFilesList();
    }
  }
}

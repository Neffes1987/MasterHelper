package com.example.masterhelper.mediaworker;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.*;
import java.util.HashSet;

public class MediaFiles {
  /** тег для дебага */
  private String TAG = "MediaFiles";

  /** путь до деректории с медиафайлами приложения */
  private File currentFilesDir;

  /** список медиа файлов  */
  HashSet<File> filesList = new HashSet<>();

  /** указатель на контекст активити */
  Context context;

  /** указатель на утилиту проигрывания медиа файла */
  SoundPool sp;

  int loadedSPFile;
  int loadedSPFilePriority;


  /** конструктор утилиты */
  public MediaFiles(Context context, int maxStreams){
     this.context = context;
     currentFilesDir = context.getFilesDir();
     SoundPool.Builder spb  = new SoundPool.Builder();
     spb.setMaxStreams(maxStreams);
     sp = spb.build();
  }

  /** обновить список файлов в деректории */
  public void setFilesList() {
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
     setFilesList();
  }

  /** отдать фктуальный список файлов в дериктории приложения */
  public HashSet<File> getFilesList() {
    setFilesList();
    return filesList;
  }

  public void startMediaRecord(int position, int priority) {
    File file = (File) filesList.toArray()[position];
    sp.setOnLoadCompleteListener(listener);
    loadedSPFilePriority = priority;
    loadedSPFile = sp.load(file.getPath(), 1);
  }
  
  OnLoadCompleteListener listener = new OnLoadCompleteListener() {
    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
      sp.play(loadedSPFile, 1, 1, loadedSPFilePriority, 0, 1);
    }
  };
  
}

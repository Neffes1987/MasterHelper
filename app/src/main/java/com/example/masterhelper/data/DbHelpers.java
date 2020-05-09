package com.example.masterhelper.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import com.example.masterhelper.data.contracts.Journeys;

import java.util.HashSet;

public class DbHelpers extends SQLiteOpenHelper {
  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 1;


  /**
   * @param context Контекст приложения
   */
  public DbHelpers(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Вызывается при создании базы данных
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    // Строка для создания таблицы
    String SQL_CREATE_JOURNEYS_TABLE = Journeys.CREATE_TABLE;

    // Запускаем создание таблицы
    db.execSQL(SQL_CREATE_JOURNEYS_TABLE);
  }

  /**
   * Вызывается при обновлении схемы базы данных
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  public Cursor getList(String query){
    HashSet<Bundle> result = new HashSet<>();
    // Создадим и откроем для чтения базу данных
    SQLiteDatabase db = getReadableDatabase();
    return db.rawQuery(query, null);
  }

  public int addNewItem(String insertQuery){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(insertQuery);
    return 1;
  }

}

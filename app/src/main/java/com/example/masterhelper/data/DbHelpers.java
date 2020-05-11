package com.example.masterhelper.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.masterhelper.data.contracts.JourneysContract;
import com.example.masterhelper.data.contracts.SceneContract;
import com.example.masterhelper.data.contracts.ScriptsContract;

public class DbHelpers extends SQLiteOpenHelper {
  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 8;

  SQLiteDatabase db;

  private void generateJourneyTable(){
    db.execSQL("DROP TABLE IF EXISTS "+JourneysContract.TABLE_NAME);
    String SQL_CREATE_TABLE = JourneysContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScenesTable(){
    db.execSQL("DROP TABLE IF EXISTS "+SceneContract.TABLE_NAME);
    String SQL_CREATE_TABLE = SceneContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScriptsTable(){
    db.execSQL("DROP TABLE IF EXISTS "+ScriptsContract.TABLE_NAME);
    String SQL_CREATE_TABLE = ScriptsContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }


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
    this.db = db;
    // Строка для создания таблицы
    generateJourneyTable();
    generateScenesTable();
    generateScriptsTable();
  }

  /**
   * Вызывается при обновлении схемы базы данных
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    this.db = db;
    generateJourneyTable();
    generateScenesTable();
    generateScriptsTable();
  }

  public Cursor getList(String query){
    // Создадим и откроем для чтения базу данных
    SQLiteDatabase db = getReadableDatabase();
    return db.rawQuery(query, null);
  }

  public int addNewItem(String insertQuery){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(insertQuery);
    return 1;
  }

  public int deleteItem(String deleteQuery){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(deleteQuery);
    return 1;
  }

  public int updateItem(String updateQuery){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(updateQuery);
    return 1;
  }

}

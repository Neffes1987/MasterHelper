package com.example.masterhelper.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.masterhelper.data.contracts.*;

public class DbHelpers extends SQLiteOpenHelper {
  private String SQLCreateTemplate ="DROP TABLE IF EXISTS ";
  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 10;

  SQLiteDatabase db;

  private void generateJourneyTable(){
    db.execSQL(SQLCreateTemplate + JourneysContract.TABLE_NAME);
    String SQL_CREATE_TABLE = JourneysContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScenesTable(){
    db.execSQL(SQLCreateTemplate + SceneContract.TABLE_NAME);
    String SQL_CREATE_TABLE = SceneContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScriptsTable(){
    db.execSQL(SQLCreateTemplate + ScriptsContract.TABLE_NAME);
    String SQL_CREATE_TABLE = ScriptsContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateEnemyTable(){
    db.execSQL(SQLCreateTemplate + EnemyContract.TABLE_NAME);
    String SQL_CREATE_TABLE = EnemyContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateAchieveTable(){
    db.execSQL(SQLCreateTemplate + AchievesContract.TABLE_NAME);
    String SQL_CREATE_TABLE = AchievesContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScriptSettingsTable(){
    db.execSQL(SQLCreateTemplate + ScriptSettingsContract.TABLE_NAME);
    String SQL_CREATE_TABLE = ScriptSettingsContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateEnemySettingsTable(){
    db.execSQL(SQLCreateTemplate + EnemySettingsContract.TABLE_NAME);
    String SQL_CREATE_TABLE = EnemySettingsContract.CREATE_TABLE;
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
    generateEnemyTable();
    generateAchieveTable();
    generateScriptSettingsTable();
    generateEnemySettingsTable();
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
    generateEnemyTable();
    generateAchieveTable();
    generateScriptSettingsTable();
    generateEnemySettingsTable();
  }

  public Cursor getList(String query){
    // Создадим и откроем для чтения базу данных
    SQLiteDatabase db = getReadableDatabase();
    return db.rawQuery(query, null);
  }

  public void addNewItem(String insertQuery){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(insertQuery);
  }

  public void deleteItem(String deleteQuery){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(deleteQuery);
  }

  public void updateItem(String updateQuery){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(updateQuery);
  }

}

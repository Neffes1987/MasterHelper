package com.masterhelper.appconfig;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.masterhelper.appconfig.contracts.*;

public class DbHelpers extends SQLiteOpenHelper {
  private String SQLCreateTemplate ="DROP TABLE IF EXISTS ";

  public AbilitiesContract  abilitiesContract = new AbilitiesContract();
  public EnemyAbilitiesContract enemyAbilitiesContract = new EnemyAbilitiesContract();
  public EnemyContract enemyContract = new EnemyContract();
  public JourneysContract journeysContract = new JourneysContract();
  public ScriptsContract scriptsContract = new ScriptsContract();
  public SceneContract sceneContract =new SceneContract();
  public SceneMusicContract sceneMusicContract = new SceneMusicContract();
  public ScriptMusicContract scriptMusicContract = new ScriptMusicContract();


  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 28;

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

  private void generateScenesMusicTable(){
    db.execSQL(SQLCreateTemplate + SceneMusicContract.TABLE_NAME);
    String SQL_CREATE_TABLE = SceneMusicContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScriptMusicTable(){
    db.execSQL(SQLCreateTemplate + ScriptMusicContract.TABLE_NAME);
    String SQL_CREATE_TABLE = ScriptMusicContract.CREATE_TABLE;
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

  private void generateAbilitiesTable(){
    db.execSQL(SQLCreateTemplate + AbilitiesContract.TABLE_NAME);
    String SQL_CREATE_TABLE = AbilitiesContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateEnemyAbilitiesTable(){
    db.execSQL(SQLCreateTemplate + EnemyAbilitiesContract.TABLE_NAME);
    String SQL_CREATE_TABLE = EnemyAbilitiesContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_TABLE);
  }

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
    generateAbilitiesTable();
    generateEnemyAbilitiesTable();
    generateScenesMusicTable();
    generateScriptMusicTable();
  }

  @Override
  public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
    if (!db.isReadOnly()) {
      // Enable foreign key constraints
      db.execSQL("PRAGMA foreign_keys=ON;");
    }
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
    generateAbilitiesTable();
    generateEnemyAbilitiesTable();
    generateScenesMusicTable();
    generateScriptMusicTable();
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

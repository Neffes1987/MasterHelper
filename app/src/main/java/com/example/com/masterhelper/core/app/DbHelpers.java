package com.example.com.masterhelper.core.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.enemies.EnemyAbilitiesContract;
import com.example.com.masterhelper.core.contracts.enemies.EnemyContract;
import com.example.com.masterhelper.core.contracts.enemies.ScriptMusicContract;
import com.example.com.masterhelper.core.contracts.enemies.ScriptsContract;
import com.example.com.masterhelper.core.contracts.journey.JourneysContract;
import com.example.com.masterhelper.core.contracts.scene.SceneContract;
import com.example.com.masterhelper.core.contracts.scene.SceneMusicContract;
import com.example.com.masterhelper.core.contracts.settings.AbilitiesContract;
import com.example.com.masterhelper.core.contracts.settings.AdvanceContract;
import com.example.com.masterhelper.core.contracts.settings.GoalContract;

public class DbHelpers extends SQLiteOpenHelper {
  private String SQLCreateTemplate ="DROP TABLE IF EXISTS ";

  public GeneralContract abilitiesContract = new AbilitiesContract().getContract();
  public GeneralContract enemyAbilitiesContract = new EnemyAbilitiesContract().getContract();
  public GeneralContract enemyContract = new EnemyContract().getContract();
  public GeneralContract journeysContract = new JourneysContract().getContract();
  public GeneralContract scriptsContract = new ScriptsContract().getContract();
  public GeneralContract sceneContract =new SceneContract().getContract();
  public GeneralContract sceneMusicContract = new SceneMusicContract().getContract();
  public GeneralContract scriptMusicContract = new ScriptMusicContract().getContract();
  public GeneralContract goalContract = new GoalContract().getContract();
  public GeneralContract advanceContract = new AdvanceContract().getContract();


  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 31;

  SQLiteDatabase db;

  private void generateJourneyTable(){
    db.execSQL(SQLCreateTemplate + journeysContract.getTableName());
    String SQL_CREATE_TABLE = journeysContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateGoalsTable(){
    db.execSQL(SQLCreateTemplate + goalContract.getTableName());
    String SQL_CREATE_TABLE = goalContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScenesTable(){
    db.execSQL(SQLCreateTemplate + sceneContract.getTableName());
    String SQL_CREATE_TABLE = sceneContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScenesMusicTable(){
    db.execSQL(SQLCreateTemplate + sceneMusicContract.getTableName());
    String SQL_CREATE_TABLE = sceneMusicContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScriptMusicTable(){
    db.execSQL(SQLCreateTemplate + scriptMusicContract.getTableName());
    String SQL_CREATE_TABLE = scriptMusicContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateScriptsTable(){
    db.execSQL(SQLCreateTemplate + scriptsContract.getTableName());
    String SQL_CREATE_TABLE = scriptsContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateEnemyTable(){
    db.execSQL(SQLCreateTemplate + enemyContract.getTableName());
    String SQL_CREATE_TABLE = enemyContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateAbilitiesTable(){
    db.execSQL(SQLCreateTemplate + abilitiesContract.getTableName());
    String SQL_CREATE_TABLE = abilitiesContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateEnemyAbilitiesTable(){
    db.execSQL(SQLCreateTemplate + enemyAbilitiesContract.getTableName());
    String SQL_CREATE_TABLE = enemyAbilitiesContract.getCreateTableQuery();
    db.execSQL(SQL_CREATE_TABLE);
  }

  private void generateAdvanceTable(){
    db.execSQL(SQLCreateTemplate + advanceContract.getTableName());
    String SQL_CREATE_TABLE = advanceContract.getCreateTableQuery();
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
    generateGoalsTable();
    generateAdvanceTable();
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
    generateGoalsTable();
    generateAdvanceTable();
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
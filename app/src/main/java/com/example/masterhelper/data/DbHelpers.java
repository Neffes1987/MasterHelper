package com.example.masterhelper.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.masterhelper.data.contracts.JourneysContract;
import com.example.masterhelper.data.contracts.SceneContract;

public class DbHelpers extends SQLiteOpenHelper {
  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 6;


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
    String SQL_CREATE_JOURNEYS_TABLE = JourneysContract.CREATE_TABLE;

    // Запускаем создание таблицы
    db.execSQL(SQL_CREATE_JOURNEYS_TABLE);

    String SQL_CREATE_SCENES_TABLE = SceneContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_SCENES_TABLE);
  }

  /**
   * Вызывается при обновлении схемы базы данных
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE "+JourneysContract.TABLE_NAME);
    db.execSQL("DROP TABLE "+SceneContract.TABLE_NAME);
    // Строка для создания таблицы
    String SQL_CREATE_JOURNEYS_TABLE = JourneysContract.CREATE_TABLE;

    // Запускаем создание таблицы
    db.execSQL(SQL_CREATE_JOURNEYS_TABLE);

    String SQL_CREATE_SCENES_TABLE = SceneContract.CREATE_TABLE;
    db.execSQL(SQL_CREATE_SCENES_TABLE);
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

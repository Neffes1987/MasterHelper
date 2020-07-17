package com.example.com.masterhelper.core.contracts;

import android.provider.BaseColumns;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.com.masterhelper.core.models.DataModel;

import java.util.ArrayList;
import java.util.Arrays;

public class GeneralContract implements BaseColumns, IContract {
  private String TABLE_NAME;

  protected String[] CREATE_TABLE_COLUMNS;

  protected String[] UPDATE_COLUMNS_PROPS;

  protected String[] INSERT_COLUMNS_PROPS;

  protected String CREATE_TABLE;

  protected Callback getValueCallback;

  public GeneralContract(String tableName, String[] createColumns, String[] updateColumns, String[] insertColumns, Callback callback){
    TABLE_NAME = tableName;
    CREATE_TABLE_COLUMNS = createColumns;
    UPDATE_COLUMNS_PROPS = updateColumns;
    INSERT_COLUMNS_PROPS = insertColumns;
    CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);
    getValueCallback = callback;
  }

  public String getCreateTableQuery() {
    return CREATE_TABLE;
  }

  public String getTableName(){
    return TABLE_NAME;
  }

  public final static String _ID = BaseColumns._ID;

  public final static String INDEX_KEY = _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";

  /** получить весь список записей по таблице
   * @param tableName - имя таблицы, откуда получаются записи
   * @param columns - колонки, которые надо получить
   * */
  public static String getListQueryBase(String tableName, @Nullable String[] columns){
    String columnsList = columns != null ? Arrays.toString(columns) : "*";
    return "SELECT "+ columnsList +" FROM " + tableName;
  }

  /** получить список записий по условиям
   * @param tableName - имя таблицы, откуда тянем записи
   * @param columns - колонки которые надо получить
   * @param where - условие поиска
   * @param ordering - сортирвка списка
   * @param limit - максимальное количество записей
   * */
  public static String getListQuery(String tableName, @Nullable String[] columns, @Nullable String where,  @Nullable String ordering,  int limit ){
    String query = getListQueryBase(tableName, columns);
    if(where != null){
      query += " WHERE " + where;
    }

    if(ordering != null){
      query += " ORDER BY " + ordering;
    }

    if(limit > 0){
      query += " LIMIT " + limit;
    }

    return query + ';';
  }

  /**
   * добавить новую строку в таблицу
   * @param tableName - имя таблицы куда добавляется строка
   * @param columns - имена колонок, которые надо инициализировать
   * @param values - значения для инициализации
   * */
  public static String generateInsertQuery(String tableName, String[] columns, String[] values){
    StringBuilder valuesResult = new StringBuilder();
    StringBuilder columnsResult = new StringBuilder();
    int columnsLastIndex = columns.length -1;
    for(int i=0; i<=columnsLastIndex; i++){
      String columnName = columns[i];
      String value = values[i];

      columnsResult.append(columnName);
      valuesResult.append("'").append(value).append("'");
      if(i <  columnsLastIndex){
        columnsResult.append(",");
        valuesResult.append(",");
      }
    }

    return "INSERT INTO " + tableName + " (" + columnsResult + ") VALUES ("+ valuesResult +")";
  }

  /** обновить запись по его ид
   * @param tableName - имя таблица, где меняем строку
   * @param tableRecordId - ид строки, которую меняем
   * @param columns - колонки, значения которых надо поменять
   * @param values -  значения для этих полей
   * */
  public static String generateUpdateValues(String tableName, int tableRecordId, String[] columns, String[] values){
    return commonUpdateGenerator(tableName, columns, values) +" WHERE " + BaseColumns._ID + "='"+tableRecordId+"'";
  }

  public static String commonUpdateGenerator(String tableName, String[] columns, String[] values){
    StringBuilder result = new StringBuilder();
    int columnsLastIndex = columns.length -1;

    for(int i=0; i<=columnsLastIndex; i++){
      String columnName = columns[i];
      String value = values[i];

      result.append(columnName).append("='").append(value).append("'");
      if(i <  columnsLastIndex){
        result.append(",");
      }
    }
    return "UPDATE " + tableName + " SET "+ result.toString();
  }


  /** запрос в бд на создание таблицы */
  public static String generateTableQuery(String TableName, String[] columns){
    Log.i("TAG", "generateTableQuery: "+TableName);
    StringBuilder result = new StringBuilder();
    if(columns.length == 0){
      return "";
    }
    result.append(INDEX_KEY);
    int columnsLastIndex = columns.length -1;

    for(int i=0; i<=columnsLastIndex; i++){
      String columnName = columns[i];
      result.append(columnName);
      if(i <  columnsLastIndex){
        result.append(",");
      }
    }
    return "CREATE TABLE " + TableName + " (" +result.toString() + ")";
  }

  /** удалить запись в таблице */
  public String generateDeleteItemQuery(String tableName, int deletedItemId){
    return "DELETE FROM " + tableName + " WHERE " + _ID + "='"+deletedItemId+"'";
  }

  /** функция конкатернации строк */
  public static String [] concat(final String[] first, final String[] second) {
    final ArrayList<String> resultList = new ArrayList<>(Arrays.asList(first));
    resultList.addAll(new ArrayList<>(Arrays.asList(second)));
    return resultList.toArray(new String[0]);
  }

  @Override
  public String[] getValues(DataModel newItem, int parentID) {
    return getValueCallback.get(newItem, parentID);
  }

  public String add(DataModel newItem, int scriptId){
    String[] values = getValues(newItem, scriptId);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public String delete(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public String update(int enemyId, DataModel newItem){
    String[] values = getValues(newItem, enemyId);
    return commonUpdateGenerator(TABLE_NAME, UPDATE_COLUMNS_PROPS, values);
  }

  public String deleteRecordsByIds(String ids){
    return "DELETE FROM " + TABLE_NAME + " WHERE " + _ID + " IN (" +ids+ ")";
  }

  public interface Callback{
    String[] get(DataModel newItem, int parentID);
  }
}

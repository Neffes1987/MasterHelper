package com.masterhelper.appconfig.contracts;

import android.provider.BaseColumns;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GeneralContract<Model> implements BaseColumns, IContract<Model> {

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
  public static String generateDeleteItemQuery(String tableName, int deletedItemId){
    return "DELETE FROM " + tableName + " WHERE " + _ID + "='"+deletedItemId+"'";
  }

  /** функция конкатернации строк */
  public static String [] concat(final String [] first, final String [] second) {
    final ArrayList<String> resultList = new ArrayList<>(Arrays.asList(first));
    resultList.addAll(new ArrayList<>(Arrays.asList(second)));
    return resultList.toArray(new String [resultList.size()]);
  }
}

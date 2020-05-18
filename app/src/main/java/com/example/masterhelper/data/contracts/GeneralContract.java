package com.example.masterhelper.data.contracts;

import android.provider.BaseColumns;
import android.util.Log;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralContract implements BaseColumns {

  public final static String _ID = BaseColumns._ID;

  public final static String INDEX_KEY = _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";

  public static String getListQueryBase(String tableName, @Nullable String[] columns){
    String columnsList = columns != null ? Arrays.toString(columns) : "*";
    return "SELECT "+ columnsList +" FROM " + tableName;
  }

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

  public static String generateUpdateValues(String tableName, int itemId, String[] columns, String[] values){
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

    return "UPDATE " + tableName + " SET "+ result.toString() +" WHERE " + BaseColumns._ID + "='"+itemId+"'";
  }

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
    return "CREATE TABLE " + TableName + " (" +result.toString() + ");";
  }

  public static String generateDeleteItemQuery(String tableName, int itemId){
    return "DELETE FROM " + tableName + " WHERE " + _ID + "='"+itemId+"'";
  }


  public static final String [] concat(final String [] first, final String [] second) {
    final ArrayList<String> resultList = new ArrayList<>(Arrays.asList(first));
    resultList.addAll(new ArrayList<>(Arrays.asList(second)));
    return resultList.toArray(new String [resultList.size()]);
  }

}

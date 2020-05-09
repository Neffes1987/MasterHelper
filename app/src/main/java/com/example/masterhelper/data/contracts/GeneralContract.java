package com.example.masterhelper.data.contracts;

import android.provider.BaseColumns;
import androidx.annotation.Nullable;

import java.util.Arrays;

public class GeneralContract implements BaseColumns {

  public final static String _ID = BaseColumns._ID;

  public final static String INDEX_KEY = _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";

  public static String getListQueryBase(String tableName, @Nullable String[] columns){
    String columnsList = columns != null ? Arrays.toString(columns) : "*";
    String query = "SELECT "+ columnsList +" FROM " + tableName;
    return query;
  }

  public static String getListQuery(String tableName, @Nullable String[] columns, @Nullable String where,  @Nullable String ordering,  @Nullable int limit ){
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
}

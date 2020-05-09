package com.example.masterhelper.data.contracts;


public class Journeys extends GeneralContract {
  public final static String TABLE_NAME = "journeys";

  public final static String COLUMN_TITLE = "title";

  public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + INDEX_KEY  + COLUMN_TITLE + " TEXT NOT NULL);";

  public static String addItemQuery(String newItemName){
    return "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + ") VALUES ('"+newItemName+"')";
  }

  public static String deleteItemQuery(int itemId){
    return "DELETE FROM TABLE " + TABLE_NAME + " WHERE " + _ID + "='"+itemId+"'";
  }

  public static String updateItemQuery(int itemId, String newValue){
    return "UPDATE TABLE " + TABLE_NAME + "SET "+COLUMN_TITLE+"="+newValue+" WHERE " + _ID + "='"+itemId+"'";
  }
}

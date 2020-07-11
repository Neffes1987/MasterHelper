package com.example.com.masterhelper.core.appconfig.contracts;

public interface IContract<Model> {

  static String TABLE_NAME = null;

  static String[] CREATE_TABLE_COLUMNS = null;

  static String[] UPDATE_COLUMNS_PROPS = null;

  static String[] INSERT_COLUMNS_PROPS = null;

  static String CREATE_TABLE = null;


  String[] getValues(Model newItem, int parentID);

  String addItemQuery(Model newItem, int parentID);

  String deleteItemQuery(int itemId);

  String updateItemQuery(int parentID, Model newItem);
}

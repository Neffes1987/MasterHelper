package com.example.com.masterhelper.core.contracts;

import com.example.com.masterhelper.core.models.DataModel;

public interface IContract {

  static String TABLE_NAME = null;

  static String[] CREATE_TABLE_COLUMNS = null;

  static String[] UPDATE_COLUMNS_PROPS = null;

  static String[] INSERT_COLUMNS_PROPS = null;

  static String CREATE_TABLE = null;


  String[] getValues(DataModel newItem, int parentID);

  String add(DataModel newItem, int parentID);

  String delete(int itemId);

  String update(int parentID, DataModel newItem);
}

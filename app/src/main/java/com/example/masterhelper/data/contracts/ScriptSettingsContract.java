package com.example.masterhelper.data.contracts;

import com.example.masterhelper.models.EnemyModel;


public class ScriptSettingsContract extends GeneralContract {
  public final static String TABLE_NAME = "scriptSettings";

  public final static String COLUMN_SCRIPT_ID = "scriptId";
  public final static String COLUMN_CHAINED_ID = "chainedId";
  public final static String COLUMN_CHAINED_TYPE = "chainedType";

  private final static String COLUMN_SCRIPT_ID_PROPS = COLUMN_SCRIPT_ID + " INTEGER NOT NULL";
  private final static String COLUMN_CHAINED_ID_PROPS = COLUMN_CHAINED_ID + " INTEGER NOT NULL";
  private final static String COLUMN_CHAINED_TYPE_PROPS = COLUMN_CHAINED_TYPE + " TEXT NOT NULL";
  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_CHAINED_ID_PROPS,
    COLUMN_CHAINED_TYPE_PROPS,
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_SCRIPT_ID,
    COLUMN_CHAINED_ID,
    COLUMN_CHAINED_TYPE
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  public static String[] getValues(EnemyModel newItem, int id){
    if(id == 0){
      return new String[]{newItem.getName(), newItem.getDescription(), newItem.getTotalHealth() + ""};
    }
    return new String[]{newItem.getName(), newItem.getDescription(), newItem.getTotalHealth() + "",  id+""};
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public static String addItemQuery(EnemyModel newItem, int id){
    String[] values = getValues(newItem, id);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public static String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public static String updateItemQuery(int itemId, EnemyModel newItem){
    String[] values = getValues(newItem, 0);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }
}

package com.example.masterhelper.data.contracts;

import com.example.masterhelper.models.ScriptRecycleDataModel;


public class AchievesContract extends GeneralContract {
  public final static String TABLE_NAME = "achieves";

  public final static String COLUMN_TITLE = "title";
  public final static String COLUMN_DESCRIPTION = "value";
  public final static String COLUMN_SCRIPT_ID = "scriptId";
  public final static String COLUMN_READONLY = "readonly";
  public final static String COLUMN_TAG = "tag";

  private final static String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_SCRIPT_ID_PROPS = COLUMN_SCRIPT_ID + " INTEGER";
  private final static String COLUMN_HAS_BATTLE_EVENT_PROPS = COLUMN_READONLY + " BOOLEAN";
  private final static String COLUMN_TAG_PROPS = COLUMN_TAG + " CHAR(30)";

  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_HAS_BATTLE_EVENT_PROPS,
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_TAG_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_READONLY,
    COLUMN_TAG
  };

  public static String[] INSERT_COLUMNS_PROPS = concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_SCRIPT_ID});

  public static String[] getValues(ScriptRecycleDataModel newScript, int id){
    if(id == 0){
      return new String[]{newScript.title, newScript.description, newScript.hasBattleActionIcon + "", newScript.isFinished+""};
    }
    return new String[]{newScript.title,newScript.description, newScript.hasBattleActionIcon + "", newScript.isFinished+"",  id+""};
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public static String addItemQuery(ScriptRecycleDataModel newItem, int id){
    String[] values = getValues(newItem, id);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public static String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public static String updateItemQuery(int itemId, ScriptRecycleDataModel newItem){
    String[] values = getValues(newItem, 0);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }
}

package com.example.masterhelper.data.contracts;

import com.example.masterhelper.models.ScriptRecycleDataModel;


public class EnemyContract extends GeneralContract {
  public final static String TABLE_NAME = "enemies";

  public final static String COLUMN_TITLE = "name";
  public final static String COLUMN_DESCRIPTION = "description";

  private final static String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

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

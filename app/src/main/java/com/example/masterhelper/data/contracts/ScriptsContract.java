package com.example.masterhelper.data.contracts;

import android.util.Log;
import com.example.masterhelper.models.ScriptRecycleDataModel;


public class ScriptsContract extends GeneralContract {
  public final static String TABLE_NAME = "scripts";

  public final static String COLUMN_TITLE = "title";
  public final static String COLUMN_DESCRIPTION = "description";
  public final static String COLUMN_SCENE_ID = "sceneId";
  public final static String COLUMN_HAS_BATTLE_EVENT = "hasBattleActionIcon";
  public final static String COLUMN_IS_FINISHED = "isFinished";

  private final static String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_SCENE_ID_PROPS = COLUMN_SCENE_ID + " INTEGER";
  private final static String COLUMN_HAS_BATTLE_EVENT_PROPS = COLUMN_HAS_BATTLE_EVENT + " BOOLEAN";
  private final static String COLUMN_IS_FINISHED_PROPS = COLUMN_IS_FINISHED + " BOOLEAN";

  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_HAS_BATTLE_EVENT_PROPS,
    COLUMN_IS_FINISHED_PROPS,
    COLUMN_SCENE_ID_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_HAS_BATTLE_EVENT,
    COLUMN_IS_FINISHED
  };

  public static String[] INSERT_COLUMNS_PROPS = concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_SCENE_ID});

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

package com.masterhelper.appconfig.contracts;

import com.example.masterhelper.models.ScriptRecycleDataModel;

public class ScriptsContract extends GeneralContract<ScriptRecycleDataModel> {
  public final static  String TABLE_NAME = "scripts";

  public final static  String COLUMN_TITLE = "title";
  public final static  String COLUMN_DESCRIPTION = "description";
  public final static  String COLUMN_SCENE_ID = "sceneId";
  public final static  String COLUMN_HAS_BATTLE_EVENT = "hasBattleActionIcon";
  public final static  String COLUMN_IS_FINISHED = "isFinished";

  public final static String COLUMN_SCENE_REF_PROPS = "FOREIGN KEY ("+COLUMN_SCENE_ID+") REFERENCES " + SceneContract.TABLE_NAME + "("+SceneContract._ID+") ON DELETE CASCADE";

  private final static  String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static  String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static  String COLUMN_SCENE_ID_PROPS = COLUMN_SCENE_ID + " INTEGER";
  private final static  String COLUMN_HAS_BATTLE_EVENT_PROPS = COLUMN_HAS_BATTLE_EVENT + " BOOLEAN";
  private final static  String COLUMN_IS_FINISHED_PROPS = COLUMN_IS_FINISHED + " BOOLEAN";

  public static  String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_HAS_BATTLE_EVENT_PROPS,
    COLUMN_IS_FINISHED_PROPS,
    COLUMN_SCENE_ID_PROPS,
    COLUMN_SCENE_REF_PROPS
  };

  public static  String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_HAS_BATTLE_EVENT,
    COLUMN_IS_FINISHED
  };

  public static  String[] INSERT_COLUMNS_PROPS = concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_SCENE_ID});

  public String[] getValues(ScriptRecycleDataModel newScript, int sceneID){
    String title = newScript.title;
    String description = newScript.description;
      String hasBattle = newScript.hasBattleActionIcon + "";
        String isFinish = newScript.isFinished+"";
    String[] insertValue = new String[]{title, description, hasBattle, isFinish};
    if(sceneID == 0){
      return insertValue;
    }
    return concat(insertValue, new String[]{sceneID+""});
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public  String addItemQuery(ScriptRecycleDataModel newItem, int sceneID){
    String[] values = getValues(newItem, sceneID);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public  String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public  String updateItemQuery(int itemId, ScriptRecycleDataModel newItem){
    String[] values = getValues(newItem, 0);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }
}

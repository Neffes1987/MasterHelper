package com.example.masterhelper.data.contracts;

import com.example.masterhelper.models.SceneRecycleDataModel;

import java.util.Arrays;

public class SceneContract extends GeneralContract {
  public final static String TABLE_NAME = "scene";

  public final static String COLUMN_TITLE = "title";
  public final static String COLUMN_DESCRIPTION = "description";
  public final static String COLUMN_SCRIPT_FINISHED = "scriptsFinished";
  public final static String COLUMN_SCRIPT_TOTAL = "scriptsTotal";
  public final static String COLUMN_JOURNEY_ID = "journeyId";

  private final static String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_SCRIPT_FINISHED_PROPS = COLUMN_SCRIPT_FINISHED + " INTEGER";
  private final static String COLUMN_SCRIPT_TOTAL_PROPS = COLUMN_SCRIPT_TOTAL + " INTEGER";
  private final static String COLUMN_JOURNEY_ID_PROPS = COLUMN_JOURNEY_ID + " INTEGER";

  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_SCRIPT_FINISHED_PROPS,
    COLUMN_SCRIPT_TOTAL_PROPS,
    COLUMN_JOURNEY_ID_PROPS
  };

  public static String[] INSERT_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_SCRIPT_FINISHED,
    COLUMN_SCRIPT_TOTAL,
    COLUMN_JOURNEY_ID
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_SCRIPT_FINISHED,
    COLUMN_SCRIPT_TOTAL,
    COLUMN_JOURNEY_ID
  };

  public static String[] getValues(SceneRecycleDataModel newScene, int journeyId){
    if(journeyId == 0){
      return new String[]{newScene.title,newScene.description, newScene.scriptsFinished+"", newScene.scriptsTotal+"" };
    }
    return new String[]{newScene.title,newScene.description, newScene.scriptsFinished+"", newScene.scriptsTotal+"", journeyId+""};
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public static String addItemQuery(SceneRecycleDataModel newScene, int journeyId){
    String[] values = getValues(newScene, journeyId);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public static String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public static String updateItemQuery(int itemId, SceneRecycleDataModel newScene){
    String[] values = getValues(newScene, 0);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }
}

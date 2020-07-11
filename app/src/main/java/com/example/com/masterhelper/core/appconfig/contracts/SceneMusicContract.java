package com.example.com.masterhelper.core.appconfig.contracts;


public class SceneMusicContract extends GeneralContract<String> {
  public final static String TABLE_NAME = "sceneMusic";

  public final static String COLUMN_SCENE_ID = "sceneId";
  public final static String COLUMN_FILE_PATH = "filePath";

  public final static String COLUMN_SCENE_REF_PROPS = "FOREIGN KEY ("+COLUMN_SCENE_ID+") REFERENCES " + SceneContract.TABLE_NAME + "("+SceneContract._ID+") ON DELETE CASCADE";

  private final static String COLUMN_SCENE_ID_PROPS = COLUMN_SCENE_ID + " INTEGER NOT NULL";
  private final static String COLUMN_FILE_PATH_PROPS = COLUMN_FILE_PATH + " TEXT NOT NULL";


  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_SCENE_ID_PROPS,
    COLUMN_FILE_PATH_PROPS,

    COLUMN_SCENE_REF_PROPS,
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_SCENE_ID,
    COLUMN_FILE_PATH
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  public String[] getValues(String path, int sceneId){
    return new String[]{ sceneId + "", path};
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public String addItemQuery(String path, int sceneId){
    String[] values = getValues(path, sceneId);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public String deleteRecordsByIds(String ids){
    return "DELETE FROM " + TABLE_NAME + " WHERE " + _ID + " IN (" +ids+ ")";
  }

  public String updateItemQuery(int sceneId, String path){
    String[] values = getValues(path, sceneId);
    return commonUpdateGenerator(TABLE_NAME, UPDATE_COLUMNS_PROPS, values);
  }
}

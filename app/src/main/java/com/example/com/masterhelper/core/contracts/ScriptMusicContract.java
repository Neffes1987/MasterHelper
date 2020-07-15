package com.example.com.masterhelper.core.contracts;


public class ScriptMusicContract extends GeneralContract<String> {
  public final static String TABLE_NAME = "scriptMusic";

  public final static String COLUMN_SCRIPT_ID = "scriptId";
  public final static String COLUMN_FILE_PATH = "filePath";

  public final static String COLUMN_SCRIPT_REF_PROPS = "FOREIGN KEY ("+ COLUMN_SCRIPT_ID +") REFERENCES " + ScriptsContract.TABLE_NAME + "("+ScriptsContract._ID+") ON DELETE CASCADE";

  private final static String COLUMN_SCRIPT_ID_PROPS = COLUMN_SCRIPT_ID + " INTEGER NOT NULL";
  private final static String COLUMN_FILE_PATH_PROPS = COLUMN_FILE_PATH + " TEXT NOT NULL";


  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_FILE_PATH_PROPS,

    COLUMN_SCRIPT_REF_PROPS,
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_SCRIPT_ID,
    COLUMN_FILE_PATH
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  public String[] getValues(String path, int scriptId){
    return new String[]{ scriptId + "", path};
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public String addItemQuery(String path, int scriptId){
    String[] values = getValues(path, scriptId);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public String deleteRecordsByIds(String ids){
    return "DELETE FROM " + TABLE_NAME + " WHERE " + _ID + " IN (" +ids+ ")";
  }

  public String updateItemQuery(int scriptId, String path){
    String[] values = getValues(path, scriptId);
    return commonUpdateGenerator(TABLE_NAME, UPDATE_COLUMNS_PROPS, values);
  }
}

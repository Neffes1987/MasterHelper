package com.example.com.masterhelper.core.appconfig.contracts;

import com.example.com.masterhelper.core.appconfig.models.EnemyModel;

public class EnemyContract extends GeneralContract<EnemyModel> {
  public final static String TABLE_NAME = "enemies";

  public final static  String COLUMN_TITLE = "name";
  public final static  String COLUMN_DESCRIPTION = "description";
  public final static  String COLUMN_TOTAL_HEALTH = "totalHealth";
  public final static  String COLUMN_CURRENT_HEALTH = "currentHealth";
  public final static  String COLUMN_CURRENT_ORDERING = "ordering";
  public final static  String COLUMN_SCRIPT_ID = "scriptId";

  public final static String COLUMN_SCRIPT_REF_PROPS = "FOREIGN KEY ("+COLUMN_SCRIPT_ID+") REFERENCES " + ScriptsContract.TABLE_NAME + "("+ScriptsContract._ID+") ON DELETE CASCADE";


  private final static  String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static  String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static  String COLUMN_TOTAL_HEALTH_PROPS = COLUMN_TOTAL_HEALTH + " INTEGER NOT NULL";
  private final static  String COLUMN_CURRENT_HEALTH_PROPS = COLUMN_CURRENT_HEALTH + " INTEGER NOT NULL";
  private final static  String COLUMN_SCRIPT_ID_PROPS = COLUMN_SCRIPT_ID + " INTEGER NOT NULL";
  private final static  String COLUMN_CURRENT_ORDERING_PROPS = COLUMN_CURRENT_ORDERING + " INTEGER NOT NULL";

  public static  String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_TOTAL_HEALTH_PROPS,
    COLUMN_CURRENT_HEALTH_PROPS,
    COLUMN_CURRENT_ORDERING_PROPS,
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_SCRIPT_REF_PROPS
  };

  public static  String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_TOTAL_HEALTH,
    COLUMN_CURRENT_HEALTH,
    COLUMN_CURRENT_ORDERING
  };

  public static  String[] INSERT_COLUMNS_PROPS =  concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_SCRIPT_ID});

  public String[] getValues(EnemyModel newItem, int scriptId){
    String name = newItem.getName();
    String description = newItem.getDescription();
    String totalHealth = newItem.getTotalHealth() + "";
    String currentHealth = newItem.getCurrentHealth() + "";
    String ordering = newItem.getOrdering() + "";

    String[] insertValues = new String[]{name, description, totalHealth, currentHealth, ordering};

    if(scriptId == 0){
      return insertValues;
    }
    return concat(insertValues, new String[]{scriptId+""});
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public  String addItemQuery(EnemyModel newItem, int scriptId){
    String[] values = getValues(newItem, scriptId);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public  String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public  String updateItemQuery(int itemId, EnemyModel newItem){
    String[] values = getValues(newItem, 0);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }
}

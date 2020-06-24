package com.example.masterhelper.data.contracts;

import com.example.masterhelper.models.EnemyModel;


public class EnemyContract extends GeneralContract {
  public final static String TABLE_NAME = "enemies";

  public final static String COLUMN_TITLE = "name";
  public final static String COLUMN_DESCRIPTION = "description";
  public final static String COLUMN_TOTAL_HEALTH = "totalHealth";
  public final static String COLUMN_CURRENT_HEALTH = "totalHealth";

  private final static String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_TOTAL_HEALTH_PROPS = COLUMN_TOTAL_HEALTH + " INTEGER NOT NULL";
  private final static String COLUMN_CURRENT_HEALTH_PROPS = COLUMN_CURRENT_HEALTH + " INTEGER NOT NULL";
  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_TOTAL_HEALTH_PROPS,
    COLUMN_CURRENT_HEALTH_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_TOTAL_HEALTH,
    COLUMN_CURRENT_HEALTH
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  public static String[] getValues(EnemyModel newItem, int id){
    if(id == 0){
      return new String[]{newItem.getName(), newItem.getDescription(), newItem.getTotalHealth() + "", newItem.getCurrentHealth()+""};
    }
    return new String[]{newItem.getName(), newItem.getDescription(), newItem.getTotalHealth() + "", newItem.getCurrentHealth()+"",  id+""};
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

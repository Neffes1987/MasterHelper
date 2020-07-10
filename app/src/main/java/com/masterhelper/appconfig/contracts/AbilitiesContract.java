package com.masterhelper.appconfig.contracts;

import com.example.masterhelper.models.AbilityModel;

public class AbilitiesContract extends GeneralContract<AbilityModel> {
  public final static String TABLE_NAME = "achieves";

  public final static String COLUMN_NAME = "name";

  private final static String COLUMN_NAME_PROPS = COLUMN_NAME + " TEXT NOT NULL";

  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_NAME_PROPS,
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_NAME,
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public String[] getValues(AbilityModel newScript, int id){
    return new String[]{newScript.getName()};
  }


  public String addItemQuery(AbilityModel newItem, int id){
    String[] values = getValues(newItem, id);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public String updateItemQuery(int itemId, AbilityModel newItem){
    String[] values = getValues(newItem, 0);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }
}

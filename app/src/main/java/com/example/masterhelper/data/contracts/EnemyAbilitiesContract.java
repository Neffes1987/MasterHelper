package com.example.masterhelper.data.contracts;

import com.example.masterhelper.models.AbilityModel;


public class EnemyAbilitiesContract extends GeneralContract implements IContract<AbilityModel> {
  public final static String TABLE_NAME = "enemyAbilities";

  public final static String COLUMN_SCRIPT_ID = "enemyId";
  public final static String COLUMN_ABILITY_ID = "abilityId";
  public final static String COLUMN_ABILITY_VALUE = "abilityValue";

  private final static String COLUMN_SCRIPT_ID_PROPS = COLUMN_SCRIPT_ID + " INTEGER NOT NULL";
  private final static String COLUMN_ABILITY_ID_PROPS = COLUMN_ABILITY_ID + " INTEGER NOT NULL";
  private final static String COLUMN_ABILITY_VALUE_PROPS = COLUMN_ABILITY_VALUE + " INTEGER NOT NULL";
  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_ABILITY_ID_PROPS,
    COLUMN_ABILITY_VALUE_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_SCRIPT_ID,
    COLUMN_ABILITY_ID,
    COLUMN_ABILITY_VALUE
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  public String[] getValues(AbilityModel newItem, int scriptId){
    return new String[]{ scriptId + "", newItem.getId()+"", newItem.getValue()+""};
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public String addItemQuery(AbilityModel newItem, int scriptId){
    String[] values = getValues(newItem, scriptId);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public String updateItemQuery(int scriptId, AbilityModel newItem){
    String[] values = getValues(newItem, scriptId);
    return generateUpdateValues(TABLE_NAME, newItem.getId(), UPDATE_COLUMNS_PROPS, values);
  }
}
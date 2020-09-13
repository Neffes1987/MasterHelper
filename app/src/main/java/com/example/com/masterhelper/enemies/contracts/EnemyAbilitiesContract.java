package com.example.com.masterhelper.enemies.contracts;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.enemies.models.AbilityModel;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.settings.contracts.SettingsContract;


public class EnemyAbilitiesContract implements BaseColumns {

  public final static String TABLE_NAME = "enemyAbilities";

  public final static String COLUMN_ENEMY_ID = "enemyId";
  public final static String COLUMN_ABILITY_ID = "abilityId";
  public final static String COLUMN_ABILITY_VALUE = "abilityValue";

  public final static String COLUMN_ENEMY_REF_PROPS = "FOREIGN KEY ("+COLUMN_ENEMY_ID+") REFERENCES " + EnemyContract.TABLE_NAME + "("+EnemyContract._ID+") ON DELETE CASCADE";
  public final static String COLUMN_ABILITY_REF_PROPS = "FOREIGN KEY ("+COLUMN_ABILITY_ID+") REFERENCES " + SettingsContract.TABLE_NAME + "("+SettingsContract._ID+") ON DELETE CASCADE";

  private final static String COLUMN_SCRIPT_ID_PROPS = COLUMN_ENEMY_ID + " INTEGER NOT NULL";
  private final static String COLUMN_ABILITY_ID_PROPS = COLUMN_ABILITY_ID + " INTEGER NOT NULL";
  private final static String COLUMN_ABILITY_VALUE_PROPS = COLUMN_ABILITY_VALUE + " INTEGER NOT NULL";


  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_ABILITY_ID_PROPS,
    COLUMN_ABILITY_VALUE_PROPS,

    COLUMN_ENEMY_REF_PROPS,
    COLUMN_ABILITY_REF_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_ENEMY_ID,
    COLUMN_ABILITY_ID,
    COLUMN_ABILITY_VALUE
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel newItem, int scriptId){
    AbilityModel model = (AbilityModel) newItem;
    return new String[]{ scriptId + "", model.getId()+"", model.getValue()+""};
  }
}

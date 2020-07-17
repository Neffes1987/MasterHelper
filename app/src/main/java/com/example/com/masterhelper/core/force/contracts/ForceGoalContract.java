package com.example.com.masterhelper.core.force.contracts;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.enemies.EnemyContract;
import com.example.com.masterhelper.core.contracts.settings.AbilitiesContract;
import com.example.com.masterhelper.core.models.AbilityModel;
import com.example.com.masterhelper.core.models.DataModel;


public class ForceGoalContract implements BaseColumns {

  public final static String TABLE_NAME = "force_goal_settings";

  public final static String COLUMN_FORCE_ID = "forceId";
  public final static String COLUMN_GOAL_ID = "goalId";
  public final static String COLUMN_GOAL_POSITIVE_RESULT = "resolveResult";
  public final static String COLUMN_GOAL_NEGATIVE_RESULT = "rejectResult";

  public final static String COLUMN_ENEMY_REF_PROPS = "FOREIGN KEY ("+ COLUMN_FORCE_ID +") REFERENCES " + EnemyContract.TABLE_NAME + "("+EnemyContract._ID+") ON DELETE CASCADE";
  public final static String COLUMN_ABILITY_REF_PROPS = "FOREIGN KEY ("+ COLUMN_GOAL_ID +") REFERENCES " + AbilitiesContract.TABLE_NAME + "("+AbilitiesContract._ID+") ON DELETE CASCADE";

  private final static String COLUMN_SCRIPT_ID_PROPS = COLUMN_FORCE_ID + " INTEGER NOT NULL";
  private final static String COLUMN_ABILITY_ID_PROPS = COLUMN_GOAL_ID + " INTEGER NOT NULL";
  private final static String COLUMN_ABILITY_VALUE_PROPS = COLUMN_GOAL_POSITIVE_RESULT + " INTEGER NOT NULL";


  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_ABILITY_ID_PROPS,
    COLUMN_ABILITY_VALUE_PROPS,

    COLUMN_ENEMY_REF_PROPS,
    COLUMN_ABILITY_REF_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_FORCE_ID,
    COLUMN_GOAL_ID,
    COLUMN_GOAL_POSITIVE_RESULT
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

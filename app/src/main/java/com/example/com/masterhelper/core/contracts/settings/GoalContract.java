package com.example.com.masterhelper.core.contracts.settings;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.force.models.GoalModel;
import com.example.com.masterhelper.core.models.DataModel;

public class GoalContract implements BaseColumns {
  public final static String TABLE_NAME = "goal_setting";

  public final static String COLUMN_NAME = "name";
  public final static String COLUMN_DESCRIPTION = "description";
  public final static String COLUMN_GOAL_STATUS = "status";

  private final static String COLUMN_NAME_PROPS = COLUMN_NAME + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_GOAL_STATUS_PROPS = COLUMN_GOAL_STATUS + " TEXT NOT NULL";

  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_NAME_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_GOAL_STATUS_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_NAME,
    COLUMN_DESCRIPTION,
    COLUMN_GOAL_STATUS
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel goal, int id){
    GoalModel goalModel = (GoalModel) goal;
    return new String[]{goalModel.getName(), goalModel.getDescription(), goalModel.getResult().name()};
  }
}

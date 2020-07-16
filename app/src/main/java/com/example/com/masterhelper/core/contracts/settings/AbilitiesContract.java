package com.example.com.masterhelper.core.contracts.settings;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;

public class AbilitiesContract implements BaseColumns {
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

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel newScript, int id){
    return new String[]{newScript.getName()};
  }
}

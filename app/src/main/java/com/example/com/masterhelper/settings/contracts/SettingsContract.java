package com.example.com.masterhelper.settings.contracts;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.settings.models.SettingModel;

public class SettingsContract implements BaseColumns {
  public final static String TABLE_NAME = "settings";

  public final static String COLUMN_NAME = "name";
  public final static String COLUMN_DESCRIPTION = "description";
  public final static String COLUMN_TYPE = "record_type";
  public final static String COLUMN_POSITIVE_RESULT = "positive_result";
  public final static String COLUMN_NEGATIVE_RESULT = "negative_result";

  private final static String COLUMN_NAME_PROPS = COLUMN_NAME + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_TYPE_PROPS = COLUMN_TYPE + " TEXT NOT NULL";
  private final static String COLUMN_POSITIVE_RESULT_PROPS = COLUMN_POSITIVE_RESULT + " TEXT";
  private final static String COLUMN_NEGATIVE_RESULT_PROPS = COLUMN_NEGATIVE_RESULT + " TEXT";

  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_NAME_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_TYPE_PROPS,
    COLUMN_POSITIVE_RESULT_PROPS,
    COLUMN_NEGATIVE_RESULT_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_NAME,
    COLUMN_DESCRIPTION,
    COLUMN_TYPE,
    COLUMN_POSITIVE_RESULT,
    COLUMN_NEGATIVE_RESULT
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel modal, int id){
    SettingModel item = (SettingModel) modal;
    return new String[]{item.getName(), item.getDescription(), item.getType(), item.getPositiveResult(), item.getNegativeResult() };
  }
}

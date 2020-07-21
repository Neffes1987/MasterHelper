package com.example.com.masterhelper.force.contracts;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.force.models.ForceModel;


public class ForceContract implements BaseColumns {

  public final static String TABLE_NAME = "force_settings";

  public final static String COLUMN_FORCE_NAME = "name";
  public final static String COLUMN_FORCE_DESCRIPTION = "description";
  public final static String COLUMN_FORCE_TYPE = "type";

  private final static String COLUMN_FORCE_NAME_PROPS = COLUMN_FORCE_NAME + " TEXT NOT NULL";
  private final static String COLUMN_FORCE_DESCRIPTION_PROPS = COLUMN_FORCE_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_FORCE_TYPE_PROPS = COLUMN_FORCE_TYPE + " CHAR(40) NOT NULL";


  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_FORCE_NAME_PROPS,
    COLUMN_FORCE_DESCRIPTION_PROPS,
    COLUMN_FORCE_TYPE_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_FORCE_NAME,
    COLUMN_FORCE_DESCRIPTION,
    COLUMN_FORCE_TYPE
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel newItem, int scriptId){
    ForceModel model = (ForceModel) newItem;
    return new String[]{ model.getName() + "", model.getDescription()+"", model.getType().name()};
  }
}

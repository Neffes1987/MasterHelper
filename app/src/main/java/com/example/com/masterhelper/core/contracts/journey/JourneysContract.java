package com.example.com.masterhelper.core.contracts.journey;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;

public class JourneysContract implements BaseColumns {
  public static final  String TABLE_NAME = "journeys";

  public static final  String COLUMN_TITLE = "title";

  private static final  String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";

  private static final  String[] CREATE_TABLE_COLUMNS = { COLUMN_TITLE_PROPS  };

  private static final  String[] INSERT_COLUMNS_PROPS = { COLUMN_TITLE };

  private static final  String[] UPDATE_COLUMNS_PROPS = INSERT_COLUMNS_PROPS;

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel newItem, int parentID) {
    return new String[]{newItem.getName()};
  }

}

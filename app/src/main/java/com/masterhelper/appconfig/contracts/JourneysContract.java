package com.masterhelper.appconfig.contracts;

import com.masterhelper.appconfig.models.JourneyModel;

public class JourneysContract extends GeneralContract<JourneyModel> {
  public static final  String TABLE_NAME = "journeys";

  public static final  String COLUMN_TITLE = "title";

  private static final  String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";

  private static final  String[] CREATE_COLUMNS_PROPS = { COLUMN_TITLE_PROPS  };

  private static final  String[] INSERT_COLUMNS_PROPS = { COLUMN_TITLE };

  private static final  String[] UPDATE_COLUMNS_PROPS = INSERT_COLUMNS_PROPS;


  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_COLUMNS_PROPS);

  public String[] getValues(JourneyModel newItem, int parentID) {
    return new String[]{newItem.getTitle()};
  }

  public String addItemQuery(JourneyModel newItem, int parentID) {
    String[] values = getValues(newItem, parentID);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public  String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public  String updateItemQuery(int itemId, JourneyModel newItem){
    String[] values = getValues(newItem, itemId);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }

}

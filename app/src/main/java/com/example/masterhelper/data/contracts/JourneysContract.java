package com.example.masterhelper.data.contracts;

import com.example.masterhelper.models.JourneyModel;

public class JourneysContract extends GeneralContract {
  public final static String TABLE_NAME = "journeys";

  public final static String COLUMN_TITLE = "title";

  private final static String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";

  private final static String[] CREATE_COLUMNS_PROPS = { COLUMN_TITLE_PROPS  };

  private final static String[] INSERT_COLUMNS_PROPS = { COLUMN_TITLE };

  private final static String[] UPDATE_COLUMNS_PROPS = INSERT_COLUMNS_PROPS;

  public static String[] getValues(JourneyModel journey){
    return new String[]{journey.getTitle()};
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_COLUMNS_PROPS);

  public static String addItemQuery(JourneyModel newItem){
    String[] values = getValues(newItem);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public static String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public static String updateItemQuery(int itemId, JourneyModel newItem){
    String[] values = getValues(newItem);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }

}

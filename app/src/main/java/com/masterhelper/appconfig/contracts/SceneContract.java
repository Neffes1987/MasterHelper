package com.masterhelper.appconfig.contracts;

import com.masterhelper.appconfig.models.SceneModel;

public class SceneContract extends GeneralContract<SceneModel> {
  public final static String TABLE_NAME = "scene";

  public final static String COLUMN_TITLE = "title";
  public final static String COLUMN_DESCRIPTION = "description";
  public final static String COLUMN_JOURNEY_ID = "journeyId";

  public final static String COLUMN_JOURNEY_REF_PROPS = "FOREIGN KEY ("+COLUMN_JOURNEY_ID+") REFERENCES " + JourneysContract.TABLE_NAME + "("+JourneysContract._ID+") ON DELETE CASCADE";

  private final static String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static String COLUMN_JOURNEY_ID_PROPS = COLUMN_JOURNEY_ID + " INTEGER";

  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_JOURNEY_ID_PROPS,
    COLUMN_JOURNEY_REF_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION
  };

  public static String[] INSERT_COLUMNS_PROPS = concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_JOURNEY_ID});

  public String[] getValues(SceneModel newScene, int journeyId){
    String title = newScene.title;
    String description = newScene.description;
    String[] insertValues = new String[]{title, description};

    if(journeyId == 0){
      return insertValues;
    }
    return concat(insertValues, new String[]{journeyId+""});
  }

  public static String CREATE_TABLE = generateTableQuery(TABLE_NAME, CREATE_TABLE_COLUMNS);

  public String addItemQuery(SceneModel newScene, int journeyId){
    String[] values = getValues(newScene, journeyId);
    return generateInsertQuery(TABLE_NAME, INSERT_COLUMNS_PROPS, values);
  }

  public String deleteItemQuery(int itemId){
    return generateDeleteItemQuery(TABLE_NAME, itemId);
  }

  public String updateItemQuery(int itemId, SceneModel newScene){
    String[] values = getValues(newScene, 0);
    return generateUpdateValues(TABLE_NAME, itemId, UPDATE_COLUMNS_PROPS, values);
  }
}

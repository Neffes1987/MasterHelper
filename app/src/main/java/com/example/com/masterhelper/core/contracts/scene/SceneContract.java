package com.example.com.masterhelper.core.contracts.scene;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.journey.JourneysContract;
import com.example.com.masterhelper.core.models.DataModel;

public class SceneContract implements BaseColumns {
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

  public static String[] INSERT_COLUMNS_PROPS = GeneralContract.concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_JOURNEY_ID});

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel newScene, int journeyId){
    String title = newScene.getName();
    String description = newScene.getDescription();
    String[] insertValues = new String[]{title, description};

    if(journeyId == 0){
      return insertValues;
    }
    return GeneralContract.concat(insertValues, new String[]{journeyId+""});
  }
}

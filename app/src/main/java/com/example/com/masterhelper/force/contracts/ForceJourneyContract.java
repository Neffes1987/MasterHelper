package com.example.com.masterhelper.force.contracts;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.journey.contracts.JourneysContract;


public class ForceJourneyContract implements BaseColumns {
  private final static String relationTable = JourneysContract.TABLE_NAME;
  private final static String relationId = JourneysContract._ID;

  public final static String TABLE_NAME = "force_journey_settings";

  public final static String COLUMN_FORCE_ID = "forceId";
  public final static String COLUMN_RELATION_ID = "journey";

  public final static String COLUMN_FORCE_REF_PROPS = "FOREIGN KEY ("+ COLUMN_FORCE_ID +") REFERENCES " + ForceContract.TABLE_NAME + "("+ForceContract._ID+") ON DELETE CASCADE";
  public final static String COLUMN_RELATION_REF_PROPS = "FOREIGN KEY ("+ COLUMN_RELATION_ID +") REFERENCES " + relationTable + "("+relationId+") ON DELETE CASCADE";

  private final static String COLUMN_FORCE_ID_PROPS = COLUMN_FORCE_ID + " INTEGER NOT NULL";
  private final static String COLUMN_RELATION_ID_PROPS = COLUMN_RELATION_ID + " INTEGER NOT NULL";


  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_FORCE_ID_PROPS,
    COLUMN_RELATION_ID_PROPS,

    COLUMN_FORCE_REF_PROPS,
    COLUMN_RELATION_REF_PROPS
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_FORCE_ID,
    COLUMN_RELATION_ID,
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel newItem, int forceId){
    return new String[]{ forceId + "", newItem.getId() + ""};
  }
}

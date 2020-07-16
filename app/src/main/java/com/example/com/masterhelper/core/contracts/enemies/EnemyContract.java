package com.example.com.masterhelper.core.contracts.enemies;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.EnemyModel;

public class EnemyContract implements BaseColumns {
  public final static String TABLE_NAME = "enemies";

  public final static  String COLUMN_TITLE = "name";
  public final static  String COLUMN_DESCRIPTION = "description";
  public final static  String COLUMN_TOTAL_HEALTH = "totalHealth";
  public final static  String COLUMN_CURRENT_HEALTH = "currentHealth";
  public final static  String COLUMN_CURRENT_ORDERING = "ordering";
  public final static  String COLUMN_SCRIPT_ID = "scriptId";

  public final static String COLUMN_SCRIPT_REF_PROPS = "FOREIGN KEY ("+COLUMN_SCRIPT_ID+") REFERENCES " + ScriptsContract.TABLE_NAME + "("+ScriptsContract._ID+") ON DELETE CASCADE";


  private final static  String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static  String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static  String COLUMN_TOTAL_HEALTH_PROPS = COLUMN_TOTAL_HEALTH + " INTEGER NOT NULL";
  private final static  String COLUMN_CURRENT_HEALTH_PROPS = COLUMN_CURRENT_HEALTH + " INTEGER NOT NULL";
  private final static  String COLUMN_SCRIPT_ID_PROPS = COLUMN_SCRIPT_ID + " INTEGER NOT NULL";
  private final static  String COLUMN_CURRENT_ORDERING_PROPS = COLUMN_CURRENT_ORDERING + " INTEGER NOT NULL";

  public static  String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_TOTAL_HEALTH_PROPS,
    COLUMN_CURRENT_HEALTH_PROPS,
    COLUMN_CURRENT_ORDERING_PROPS,
    COLUMN_SCRIPT_ID_PROPS,
    COLUMN_SCRIPT_REF_PROPS
  };

  public static  String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_TOTAL_HEALTH,
    COLUMN_CURRENT_HEALTH,
    COLUMN_CURRENT_ORDERING
  };

  public static  String[] INSERT_COLUMNS_PROPS =  GeneralContract.concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_SCRIPT_ID});

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }


  public String[] getValues(DataModel model, int scriptId){
    EnemyModel newItem = (EnemyModel) model;
    String name = newItem.getName();
    String description = newItem.getDescription();
    String totalHealth = newItem.getTotalHealth() + "";
    String currentHealth = newItem.getCurrentHealth() + "";
    String ordering = newItem.getOrdering() + "";

    String[] insertValues = new String[]{name, description, totalHealth, currentHealth, ordering};

    if(scriptId == 0){
      return insertValues;
    }
    return GeneralContract.concat(insertValues, new String[]{scriptId+""});
  }

}

package com.example.com.masterhelper.scene.contracts;

import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.scene.models.ScriptModel;

public class ScriptsContract implements BaseColumns {
  public final static  String TABLE_NAME = "scripts";

  public final static  String COLUMN_TITLE = "title";
  public final static  String COLUMN_DESCRIPTION = "description";
  public final static  String COLUMN_SCENE_ID = "sceneId";
  public final static  String COLUMN_HAS_BATTLE_EVENT = "hasBattleActionIcon";
  public final static  String COLUMN_IS_FINISHED = "isFinished";

  public final static String COLUMN_SCENE_REF_PROPS = "FOREIGN KEY ("+COLUMN_SCENE_ID+") REFERENCES " + SceneContract.TABLE_NAME + "("+SceneContract._ID+") ON DELETE CASCADE";

  private final static  String COLUMN_TITLE_PROPS = COLUMN_TITLE + " TEXT NOT NULL";
  private final static  String COLUMN_DESCRIPTION_PROPS = COLUMN_DESCRIPTION + " TEXT NOT NULL";
  private final static  String COLUMN_SCENE_ID_PROPS = COLUMN_SCENE_ID + " INTEGER";
  private final static  String COLUMN_HAS_BATTLE_EVENT_PROPS = COLUMN_HAS_BATTLE_EVENT + " BOOLEAN";
  private final static  String COLUMN_IS_FINISHED_PROPS = COLUMN_IS_FINISHED + " BOOLEAN";

  public static  String[] CREATE_TABLE_COLUMNS = {
    COLUMN_TITLE_PROPS,
    COLUMN_DESCRIPTION_PROPS,
    COLUMN_HAS_BATTLE_EVENT_PROPS,
    COLUMN_IS_FINISHED_PROPS,
    COLUMN_SCENE_ID_PROPS,
    COLUMN_SCENE_REF_PROPS
  };

  public static  String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_TITLE,
    COLUMN_DESCRIPTION,
    COLUMN_HAS_BATTLE_EVENT,
    COLUMN_IS_FINISHED
  };

  public static  String[] INSERT_COLUMNS_PROPS = GeneralContract.concat(UPDATE_COLUMNS_PROPS, new String[]{COLUMN_SCENE_ID});

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel model, int sceneID){
    ScriptModel newScript = (ScriptModel) model;
    String title = newScript.getName();
    String description = newScript.getDescription();
      String hasBattle = newScript.hasBattleActionIcon + "";
        String isFinish = newScript.isFinished+"";
    String[] insertValue = new String[]{title, description, hasBattle, isFinish};
    if(sceneID == 0){
      return insertValue;
    }
    return GeneralContract.concat(insertValue, new String[]{sceneID+""});
  }
}

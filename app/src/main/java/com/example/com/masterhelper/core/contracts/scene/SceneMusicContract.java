package com.example.com.masterhelper.core.contracts.scene;


import android.provider.BaseColumns;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.media.adapters.SoundFileModel;

public class SceneMusicContract implements BaseColumns {
  public final static String TABLE_NAME = "sceneMusic";

  public final static String COLUMN_SCENE_ID = "sceneId";
  public final static String COLUMN_FILE_PATH = "filePath";

  public final static String COLUMN_SCENE_REF_PROPS = "FOREIGN KEY ("+COLUMN_SCENE_ID+") REFERENCES " + SceneContract.TABLE_NAME + "("+SceneContract._ID+") ON DELETE CASCADE";

  private final static String COLUMN_SCENE_ID_PROPS = COLUMN_SCENE_ID + " INTEGER NOT NULL";
  private final static String COLUMN_FILE_PATH_PROPS = COLUMN_FILE_PATH + " TEXT NOT NULL";


  public static String[] CREATE_TABLE_COLUMNS = {
    COLUMN_SCENE_ID_PROPS,
    COLUMN_FILE_PATH_PROPS,

    COLUMN_SCENE_REF_PROPS,
  };

  public static String[] UPDATE_COLUMNS_PROPS = {
    COLUMN_SCENE_ID,
    COLUMN_FILE_PATH
  };

  public static String[] INSERT_COLUMNS_PROPS = UPDATE_COLUMNS_PROPS;

  GeneralContract contract = new GeneralContract(TABLE_NAME, CREATE_TABLE_COLUMNS, UPDATE_COLUMNS_PROPS, INSERT_COLUMNS_PROPS, this::getValues);

  public GeneralContract getContract() {
    return contract;
  }

  public String[] getValues(DataModel model, int sceneId){
    SoundFileModel item = (SoundFileModel) model;
    return new String[]{ sceneId + "", item.getPath()};
  }
}

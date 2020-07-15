package com.example.com.masterhelper.settings;

import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.factorys.list.CustomListItemsEnum;
import com.example.com.masterhelper.settings.adapters.AbilityDBAdapter;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;

public final class SettingsFactory {
  public static final String ABILITY_ITEM = "ability";
  public static final String FORCE_ITEM = "force";
  public AbstractSetting dbSettingAdapter;
  public CustomListItemsEnum listType;
  public DataModel model;

  public SettingsFactory(String type) {
    switch (type){
      case ABILITY_ITEM:
        dbSettingAdapter = new AbilityDBAdapter();
        listType = CustomListItemsEnum.abilities;

        break;
      case FORCE_ITEM:
        dbSettingAdapter = new AbilityDBAdapter();
        listType = CustomListItemsEnum.force;
        break;
    }
  }

  public AbstractSetting getAdapter(){
    return dbSettingAdapter;
  }

  public CustomListItemsEnum getConvertListItemType(){
    return listType;
  }

}

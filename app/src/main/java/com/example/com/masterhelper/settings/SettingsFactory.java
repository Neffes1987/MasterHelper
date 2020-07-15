package com.example.com.masterhelper.settings;

import com.example.com.masterhelper.core.factories.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factories.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.factories.list.CustomListItemsEnum;
import com.example.com.masterhelper.settings.adapters.AbilityDBAdapter;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;
import com.example.masterhelper.R;

import static com.example.com.masterhelper.core.factories.dialogs.DialogTypes.oneFieldDialog;

public final class SettingsFactory {
  public static final String ABILITY_ITEM = "ability";
  public static final String FORCE_ITEM = "force";
  public static final String GOAL_ITEM = "goal";
  public static final String ADVANCE_ITEM = "advance";
  public AbstractSetting dbSettingAdapter;
  public CustomListItemsEnum listType;
  public CommonDialog dialog;

  public SettingsFactory(String type) {
    switch (type){
      case ABILITY_ITEM:
        dbSettingAdapter = new AbilityDBAdapter();
        listType = CustomListItemsEnum.abilities;
        dialog = DialogsFactory.createDialog(oneFieldDialog);
        if(dialog != null){
          dialog.setTitle(R.string.add_abilities_setting);
        }
        break;
      case FORCE_ITEM:
        dbSettingAdapter = new AbilityDBAdapter();
        listType = CustomListItemsEnum.force;
        break;
      case GOAL_ITEM:
        dbSettingAdapter = new AbilityDBAdapter();
        listType = CustomListItemsEnum.setting;
        break;
      case ADVANCE_ITEM:
        dbSettingAdapter = new AbilityDBAdapter();
        listType = CustomListItemsEnum.setting;
        break;
    }
  }

  public AbstractSetting getAdapter(){
    return dbSettingAdapter;
  }

  public CustomListItemsEnum getConvertListItemType(){
    return listType;
  }

  public CommonDialog getDialog() {
    return dialog;
  }
}

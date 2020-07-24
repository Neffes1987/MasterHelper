package com.example.com.masterhelper.settings;

import com.example.com.masterhelper.core.factories.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factories.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.journey.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.listFactory.CustomListItemsEnum;
import com.example.com.masterhelper.force.adapters.ForceDBAdapter;
import com.example.com.masterhelper.settings.adapters.AbilityDBAdapter;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;
import com.example.com.masterhelper.settings.adapters.AdvanceDBAdapter;
import com.example.com.masterhelper.journey.adapters.GoalDBAdapter;
import com.example.masterhelper.R;

import static com.example.com.masterhelper.core.factories.dialogs.DialogTypes.*;

public final class SettingsFactory {
  public AbstractSetting dbSettingAdapter;
  public CustomListItemsEnum listType;
  public CommonDialog dialog;

  public SettingsFactory(String type, boolean isSelectable) {
    switch (SettingsFactoryType.valueOf(type)){
      case ability:
        dbSettingAdapter = new AbilityDBAdapter();
        listType = CustomListItemsEnum.abilities;
        dialog = DialogsFactory.createDialog(oneFieldDialog);
        if(dialog != null){
          dialog.setTitle(R.string.add_abilities_setting);
        }
        break;
      case force:
        dbSettingAdapter = new ForceDBAdapter();
        listType = CustomListItemsEnum.force;
        dialog = DialogsFactory.createDialog(force);
        if(dialog != null){
          dialog.setTitle(R.string.force_settings_create);
        }
        break;
      case goal:
        dbSettingAdapter = new GoalDBAdapter();
        listType = isSelectable ? CustomListItemsEnum.setting_selectable : CustomListItemsEnum.setting;
        dialog = DialogsFactory.createDialog(goal);

        if(dialog != null){
          dialog.setTitle(R.string.goal_settings_create);
        }
        break;
      case advance:
        dbSettingAdapter = new AdvanceDBAdapter();
        listType = isSelectable ? CustomListItemsEnum.setting_selectable : CustomListItemsEnum.setting;
        dialog = DialogsFactory.createDialog(advance);
        if(dialog != null){
          dialog.setTitle(R.string.advance_settings_create);
        }
        break;
      case journey:
        dbSettingAdapter = new JourneyDBAdapter();
        listType = isSelectable ? CustomListItemsEnum.setting_selectable : CustomListItemsEnum.setting;
        dialog = DialogsFactory.createDialog(oneFieldDialog);
        if(dialog != null){
          dialog.setTitle(R.string.force_journeys_add);
        }
        break;
    }
  }

  public enum SettingsFactoryType {
    ability,
    force,
    goal,
    advance,
    journey
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

package com.example.com.masterhelper.settings;

import com.example.com.masterhelper.core.components.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.components.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.CommonItem;
import com.example.com.masterhelper.settings.ui.SettingsItem;

import static com.example.com.masterhelper.core.components.dialogs.DialogTypes.*;

public final class SettingsFactory {
  public CommonDialog dialog;
  public CommonItem commonItem;

  public SettingsFactory(SettingsType type, int dialogTitle) {
    commonItem = new SettingsItem(type);
    switch (type){
      case showDescription:
      case relationSetting:
      case defaultSettings:
        dialog = DialogsFactory.createDialog(oneFieldDialog);
        break;
      case selectable:
      case global:
        dialog = DialogsFactory.createDialog(goal);
        break;
    }
    if(dialog != null){
      dialog.setTitle(dialogTitle);
    }
  }
  public CommonDialog getDialog() {
    return dialog;
  }
}

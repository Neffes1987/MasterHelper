package com.example.com.masterhelper.core.factories.dialogs.dialogs;

import android.app.Activity;
import android.content.Intent;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.ScriptModel;
import com.example.com.masterhelper.core.factories.dialogs.ui.CreateNewItemDialog;

public class CreateScriptDialog extends CommonDialog {
  public CreateScriptDialog() {}

  @Override
  public void show(Activity context) {}

  public void show(Activity context, DataModel settings) {
    Intent intent = new Intent(context, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.CONFIGURATION, CreateNewItemDialog.Configurations.script.name());
    intent.putExtra(CreateNewItemDialog.TITLE, getTitle());
    if(settings != null){
      ScriptModel scriptModel = (ScriptModel) settings;
      intent.putExtra(CreateNewItemDialog.ID, settings.getId());
      intent.putExtra(CreateNewItemDialog.OLD_NAME, settings.getName());
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, settings.getDescription());
      intent.putExtra(CreateNewItemDialog.IS_BATTLE, scriptModel.hasBattleActionIcon ? 1 : 0);
      context.startActivityForResult(intent, DIALOG_UPDATE_ACTIVITY_RESULT);
      return;
    }
    context.startActivityForResult(intent, DIALOG_CREATE_ACTIVITY_RESULT);
  }
}



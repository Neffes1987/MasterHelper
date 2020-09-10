package com.example.com.masterhelper.core.components.dialogs;

import com.example.com.masterhelper.core.components.dialogs.dialogs.*;

public class DialogsFactory implements IDialogsFactory {
  public static CommonDialog createDialog(DialogTypes type) {
    switch (type){
      case scriptDialog:
        return new CreateScriptDialog();
      case advance:
        return new CreateAdvanceDialog();
      case goal:
        return new CreateGoalDialog();
      case force:
        return new CreateForceDialog();
    }
    return null;
  }
}

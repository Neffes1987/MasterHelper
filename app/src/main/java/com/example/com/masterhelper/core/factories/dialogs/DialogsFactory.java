package com.example.com.masterhelper.core.factories.dialogs;

import com.example.com.masterhelper.core.factories.dialogs.dialogs.*;

public class DialogsFactory implements IDialogsFactory {

  public static CommonDialog createDialog(DialogTypes type) {
    switch (type){
      case delete:
        return new DeleteDialog();
      case multi:
        return new MultiChooseDialog();
      case oneFieldDialog:
        return new CreateOneTextDialog();
      case withDescription:
        return new CreateDialogWithDescription();
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

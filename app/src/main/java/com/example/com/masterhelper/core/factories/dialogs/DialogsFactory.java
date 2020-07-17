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
      case setting:
        return new CreateAdvanceDialog();
    }
    return null;
  }
}

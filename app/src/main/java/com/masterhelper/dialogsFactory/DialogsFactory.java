package com.masterhelper.dialogsFactory;

import com.masterhelper.dialogsFactory.dialogs.CommonDialog;
import com.masterhelper.dialogsFactory.dialogs.DeleteDialog;
import com.masterhelper.dialogsFactory.dialogs.MultiChooseDialog;

public class DialogsFactory implements IDialogsFactory {

  public static CommonDialog createDialog(DialogTypes type) {
    switch (type){
      case delete:
        return new DeleteDialog();
      case multi:
        return new MultiChooseDialog();
    }
    return null;
  }
}

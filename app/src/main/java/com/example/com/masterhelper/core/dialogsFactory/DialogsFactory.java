package com.example.com.masterhelper.core.dialogsFactory;

import com.example.com.masterhelper.core.dialogsFactory.dialogs.CommonDialog;
import com.example.com.masterhelper.core.dialogsFactory.dialogs.DeleteDialog;
import com.example.com.masterhelper.core.dialogsFactory.dialogs.MultiChooseDialog;

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

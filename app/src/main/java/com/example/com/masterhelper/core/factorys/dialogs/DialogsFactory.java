package com.example.com.masterhelper.core.factorys.dialogs;

import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CreateOneTextDialog;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.DeleteDialog;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.MultiChooseDialog;

public class DialogsFactory implements IDialogsFactory {

  public static CommonDialog createDialog(DialogTypes type) {
    switch (type){
      case delete:
        return new DeleteDialog();
      case multi:
        return new MultiChooseDialog();
      case oneFieldDialog:
        return new CreateOneTextDialog();
    }
    return null;
  }
}

package com.masterhelper.dialogsFactory;

import androidx.annotation.Nullable;
import com.masterhelper.dialogsFactory.dialogs.CommonDialog;

public interface IDialogsFactory {
  static CommonDialog createDialog(DialogTypes type, @Nullable int title) {
    return null;
  }
}

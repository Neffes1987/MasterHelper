package com.example.com.masterhelper.core.dialogsFactory;

import androidx.annotation.Nullable;
import com.example.com.masterhelper.core.dialogsFactory.dialogs.CommonDialog;

public interface IDialogsFactory {
  static CommonDialog createDialog(DialogTypes type, @Nullable int title) {
    return null;
  }
}

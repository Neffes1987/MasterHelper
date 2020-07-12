package com.example.com.masterhelper.core.factorys.dialogs;

import androidx.annotation.Nullable;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CommonDialog;

public interface IDialogsFactory {
  static CommonDialog createDialog(DialogTypes type, @Nullable int title) {
    return null;
  }
}

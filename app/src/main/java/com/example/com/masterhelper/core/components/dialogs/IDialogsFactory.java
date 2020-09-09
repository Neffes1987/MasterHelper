package com.example.com.masterhelper.core.components.dialogs;

import androidx.annotation.Nullable;
import com.example.com.masterhelper.core.components.dialogs.dialogs.CommonDialog;

public interface IDialogsFactory {
  static CommonDialog createDialog(DialogTypes type, @Nullable int title) {
    return null;
  }
}

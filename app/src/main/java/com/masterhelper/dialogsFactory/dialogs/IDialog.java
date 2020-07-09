package com.masterhelper.dialogsFactory.dialogs;

import android.content.Context;
import android.content.DialogInterface;

public interface IDialog {
  void show(Context context);
  void setTitle(int title);
  void setOnResolveListener(DialogInterface.OnClickListener newClickListener);
  void setOnRejectListener(DialogInterface.OnClickListener newClickListener);
}

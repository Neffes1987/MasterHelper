package com.example.com.masterhelper.core.factorys.dialogs.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import com.example.com.masterhelper.core.appconfig.models.DataModel;

public interface IDialog {
  void show(Activity context);
  void show(Activity context, DataModel settings);
  void setTitle(int title);
  void setOnResolveListener(DialogInterface.OnClickListener newClickListener);
  void setOnRejectListener(DialogInterface.OnClickListener newClickListener);
}

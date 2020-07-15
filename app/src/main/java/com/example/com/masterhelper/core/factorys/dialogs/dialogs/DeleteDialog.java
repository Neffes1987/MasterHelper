package com.example.com.masterhelper.core.factorys.dialogs.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.masterhelper.R;


public class DeleteDialog extends CommonDialog {
  public DeleteDialog() {}

  @Override
  public void show(Activity context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(R.string.delete_dialog_caption)
      .setPositiveButton(R.string.accept, resolveListener)
      .setNegativeButton(R.string.cancel, rejectListener);

    builder.create().show();
  }

  public void show(Activity context, DataModel Setting) {

  }
}



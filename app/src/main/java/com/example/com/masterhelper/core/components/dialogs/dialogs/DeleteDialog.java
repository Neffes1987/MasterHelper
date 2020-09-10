package com.example.com.masterhelper.core.components.dialogs.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import com.example.masterhelper.R;


public class DeleteDialog extends CommonDialog {
  Activity context;

  public DeleteDialog(Activity context) {
    this.context = context;
  }

  public void show() {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(R.string.delete_dialog_caption)
      .setPositiveButton(R.string.accept, resolveListener)
      .setNegativeButton(R.string.cancel, rejectListener);

    builder.create().show();
  }
}



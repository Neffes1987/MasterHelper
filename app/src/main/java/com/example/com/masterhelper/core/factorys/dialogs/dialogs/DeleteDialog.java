package com.example.com.masterhelper.core.factorys.dialogs.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import com.example.masterhelper.R;


public class DeleteDialog extends CommonDialog {
  public DeleteDialog() {}

  public void show(Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(R.string.delete_dialog_caption)
      .setPositiveButton(R.string.accept, resolveListener)
      .setNegativeButton(R.string.cancel, rejectListener);

    builder.create().show();
  }
}



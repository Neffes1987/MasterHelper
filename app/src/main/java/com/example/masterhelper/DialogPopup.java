package com.example.masterhelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class DialogPopup {
  FragmentManager fragmentManager;
  DeleteDialog deleteDialog;

  public DialogPopup(FragmentManager fragmentManager){
    this.fragmentManager = fragmentManager;
    deleteDialog = new DeleteDialog();
  }

  public void setClickListener(DialogInterface.OnClickListener newClickListener){
    deleteDialog.clickListener = newClickListener;
  }


  public void show() {
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    deleteDialog.show(transaction, "delete_dialog");
  }

  public static class DeleteDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setTitle(R.string.delete_dialog_caption)
        .setPositiveButton(R.string.accept, clickListener)
        .setNegativeButton(R.string.cancel, clickListener);
      return builder.create();
    }

    public DialogInterface.OnClickListener clickListener = (dialog, id) -> {};

  }
}



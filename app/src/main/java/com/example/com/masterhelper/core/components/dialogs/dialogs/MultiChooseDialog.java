package com.example.com.masterhelper.core.components.dialogs.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MultiChooseDialog extends CommonDialog {
  Activity context;

  private String[] listOfItems;

  private boolean[] selectedItems;

  public MultiChooseDialog(Activity context) {
    this.context = context;
  }

  public boolean[] getSelectedItems() {
    return selectedItems;
  }

  public String[] getListOfItems() {
    return listOfItems;
  }

  public void setListOfItems(String[] listOfItems) {
    this.listOfItems = listOfItems;
    this.selectedItems = new boolean[listOfItems.length];
  }

  private  DialogInterface.OnMultiChoiceClickListener multiChoice = (dialog, which, isChecked) -> getSelectedItems()[which] = isChecked;


  public void show() {
    int title = getTitle();
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(title)
      .setMultiChoiceItems(getListOfItems(), getSelectedItems(), multiChoice)
      .setPositiveButton("Готово", resolveListener)

      .setNegativeButton("Отмена", rejectListener);

    builder.create().show();
  }

}
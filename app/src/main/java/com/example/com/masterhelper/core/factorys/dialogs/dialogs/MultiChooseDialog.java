package com.example.com.masterhelper.core.factorys.dialogs.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MultiChooseDialog extends CommonDialog implements IDialog {

  private String[] listOfItems;

  private boolean[] selectedItems;

  public MultiChooseDialog() {}

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

  @Override
  public void show(Context context) {
    int title = getTitle();
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(title)
      .setMultiChoiceItems(getListOfItems(), getSelectedItems(), multiChoice)
      .setPositiveButton("Готово", resolveListener)

      .setNegativeButton("Отмена", rejectListener);

    builder.create().show();
  }
}
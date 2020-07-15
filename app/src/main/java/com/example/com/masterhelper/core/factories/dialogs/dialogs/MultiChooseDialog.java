package com.example.com.masterhelper.core.factories.dialogs.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.example.com.masterhelper.core.models.DataModel;

public class MultiChooseDialog extends CommonDialog {

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
  public void show(Activity context) {
    int title = getTitle();
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(title)
      .setMultiChoiceItems(getListOfItems(), getSelectedItems(), multiChoice)
      .setPositiveButton("Готово", resolveListener)

      .setNegativeButton("Отмена", rejectListener);

    builder.create().show();
  }

  @Override
  public void show(Activity context, DataModel settings) {}
}
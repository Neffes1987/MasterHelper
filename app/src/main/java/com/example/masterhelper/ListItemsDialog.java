package com.example.masterhelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ListItemsDialog extends AppCompatDialogFragment {
  private String caption = "";

  private String[] listOfItems;

  boolean[] selectedItems;

  IButtonsEvents mListener;

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getCaption() {
    return caption;
  }

  public ListItemsDialog(String[] listOfItems, String title){
    setCaption(title);
    this.listOfItems = listOfItems;
    this.selectedItems = new boolean[listOfItems.length];
  }

  public boolean[] getSelectedItems() {
    return selectedItems;
  }

  public String[] getListOfItems() {
    return listOfItems;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof IButtonsEvents) {
      mListener = (IButtonsEvents) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement IButtonsEvents");
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(getCaption())
      .setMultiChoiceItems(getListOfItems(), getSelectedItems(), multiChoice)
      .setPositiveButton("Готово", accept)

      .setNegativeButton("Отмена", cancel);

    return builder.create();
  }

  private  DialogInterface.OnMultiChoiceClickListener multiChoice = new DialogInterface.OnMultiChoiceClickListener() {
    @Override
    public void onClick(DialogInterface dialog,
                        int which, boolean isChecked) {
      getSelectedItems()[which] = isChecked;
    }
  };

  private DialogInterface.OnClickListener accept = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int id) {
      mListener.accepted(selectedItems);
    }
  };


  private DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog,int id) {
      dialog.cancel();
      mListener.cancel();
    }
  };

  public interface IButtonsEvents{
    void cancel();
    void accepted(boolean[] selectedItems);
  }
}

package com.example.com.masterhelper.core.components.dialogs.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.com.masterhelper.core.components.ComponentEditField;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.masterhelper.R;

public class InputDialog extends CommonDialog {
  private FragmentManager manager;
  private Activity context;
  private boolean hideDescription;
  private View description;
  private View name;

  public InputDialog(Activity context, FragmentManager manager) {
    this.manager = manager;
    this.context = context;
  }

  private void setNameField(View dialogTemplate, String value){
    name = dialogTemplate.findViewById(R.id.DIALOG_NAME_ID);
    ComponentEditField.setCaption(name, R.string.name);
    ComponentEditField.setValue(name, value, false);
  }

  private void setDescriptionField(View dialogTemplate, String value){
    description = dialogTemplate.findViewById(R.id.DIALOG_DESCRIPTION_ID);
    if (hideDescription){
      description.setVisibility(View.GONE);
    }
    ComponentEditField.setCaption(description, R.string.description);
    ComponentEditField.setValue(description, value, false);
  }

  public String getName(){
    return ComponentEditField.getValue(name);
  }

  public String getDescription(){
    return ComponentEditField.getValue(description);
  }

  public void hideDescription(){
    hideDescription = true;
  }

  public void show() {
    this.show(new DataModel());
  }

  public void show(DataModel settings) {
    int title = getTitle();
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = context.getLayoutInflater();
    View dialogTemplate = inflater.inflate(R.layout.activity_dialog_template, null);

    setNameField(dialogTemplate, settings.getName());
    setDescriptionField(dialogTemplate, settings.getDescription());


    builder.setView(dialogTemplate);
    builder.setOnDismissListener(this::dismiss);

    builder.setTitle(title)
      .setPositiveButton("Готово", resolveListener)

      .setNegativeButton("Отмена", rejectListener);
    builder.create().show();
  }

  private void dismiss(DialogInterface dialogInterface) {
    FragmentTransaction f = manager.beginTransaction();
    f.remove(manager.findFragmentById(R.id.DIALOG_NAME_ID));
    f.remove(manager.findFragmentById(R.id.DIALOG_DESCRIPTION_ID));
    f.commit();
  }
}
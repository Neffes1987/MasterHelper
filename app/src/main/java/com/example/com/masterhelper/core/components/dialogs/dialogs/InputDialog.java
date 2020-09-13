package com.example.com.masterhelper.core.components.dialogs.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.com.masterhelper.core.components.ComponentCheckbox;
import com.example.com.masterhelper.core.components.ComponentEditField;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.masterhelper.R;

public class InputDialog extends CommonDialog {
  private FragmentManager manager;
  private Activity context;
  private boolean hideDescription;
  private int checkboxLabel;

  private ComponentEditField name;
  private ComponentEditField description;
  private ComponentCheckbox checkbox;

  public InputDialog(Activity context, FragmentManager manager) {
    this.manager = manager;
    this.context = context;
  }

  public void setCheckboxValue(boolean value) {
    checkbox.setCheckbox(value);
  }

  public boolean getCheckboxValue() {
    return checkbox.getCheckboxState();
  }

  public void setCheckboxLabel(int label) {
    this.checkboxLabel = label;
  }

  public void setCheckbox(View dialogTemplate, int label) {
    View parent = dialogTemplate.findViewById(R.id.DIALOG_CHECKBOX_ID);
    parent.setVisibility(label > 0 ? View.VISIBLE : View.GONE);
    checkbox = new ComponentCheckbox(parent);
    checkbox.setLabel(label);
  }

  private void setNameField(View dialogTemplate, String value){
    View parent = dialogTemplate.findViewById(R.id.DIALOG_NAME_ID);
    name = new ComponentEditField(parent);
    name.setCaption(R.string.name);
    name.setValue(value, false);
  }

  private void setDescriptionField(View dialogTemplate, String value){
    View parent = dialogTemplate.findViewById(R.id.DIALOG_DESCRIPTION_ID);
    description = new ComponentEditField(parent);
    if (hideDescription){
      parent.setVisibility(View.GONE);
    }
    description.setCaption(R.string.description);
    description.setValue(value, false);
  }

  public String getName(){
    return name.getValue();
  }

  public String getDescription(){
    return description.getValue();
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
    setCheckbox(dialogTemplate, checkboxLabel);


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
    f.remove(manager.findFragmentById(R.id.DIALOG_CHECKBOX_ID));
    f.commit();
  }
}
package com.example.com.masterhelper.core.factories.dialogs.ui;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.masterhelper.R;

import java.util.ArrayList;

public class CreateNewItemDialog extends AppCompatActivity {

  int itemId;

  int createBtnId = R.id.ITEM_CREATE_BTN_ID;
  Button createBtn;

  int cancelBtnId = R.id.ITEM_CANCEL_BTN_ID;
  Button cancelBtn;

  EditText nameEditField;

  EditText descriptionEditField;

  CheckBox hasBattleSwitch;

  TextView titleField;

  LinearLayout listLayout;
  RadioGroup radioGroup;


  public final static String CONFIGURATION = "configuration";
  public final static String IS_BATTLE = "hasBattleSceneValue";
  public final static String DESCRIPTION = "description";
  public final static String OLD_NAME = "oldName";
  public final static String NAME = "name";
  public final static String ID = "id";
  public final static String TITLE = "title";
  public final static String LIST_TITLES = "listTitle";
  public final static String SELECTED_ITEMS = "selectedItems";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog_create_new_item);
    itemId = getIntent().getIntExtra(ID, -1);
    setName();
    setTitle();
    setCreateButton();
    setCancelButton();
    setConfigurationType();
  }

  void setConfigurationType(){
    String configuration = getIntent().getStringExtra(CONFIGURATION);
    switch(Configurations.valueOf(configuration)){
      case script:
        showDescription(true);
        setScriptBlock(true);
        break;
      case withDescription:
        showDescription(true);
        setScriptBlock(false);
        break;
      case singleField:
        showDescription(false);
        setScriptBlock(false);
        break;
      case setting:
        showDescription(true);
        setScriptBlock(false);
        setSelectionList(false);
        break;
    }
  }

  void sendName(Intent intent){
    intent.putExtra(NAME, nameEditField.getText().toString());
    intent.putExtra(ID, itemId);
  }

  void sendDescription(Intent intent){
    intent.putExtra(DESCRIPTION, descriptionEditField.getText().toString());
  }

  void sendScriptBlock(Intent intent){
    intent.putExtra(IS_BATTLE, hasBattleSwitch.isChecked() ? 1 : 0);
  }

  Intent sendData(){
    String configuration = getIntent().getStringExtra(CONFIGURATION);
    Intent result = new Intent();
    sendName(result);
    switch(Configurations.valueOf(configuration)){
      case script:
        sendDescription(result);
        sendScriptBlock(result);
        break;
      case withDescription:
        sendDescription(result);
        break;
      case singleField:
        break;
      case setting:
        sendSelectionsFromList(result);
        sendDescription(result);
        break;
    }
    return result;
  }


  void setCreateButton(){
    createBtn = findViewById(createBtnId);
    createBtn.setOnClickListener(createDialogListener);
    if(itemId > 0){
      createBtn.setText(R.string.update);
    }
  }

  void setCancelButton(){
    cancelBtn = findViewById(cancelBtnId);
    cancelBtn.setOnClickListener(createDialogListener);

  }

  void setScriptBlock(boolean show){
    hasBattleSwitch = findViewById(R.id.SCRIPT_HAS_BATTLE_SCENE_ID);
    if(show){
      int hasBattleSceneValue = getIntent().getIntExtra(IS_BATTLE, 0);
      hasBattleSwitch.setVisibility(View.VISIBLE);
      hasBattleSwitch.setChecked(hasBattleSceneValue == 1);
    } else {
      hasBattleSwitch.setVisibility(View.GONE);
    }
  }

  void showDescription(boolean show){
    descriptionEditField = findViewById(R.id.ITEM_DESCRIPTION_ID);
    if(show){
      String description = getIntent().getStringExtra(DESCRIPTION);
      descriptionEditField.setVisibility(View.VISIBLE);
      descriptionEditField.setText(description);
    } else {
      descriptionEditField.setVisibility(View.GONE);
    }
  }

  void setName(){
    String oldName = getIntent().getStringExtra(OLD_NAME);
    nameEditField = findViewById(R.id.ITEM_NAME_ID);
    nameEditField.setText(oldName);
  }

  void setTitle(){
    int title = getIntent().getIntExtra(TITLE, 0);
    if(title != 0){
      titleField = findViewById(R.id.CREATE_ITEM_TITLE_ID);
      titleField.setText(title);
    }
  }

  boolean checkSelectable(int[] list, int value){
    boolean isSelected = false;
    for (int listItem : list) {
      if(listItem == value){
        isSelected = true;
        break;
      }
    }
    return isSelected;
  }

  void setSelectionList(Boolean isMulti){
    listLayout = findViewById(R.id.ITEM_LIST_ID);
    radioGroup = findViewById(R.id.ITEM_SINGLE_CHOOSE_ID);
    int[] titles = getIntent().getIntArrayExtra(LIST_TITLES);
    int[] titlesSelected = getIntent().getIntArrayExtra(SELECTED_ITEMS);

    if(titlesSelected == null){
      titlesSelected = new int[]{};
    }

    if(titles == null){
      listLayout.setVisibility(View.GONE);
      radioGroup.setVisibility(View.GONE);
      return;
    }

    for (int item : titles) {
      boolean isSelected = checkSelectable(titlesSelected, item);
      if(isMulti){
        CheckBox checkBox = new CheckBox(this);
        checkBox.setChecked(isSelected);
        checkBox.setText(item);
        listLayout.addView(checkBox);
      } else {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(item);
        radioGroup.addView(radioButton);
        if(isSelected){
          radioGroup.check(radioButton.getId());
        }
      }

    }
  }

  void sendSelectionsFromList(Intent result){
    int childrenCount = listLayout.getChildCount();
    ArrayList<String> selections = new ArrayList<>();

    if(childrenCount > 0){
      for (int i = 0; i < childrenCount; i++) {
        View item = listLayout.getChildAt(i);
        if(item instanceof CheckBox){
          CheckBox radioButton = (CheckBox) item;
          if(radioButton.isChecked()){
            selections.add(radioButton.getText().toString());
          }
        }
      }
    } else {
      childrenCount = radioGroup.getChildCount();
      for (int i = 0; i < childrenCount; i++) {
        View item = radioGroup.getChildAt(i);
        if(item instanceof RadioButton){
          RadioButton radioButton = (RadioButton) item;
          if(radioButton.isChecked()){
            selections.add(radioButton.getText().toString());
          }
        }
      }
    }
    result.putStringArrayListExtra(SELECTED_ITEMS, selections);
  }


  View.OnClickListener createDialogListener = v -> {
    if(v.getId() == createBtnId){
      Intent result = sendData();
      setResult(RESULT_OK, result);
    } else {
      setResult(RESULT_CANCELED);
    }
    finish();
  };

  public enum Configurations{
    script,
    withDescription,
    singleField, setting,
  }
}



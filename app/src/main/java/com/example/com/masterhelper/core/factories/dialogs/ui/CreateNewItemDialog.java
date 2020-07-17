package com.example.com.masterhelper.core.factories.dialogs.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.masterhelper.R;

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


  public final static String CONFIGURATION = "configuration";
  public final static String IS_BATTLE = "hasBattleSceneValue";
  public final static String DESCRIPTION = "description";
  public final static String OLD_NAME = "oldName";
  public final static String NAME = "name";
  public final static String ID = "id";
  public final static String TITLE = "title";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new_item);
    itemId = getIntent().getIntExtra(ID, -1);
    setName();
    setTitle();
    setCreateButton();
    setCancelButton();
    setConfigurationType();
  }

  void setConfigurationType(){
    String configuration = getIntent().getStringExtra(CONFIGURATION);
    Log.i("TAG", "setConfigurationType: " + configuration);
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
    singleField,
  }
}



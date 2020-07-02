package com.example.masterhelper;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CreateNewItemDialog extends AppCompatActivity {

  int itemId;

  int createBtnId = R.id.ITEM_CREATE_BTN_ID;
  Button createBtn;

  int cancelBtnId = R.id.ITEM_CANCEL_BTN_ID;
  Button cancelBtn;

  int ameEditFieldId = R.id.ITEM_NAME_ID;
  EditText nameEditField;

  int descriptionEditFieldId = R.id.ITEM_DESCRIPTION_ID;
  EditText descriptionEditField;

  int hasBattleSwitchId = R.id.SCRIPT_HAS_BATTLE_SCENE_ID;
  CheckBox hasBattleSwitch;

  int titleFieldId = R.id.CREATE_ITEM_TITLE_ID;
  TextView titleField;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new_item);

    createBtn = findViewById(createBtnId);
    createBtn.setOnClickListener(createDialogListener);

    cancelBtn = findViewById(cancelBtnId);
    cancelBtn.setOnClickListener(createDialogListener);

    nameEditField = findViewById(ameEditFieldId);
    int isScript = getIntent().getIntExtra("isScript", 0);

    if(isScript == 1){
      String description = getIntent().getStringExtra("description");
      descriptionEditField = findViewById(descriptionEditFieldId);
      descriptionEditField.setVisibility(View.VISIBLE);
      descriptionEditField.setText(description);

      int hasBattleSceneValue = getIntent().getIntExtra("hasBattleSceneValue", 0);

      hasBattleSwitch = findViewById(hasBattleSwitchId);
      hasBattleSwitch.setVisibility(View.VISIBLE);
      hasBattleSwitch.setChecked(hasBattleSceneValue == 1);
    }

    int title = getIntent().getIntExtra("title", 0);
    itemId = getIntent().getIntExtra("id", -1);

    String oldName = getIntent().getStringExtra("oldName");

    if(title != 0){
      titleField = findViewById(titleFieldId);
      titleField.setText(title);
    }

    nameEditField.setText(oldName);

  }

  View.OnClickListener createDialogListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if(v.getId() == createBtnId){
        Intent result = new Intent();
        result.putExtra("name", nameEditField.getText().toString());
        result.putExtra("id", itemId);
        int isScript = getIntent().getIntExtra("isScript", 0);
        if(isScript == 1) {
          result.putExtra("hasBattleSceneValue", hasBattleSwitch.isChecked() ? 1 : 0);
          result.putExtra("description", descriptionEditField.getText().toString());
        }
        setResult(RESULT_OK, result);
      } else {
        setResult(RESULT_CANCELED);
      }
      finish();
    }
  };
}



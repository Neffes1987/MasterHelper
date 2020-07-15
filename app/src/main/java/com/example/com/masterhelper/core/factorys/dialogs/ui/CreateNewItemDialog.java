package com.example.com.masterhelper.core.factorys.dialogs.ui;

import android.content.Intent;
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

  public final static String IS_SCRIPT = "isScript";
  public final static String IS_UPDATE = "isUpdate";
  public final static String IS_BATTLE = "hasBattleSceneValue";
  public final static String DESCRIPTION = "description";
  public final static String HIDE_DESCRIPTION = "hideDescription";
  public final static String OLD_NAME = "oldName";
  public final static String NAME = "name";
  public final static String ID = "id";

  public final static String TITLE = "title";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new_item);

    createBtn = findViewById(createBtnId);
    createBtn.setOnClickListener(createDialogListener);

    cancelBtn = findViewById(cancelBtnId);
    cancelBtn.setOnClickListener(createDialogListener);

    nameEditField = findViewById(R.id.ITEM_NAME_ID);
    int isScript = getIntent().getIntExtra(IS_SCRIPT, 0);
    boolean hideDescription = getIntent().getIntExtra(HIDE_DESCRIPTION, 0) == 1;
    int isUpdate = getIntent().getIntExtra(IS_UPDATE, 0);
    if(isUpdate > 0){
      createBtn.setText(R.string.update);
    }
    if(!hideDescription){
      String description = getIntent().getStringExtra(DESCRIPTION);
      descriptionEditField = findViewById(R.id.ITEM_DESCRIPTION_ID);
      descriptionEditField.setVisibility(View.VISIBLE);
      descriptionEditField.setText(description);
    }

    if(isScript == 1){

      int hasBattleSceneValue = getIntent().getIntExtra(IS_BATTLE, 0);

      hasBattleSwitch = findViewById(R.id.SCRIPT_HAS_BATTLE_SCENE_ID);
      hasBattleSwitch.setVisibility(View.VISIBLE);
      hasBattleSwitch.setChecked(hasBattleSceneValue == 1);
    }

    int title = getIntent().getIntExtra(TITLE, 0);
    itemId = getIntent().getIntExtra(ID, -1);

    String oldName = getIntent().getStringExtra(OLD_NAME);

    if(title != 0){
      titleField = findViewById(R.id.CREATE_ITEM_TITLE_ID);
      titleField.setText(title);
    }

    nameEditField.setText(oldName);

  }

  View.OnClickListener createDialogListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if(v.getId() == createBtnId){
        Intent result = new Intent();
        result.putExtra(NAME, nameEditField.getText().toString());
        result.putExtra(ID, itemId);
        boolean hideDescription = getIntent().getIntExtra(HIDE_DESCRIPTION, 0) == 1;
        if(!hideDescription){
          result.putExtra(DESCRIPTION, descriptionEditField.getText().toString());
        }
        int isScript = getIntent().getIntExtra(IS_SCRIPT, 0);
        if(isScript == 1) {
          result.putExtra(IS_BATTLE, hasBattleSwitch.isChecked() ? 1 : 0);
        }
        setResult(RESULT_OK, result);
      } else {
        setResult(RESULT_CANCELED);
      }
      finish();
    }
  };
}



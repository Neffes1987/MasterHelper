package com.example.masterhelper;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CreateNewItem extends AppCompatActivity {

  int createBtnId = R.id.ITEM_CREATE_BTN_ID;
  Button createBtn;

  int cancelBtnId = R.id.ITEM_CANCEL_BTN_ID;
  Button cancelBtn;

  int ameEditFieldId = R.id.ITEM_NAME_ID;
  EditText nameEditField;

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
    int title = getIntent().getIntExtra("title", 0);
    Log.i("TAG", "onCreate: " + title);
    if(title != 0){
      titleField = findViewById(titleFieldId);
      titleField.setText(title);
    }

  }

  View.OnClickListener createDialogListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      Log.i("TAG", "onClick: " + v);
      Intent result = new Intent();
      result.putExtra("name", nameEditField.getText().toString());
      setResult(RESULT_OK, result);
      finish();
    }
  };
}


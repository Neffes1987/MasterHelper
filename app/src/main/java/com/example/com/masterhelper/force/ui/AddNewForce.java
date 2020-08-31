package com.example.com.masterhelper.force.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.example.com.masterhelper.force.adapters.ForceDBAdapter;
import com.example.com.masterhelper.force.models.ForceModel;
import com.example.com.masterhelper.settings.ui.SettingList;
import com.example.masterhelper.R;

import java.util.ArrayList;


public class AddNewForce extends AppCompatActivity {
  public static final String ID = "id";
  public static final int ADD_GOALS_RESULT_CODE = 1;
  public static final int ADD_JOURNEY_RESULT_CODE = 2;
  private int currentId = 0;

  ViewPager relations;


  ForceDBAdapter coreForceAdapter = new ForceDBAdapter();
  ForceModel currentModel;
  EditText name;
  EditText description;
  RadioGroup type;

 ImageButton doneBtn;

  public void setRelations(ForceModel model) {
    this.relations = findViewById(R.id.FORCE_RELATIONS_VIEWER_ID);
    if(currentId == 0){
      relations.setVisibility(View.GONE);
      return;
    }
    relations.setCurrentItem(1); // выводим второй экран
  }

  public void setDoneBtn() {
    this.doneBtn = findViewById(R.id.FORCE_DONE_BTN_ID);
    this.doneBtn.setOnClickListener(commonListener);
  }

  public void setDeleteBtn() {
    ImageButton deleteBtn = findViewById(R.id.FORCE_DELETE_BTN_ID);
    if(currentId == 0){
      deleteBtn.setVisibility(View.GONE);
      return;
    }
    deleteBtn.setOnClickListener(commonListener);
  }

  public void setName(String name) {
    this.name = findViewById(R.id.FORCE_TITLE_NAME_ID);
    this.name. setText(name);
  }

  public void setDescription(String description) {
    this.description = findViewById(R.id.FORCE_DESCCRIPTION_ID);
    this.description.setText(description);
  }

  public void setType(ForceModel.ForceType type) {
    this.type = findViewById(R.id.FORCE_TYPE_ID);
    if(type == ForceModel.ForceType.person){
      this.type.check(R.id.FORCE_TYPE_SINGLE_ID);
    }
    if(type == ForceModel.ForceType.group){
      this.type.check(R.id.FORCE_TYPE_GROUP_ID);
    }
  }

  public ForceModel.ForceType getType() {
    if(type.getCheckedRadioButtonId() == R.id.FORCE_TYPE_SINGLE_ID){
      return ForceModel.ForceType.person;
    }
    if(type.getCheckedRadioButtonId() == R.id.FORCE_TYPE_GROUP_ID){
      return ForceModel.ForceType.group;
    }
    return null;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    currentId = getIntent().getIntExtra(ID, 0);
    if(currentId == 0){
      currentModel = new ForceModel();
      currentModel.initGeneralFields(0, "", "");
      setType(currentModel.getType());
    } else {
      currentModel = coreForceAdapter.get(currentId);
    }
    setContentView(R.layout.activity_dialog_add_new_force);
    setName(currentModel.getName());
    setDescription(currentModel.getDescription());
    setType(currentModel.getType());
    setDoneBtn();
    setDeleteBtn();
    setRelations(currentModel);
  }

  View.OnClickListener commonListener = v -> {
    switch (v.getId()){
      case R.id.FORCE_DONE_BTN_ID:
        currentModel.setName(name.getText().toString());
        currentModel.setDescription(description.getText().toString());
        currentModel.setType(getType());
        if(currentId == 0){
          coreForceAdapter.add(currentModel, 0);
        } else {
          coreForceAdapter.update(currentModel);
        }
        setResult(RESULT_OK);
        finish();
        break;
      case R.id.FORCE_DELETE_BTN_ID:
        coreForceAdapter.delete(currentId);
        setResult(RESULT_OK);
        finish();
        break;
      default:
    }
  };


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode != RESULT_OK){
      return;
    }
    switch (requestCode){
      case ADD_GOALS_RESULT_CODE: break;
      case ADD_JOURNEY_RESULT_CODE:
        ArrayList<String> selectedJourneys = data.getStringArrayListExtra(SettingList.EXTRA_SELECTED_LIST_ITEMS_IDS);
        if(selectedJourneys != null){
          coreForceAdapter.setSelectedJourney(currentId, selectedJourneys);
        }
        break;
    }

  }
}

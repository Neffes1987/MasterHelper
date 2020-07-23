package com.example.com.masterhelper.force.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.settings.adapters.ForceDBAdapter;
import com.example.com.masterhelper.force.models.ForceModel;
import com.example.com.masterhelper.force.viewpageradapters.AdaptersFactory;
import com.example.com.masterhelper.force.viewpageradapters.AddNewRelation;
import com.example.com.masterhelper.settings.ui.SettingList;
import com.example.masterhelper.R;

import static com.example.com.masterhelper.settings.SettingsFactory.GOAL_ITEM;

public class AddNewForce extends AppCompatActivity implements AddNewRelation.IAddNewRelation {
  public static final String ID = "id";
  public static final int ADD_GOALS_RESULT_CODE = 1;
  private int currentId = 0;

  ViewPager relations;


  ForceDBAdapter coreForceAdapter = new ForceDBAdapter();
  ForceModel currentModel;
  EditText name;
  EditText description;
  RadioGroup type;

  TextView goals;
  TextView advantages;
  TextView disadvantages;
  TextView own;
  TextView depended;
  TextView enemies;
  TextView cooperators;
  TextView involvedJourneys;

  ImageButton goalsSelector;
  ImageButton advantagesSelector;
  ImageButton disadvantagesSelector;
  ImageButton ownerSelector;
  ImageButton dependedSelector;
  ImageButton enemiesSelector;
  ImageButton cooperatorsSelector;
  ImageButton involvedJourneysSelector;
  ImageButton doneBtn;

  public void setRelations() {
    AdaptersFactory adaptersFactory = new AdaptersFactory(getSupportFragmentManager());
    adaptersFactory.setAdapterData(AdaptersFactory.AdapterTypes.goal, null);
    adaptersFactory.setAdapterData(AdaptersFactory.AdapterTypes.journeys, null);
    adaptersFactory.setAdapterData(AdaptersFactory.AdapterTypes.advantages, null);
    adaptersFactory.setAdapterData(AdaptersFactory.AdapterTypes.disadvantages, null);
    adaptersFactory.setAdapterData(AdaptersFactory.AdapterTypes.depended, null);
    adaptersFactory.setAdapterData(AdaptersFactory.AdapterTypes.owned, null);
    this.relations = findViewById(R.id.FORCE_RELATIONS_VIEWER_ID);
    relations.setAdapter(adaptersFactory); // устанавливаем адаптер
    relations.setCurrentItem(1); // выводим второй экран
  }

  public void setEnemies(ModelList enemies) {
    this.enemies = findViewById(R.id.FORCE_ENEMIES_ID);
    if(enemies !=null){
      this.enemies.setText(enemies.listToString());
    }
    this.enemiesSelector = findViewById(R.id.FORCE_ENEMIES_ADD_ID);
    this.enemiesSelector.setOnClickListener(commonListener);
  }

  public void setCooperators(ModelList cooperators) {
    this.cooperators = findViewById(R.id.FORCE_COOPERATORS_ID);
    this.cooperators.setText(cooperators.listToString());
    this.cooperatorsSelector=findViewById(R.id.FORCE_COOPERATORS_ADD_ID);
    this.cooperatorsSelector.setOnClickListener(commonListener);
  }

  public void setInvolvedJourneys(ModelList involvedJourneys) {
    this.involvedJourneys = findViewById(R.id.FORCE_INVOLVED_IN_JOURNEYS_ID);
    this.involvedJourneys.setText(involvedJourneys.listToString());
    this.involvedJourneysSelector.setOnClickListener(commonListener);
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

  public void setOwn(ModelList own) {
    this.own = findViewById(R.id.FORCE_OWN_ID);
    this.own.setText(own.listToString());
    this.ownerSelector = findViewById(R.id.FORCE_OWNER_ADD_ID);
    this.ownerSelector.setOnClickListener(commonListener);
  }

  public void setDepended(ModelList depended) {
    this.depended = findViewById(R.id.FORCE_DEPENDED_ID);
    this.depended.setText(depended.listToString());
    this.dependedSelector = findViewById(R.id.FORCE_DEPENDENCIES_ADD_ID);
    this.dependedSelector.setOnClickListener(commonListener);
  }

  public void setGoals(ModelList goals) {
    this.goals = findViewById(R.id.FORCE_GOALS_AND_MOTIVATION_ID);
    if(goals != null){
      this.goals.setText(goals.listToString());
    }
    this.goalsSelector = findViewById(R.id.FORCE_GOALS_ADD_ID);
    this.goalsSelector.setOnClickListener(commonListener);
  }

  public void setAdvantages(ModelList advantages) {
    this.advantages = findViewById(R.id.FORCE_ADVANTAGES);
    this.advantages.setText(advantages.listToString());
    this.advantagesSelector = findViewById(R.id.FORCE_ADVANCE_ADD_ID);
    this.advantagesSelector.setOnClickListener(commonListener);
  }

  public void setDisadvantages(ModelList disadvantages) {
    this.disadvantages = findViewById(R.id.FORCE_DISADVANTAGES_ID);
    this.disadvantages.setText(disadvantages.listToString());
    this.disadvantagesSelector = findViewById(R.id.FORCE_DISADVANTAGES_ADD_ID);
    this.disadvantagesSelector.setOnClickListener(commonListener);
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
    setContentView(R.layout.activity_add_new_force);
    setName(currentModel.getName());
    setDescription(currentModel.getDescription());
    setType(currentModel.getType());
    setDoneBtn();
    setDeleteBtn();

    setGoals(null);
    setEnemies(null);
    setRelations();
  }

  View.OnClickListener commonListener = v -> {
    Intent intent;
    switch (v.getId()){
      case R.id.FORCE_GOALS_ADD_ID:
        intent = new Intent(this, SettingList.class);
        intent.putExtra(SettingList.EXTRA_TYPE, GOAL_ITEM);
        intent.putExtra(SettingList.EXTRA_IS_SELECTABLE, true);
        intent.putExtra(SettingList.EXTRA_SETTING_TITLE, R.string.force_goal_motivation_title);
        startActivityForResult(intent, ADD_GOALS_RESULT_CODE);
        break;
      case R.id.FORCE_DISADVANTAGES_ADD_ID:
        break;
      case R.id.FORCE_ADVANCE_ADD_ID:
        break;
      case R.id.FORCE_OWNER_ADD_ID:
        break;
      case R.id.FORCE_DEPENDENCIES_ADD_ID:
        break;
      case R.id.FORCE_DONE_BTN_ID:
        currentModel.setName(name.getText().toString());
        currentModel.setDescription(description.getText().toString());
        currentModel.setType(getType());
        if(currentId == 0){
          coreForceAdapter.add(currentModel);
        } else {
          coreForceAdapter.update(currentModel);
        }
        setResult(RESULT_OK);
        finish();
        break;
      case R.id.FORCE_ENEMIES_ADD_ID:
        break;
      case R.id.FORCE_DELETE_BTN_ID:
        coreForceAdapter.delete(currentId);
        setResult(RESULT_OK);
        finish();
        break;
      case R.id.FORCE_COOPERATORS_ADD_ID:
        break;
      default:
    }
  };

  @Override
  public void relationChanged(View v) {

  }
}

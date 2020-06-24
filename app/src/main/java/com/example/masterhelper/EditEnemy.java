package com.example.masterhelper;

import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.models.ACHIEVE_CONST_TAGS;
import com.example.masterhelper.models.AchieveModel;
import com.example.masterhelper.ui.ScriptBottomButtonsFragment.ScriptBottomButtonsFragment;
import com.example.masterhelper.ui.viewCharacteristicRow.Abilities;
import com.example.masterhelper.ui.viewCharacteristicRow.ViewCharacteristicRow;

import java.util.LinkedHashMap;

public class EditEnemy extends AppCompatActivity implements ViewCharacteristicRow.IViewCharacteristicRow, ScriptBottomButtonsFragment.IScriptBottomButtonsFragment {
  /** редактировать имя врага */
  int enemyNameId = R.id.ENEMY_EDIT_NAME_ID;
  EditText enemyName;

  /** редактировать описание врага */
  int enemyDescriptionId = R.id.ENEMY_EDIT_DESCRIPTION_ID;
  EditText enemyDescription;

  /** Созданный бандл */
  Bundle savedInstanceState;

  /** */
  Abilities abilities;

  FragmentManager fragmentManager;

  /** Характеристики врага */
  private final LinkedHashMap<Integer, AchieveModel> achieves = new LinkedHashMap<>();

  String description = "slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg ";
  String title = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.savedInstanceState = savedInstanceState;
    setContentView(R.layout.activity_edit_enemy);
    achieves.put(achieves.size(), new AchieveModel(achieves.size(), "Здоровье", 10, ACHIEVE_CONST_TAGS.HEALTH));
    fragmentManager = getSupportFragmentManager();
    abilities = new Abilities(fragmentManager, this, achieves);
    long id = getIntent().getLongExtra("id", 0);
    ScriptBottomButtonsFragment controls = (ScriptBottomButtonsFragment) fragmentManager.findFragmentById(R.id.SCRIPT_ENEMY_BOTTOM_BUTTONS);
    if(controls != null){
      controls.setControlMode(false);
      updateView(id);
    }

  }

  private void updateDescription(String description){
    if(enemyDescription == null) {
      enemyDescription = findViewById(enemyDescriptionId);
    }
    enemyDescription.setText(description);
  }

  private void updateTitle(String title){
    if(enemyName == null){
      enemyName = findViewById(enemyNameId);
    }
    enemyName.setText(title);
  }



  private void updateView(long enemyID){
    updateDescription(description);
    updateTitle(title);
    abilities.updateAbilities(achieves);
    abilities.initAddAchieveRow(enemyID);
  }

  @Override
  public void changeValue(int id, int value) {

  }

  @Override
  public void deleteRow(int rowId) {
    abilities.deleteAbility(rowId);
  }

  @Override
  public void addNewRow(String title) {
    achieves.put(achieves.size(), new AchieveModel(achieves.size(), title, 0 ));
    abilities.updateAbilities(achieves);
  }

  @Override
  public void setScriptBottomButtonsAction(ScriptBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {

  }
}

package com.example.masterhelper;

import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.masterhelper.models.ACHIEVE_CONST_TAGS;
import com.example.masterhelper.models.AchieveModel;
import com.example.masterhelper.ui.ScriptBottomButtonsFragment.ScriptBottomButtonsFragment;
import com.example.masterhelper.ui.viewCharacteristicRow.ViewCharacteristicRow;

import java.util.LinkedHashMap;

public class EditEnemy extends AppCompatActivity implements ViewCharacteristicRow.IViewCharacteristicRow, ScriptBottomButtonsFragment.IScriptBottomButtonsFragment {
  /** редактировать имя врага */
  int enemyNameId = R.id.ENEMY_EDIT_NAME_ID;
  EditText enemyName;

  /** список характеристик врага */
  int enemyAbilitiesRowWrapperId = R.id.ENEMY_ABILITIES_ROW_WRAPPER_ID;
  LinearLayout enemyAbilitiesRowWrapper;

  /** редактировать описание врага */
  int enemyDescriptionId = R.id.ENEMY_EDIT_DESCRIPTION_ID;
  EditText enemyDescription;

  /** Строка добавления характеристик */
  int addNewFragmentId = R.id.ADD_NEW_CHARACTERISTIC_ID;
  ViewCharacteristicRow addNewFragment;

  /** Созданный бандл */
  Bundle savedInstanceState;

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
    achieves.put(achieves.size(), new AchieveModel(achieves.size(), "Урон", 10 ));
    achieves.put(achieves.size(), new AchieveModel(achieves.size(), "Инициатива", 10 ));
    fragmentManager = getSupportFragmentManager();

    long id = getIntent().getLongExtra("id", 0);
    ScriptBottomButtonsFragment controls = (ScriptBottomButtonsFragment) fragmentManager.findFragmentById(R.id.SCRIPT_ENEMY_BOTTOM_BUTTONS);
    controls.setControlMode(false);
    updateView(id);
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

  private void setAddAchieveRow(long enemyID){
    FragmentManager fm = getSupportFragmentManager();

    addNewFragment = (ViewCharacteristicRow) fm.findFragmentById(addNewFragmentId);
    if(addNewFragment != null){
      addNewFragment.setIsNew(enemyID == 0);
    }
  }

  private String getAchieveRowTag(long rowId){
    return "TAG_"+rowId;
  }

  private void addNewAbility(AchieveModel achieve, FragmentTransaction fragmentTransaction){
    String tag = getAchieveRowTag(achieve.getId());
    if(fragmentManager.findFragmentByTag(tag) != null){
      return;
    }
    ViewCharacteristicRow viewCharacteristicRow = new ViewCharacteristicRow();
    fragmentTransaction.add(enemyAbilitiesRowWrapperId, viewCharacteristicRow, tag);
    viewCharacteristicRow.setDefaultValues(achieve, false);
  }

  private void updateAbilities(LinkedHashMap<Integer, AchieveModel> achieves){
    if(enemyAbilitiesRowWrapper == null){
      enemyAbilitiesRowWrapper = findViewById(enemyAbilitiesRowWrapperId);
    }

    // получаем экземпляр FragmentTransaction

    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    for(AchieveModel achieve: achieves.values()){
      addNewAbility(achieve, fragmentTransaction);
    };
    fragmentTransaction.commit();
  }

  private void updateView(long enemyID){
    updateDescription(description);
    updateTitle(title);
    updateAbilities(achieves);
    setAddAchieveRow(enemyID);
  }

  @Override
  public void changeValue(long id, int value) {

  }

  @Override
  public void deleteRow(long rowId) {
    achieves.remove(rowId);
    String tag = getAchieveRowTag(rowId);
    Fragment row = fragmentManager.findFragmentByTag(tag);
    if(row != null){
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(enemyAbilitiesRowWrapperId, null, tag);
      fragmentTransaction.commit();
    }


  }

  @Override
  public void addNewRow(String title) {
    achieves.put(achieves.size(), new AchieveModel(achieves.size(), title, 0 ));
    updateAbilities(achieves);
  }

  @Override
  public void setScriptBottomButtonsAction(ScriptBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {

  }
}

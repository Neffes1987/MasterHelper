package com.example.masterhelper;

import android.database.Cursor;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.EnemyContract;
import com.example.masterhelper.data.contracts.SceneContract;
import com.example.masterhelper.models.ACHIEVE_CONST_TAGS;
import com.example.masterhelper.models.AchieveModel;
import com.example.masterhelper.models.EnemyModel;
import com.example.masterhelper.models.SceneRecycleDataModel;
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

  /** класс для работы с характеристиками */
  Abilities abilities;

  FragmentManager fragmentManager;

  DbHelpers dbHelpers;

  /** Характеристики врага */
  private final LinkedHashMap<Integer, AchieveModel> achieves = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_enemy);
    dbHelpers = new DbHelpers(this);

    int id = getIntent().getIntExtra("id", 0);

    achieves.put(achieves.size(), new AchieveModel(achieves.size(), "Здоровье", 10, ACHIEVE_CONST_TAGS.HEALTH));
    fragmentManager = getSupportFragmentManager();
    abilities = new Abilities(fragmentManager, this, achieves);



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



  private void updateView(int enemyID){
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

  private void saveAbilities(){

  }

  private void savePerson(){
    //String name = enemyName.getText().toString().trim();
    //int totalHealth
   // String sqlQuery = EnemyContract.addItemQuery(new EnemyModel(0, name, achieves, ))
  }

  private void saveNewPerson(){
    String name = enemyName.getText().toString().trim();
    if(name.length() > 0){
      savePerson();
    } else {
      Toast.makeText(this, "Укажите имя персонажа", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void setScriptBottomButtonsAction(ScriptBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {
    switch (scriptBottomButtonsAction){
      case save:
        saveNewPerson();
        break;
      case delete:
        break;
    }
  }
}

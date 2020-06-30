package com.example.masterhelper.ui.enemies;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.masterhelper.ListItemsDialog;
import com.example.masterhelper.R;
import com.example.masterhelper.models.ACHIEVE_CONST_TAGS;
import com.example.masterhelper.models.AbilityModel;
import com.example.masterhelper.models.EnemyModel;
import com.example.masterhelper.ui.viewCharacteristicRow.Abilities;
import com.example.masterhelper.ui.viewCharacteristicRow.AbilityDBAdapter;
import com.example.masterhelper.ui.viewCharacteristicRow.ViewCharacteristicRow;
 
import java.util.LinkedHashMap;

public class EditEnemy extends AppCompatActivity implements ViewCharacteristicRow.IViewCharacteristicRow, EnemyBottomButtonsFragment.IScriptBottomButtonsFragment, ListItemsDialog.IButtonsEvents {
  /** редактировать имя врага */
  EditText enemyName;

  /** редактировать имя врага */
  TextView maxHealthValue;

  final int  HEALTH_POSITION = 0;
  final int  ORDERING_POSITION = -1;

  /** редактировать описание врага */
  EditText enemyDescription;

  Button abilityAddButton;

  /** класс для работы с характеристиками */
  Abilities abilities;

  /** класс для работы с данными противника */
  EnemyDBAdapter enemyDBAdapter;

  FragmentManager fragmentManager;

  AbilityDBAdapter abilityDBAdapter;

  int scriptID;
  int enemyId;

  EnemyModel currentEnemy;
  AbilityModel healthAbility = new AbilityModel(HEALTH_POSITION, "Здоровье", 0, ACHIEVE_CONST_TAGS.HEALTH);
  AbilityModel orderingAbility = new AbilityModel(ORDERING_POSITION, "Порядок хода", 0, ACHIEVE_CONST_TAGS.ORDERING);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_enemy);

    scriptID = getIntent().getIntExtra("scriptId", 0);
    enemyId = getIntent().getIntExtra("enemyId", 0);

    abilityDBAdapter = new AbilityDBAdapter(this);
    enemyDBAdapter = new EnemyDBAdapter(this);

    abilityAddButton = findViewById(R.id.ENEMY_ABILITIES_ADD_BTN_ID);
    maxHealthValue = findViewById(R.id.MAX_HEALTH_VALUE_ID);

    abilityAddButton.setOnClickListener(v -> showAbilitiesPopup());



    if(scriptID > 0){
      currentEnemy = enemyDBAdapter.getEnemyById(enemyId);
    }

    if(currentEnemy == null){
      currentEnemy = new EnemyModel(0, "", "", 0,0, 0);
    }

    healthAbility.setValue(currentEnemy.getCurrentHealth());
    orderingAbility.setValue(currentEnemy.getOrdering());

    maxHealthValue.setText(currentEnemy.getTotalHealth() + "");

    updateDescription(currentEnemy.getDescription());
    updateTitle(currentEnemy.getName());

    fragmentManager = getSupportFragmentManager();

    abilities = new Abilities(fragmentManager, this);
    abilities.setAbilityToList(healthAbility);
    abilities.setAbilityToList(orderingAbility);
    abilities.setAbilitiesView(abilityDBAdapter.getSettingsAbilitiesListByEnemy(enemyId));
    abilities.setAbilitiesListView(abilityDBAdapter.getSettingsAbilitiesList());

    EnemyBottomButtonsFragment controls = (EnemyBottomButtonsFragment) fragmentManager.findFragmentById(R.id.SCRIPT_ENEMY_BOTTOM_BUTTONS);
    if(controls != null){
      controls.setControlMode(false);
      updateView();
    }

  }

  private void updateDescription(String description){
    if(enemyDescription == null) {
      enemyDescription = findViewById(R.id.ENEMY_EDIT_DESCRIPTION_ID);
    }
    enemyDescription.setText(description);
  }

  private void updateTitle(String title){
    if(enemyName == null){
      enemyName = findViewById(R.id.ENEMY_EDIT_NAME_ID);
    }
    enemyName.setText(title);
  }



  private void updateView(){
    abilities.updateAbilities();
  }

  @Override
  public void changeValue(int id, int value) {
    if(id == HEALTH_POSITION){
      healthAbility.setValue(value);
      if(enemyId == 0){
        maxHealthValue.setText(value + "");
      }
      return;
    }
    if(id == ORDERING_POSITION){
      orderingAbility.setValue(value);
      return;
    }
    abilities.setAbilityValue(id, value);
  }

  @Override
  public void deleteRow(int rowId) {
    abilities.deleteAbility(rowId);
  }


  private void savePerson(String title, String description, int scriptID){
    int currentHealth = healthAbility.getValue();
    int currentOrdering = orderingAbility.getValue();
    int totalHealth = currentEnemy.getTotalHealth();

    if(totalHealth < currentHealth){
      currentEnemy.setTotalHealth(currentHealth);
    }

    currentEnemy.setCurrentHealth(currentHealth);
    currentEnemy.setName(title);
    currentEnemy.setDescription(description);
    currentEnemy.setOrdering(currentOrdering);

    if(currentEnemy.getId() != 0){
      enemyDBAdapter.updateEnemy(currentEnemy);
      abilityDBAdapter.addAbilitiesByEnemyId(abilities.getUntaggedAbilities(), currentEnemy.getId());
      Toast.makeText(this, title + " обновлен", Toast.LENGTH_LONG).show();
    } else {

      if(currentHealth == 0){
        Toast.makeText(this, "Начальное здоровье должно быть больше 0", Toast.LENGTH_LONG).show();
        return;
      }
      currentEnemy.setId(enemyDBAdapter.addNewEnemy(currentEnemy, scriptID));
      abilityDBAdapter.addAbilitiesByEnemyId(abilities.getUntaggedAbilities(), currentEnemy.getId());
      Toast.makeText(this, title + " добавлен", Toast.LENGTH_LONG).show();
    }

    setResult(RESULT_OK);
    finish();
  }

  private void saveNewPerson(){
    String name = enemyName.getText().toString().trim();
    String description = enemyDescription.getText().toString().trim();

    if(name.length() > 0){
      savePerson(name, description, scriptID);
    } else {
      Toast.makeText(this, "Укажите имя персонажа", Toast.LENGTH_LONG).show();
    }
  }

  private void deletePerson(){
    enemyDBAdapter.deleteEnemy(enemyId);
    Toast.makeText(this, currentEnemy.getName() + " удален", Toast.LENGTH_LONG).show();
    setResult(RESULT_OK);
    finish();
  }

  private void showAbilitiesPopup(){
    LinkedHashMap<Integer, AbilityModel> abilitiesForPopup = abilities.getAbilitiesListView();
    Log.i("TAG", "showAbilitiesPopup: " + abilitiesForPopup);
    if(abilitiesForPopup.size() == 0){
      Toast.makeText(this, "Нет доступных характеристик", Toast.LENGTH_LONG).show();
      return;
    }

    String[] abilitiesNames = new String[abilitiesForPopup.size()];

    int valueIdx = 0;
    for (int key: abilitiesForPopup.keySet()) {
      abilitiesNames[valueIdx] = abilitiesForPopup.get(key).getName();
      valueIdx += 1;
    }

    ListItemsDialog myDialogFragment = new ListItemsDialog(abilitiesNames, "Список характеристик");

    FragmentTransaction transaction = fragmentManager.beginTransaction();
    myDialogFragment.show(transaction, "dialog");
  }

  @Override
  public void setScriptBottomButtonsAction(EnemyBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {
    switch (scriptBottomButtonsAction){
      case save:
        saveNewPerson();
        break;
      case delete:
        deletePerson();
        break;
    }
  }

  @Override
  public void cancel() {}

  @Override
  public void accepted(boolean[] selectedItems) {
    LinkedHashMap<Integer, AbilityModel> abilitiesForPopup = abilities.getAbilitiesListView();
    int valueIdx = 0;
    for (int key: abilitiesForPopup.keySet()) {
      if(selectedItems[valueIdx]){
        AbilityModel item = abilitiesForPopup.get(key);
        abilities.setAbilityToList(item);
      }
      valueIdx += 1;
    }
    updateView();
  }
}

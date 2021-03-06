package com.example.com.masterhelper.enemies.ui;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.components.dialogs.dialogs.DeleteDialog;
import com.example.com.masterhelper.core.components.dialogs.dialogs.InputDialog;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.masterhelper.R;
import com.example.com.masterhelper.enemies.models.AbilityModel;
import com.example.com.masterhelper.enemies.models.EnemyModel;
import com.example.com.masterhelper.enemies.adapters.AbilityDBAdapter;
import com.example.com.masterhelper.enemies.adapters.EnemyDBAdapter;
import com.example.com.masterhelper.core.components.dialogs.dialogs.MultiChooseDialog;


import static android.content.DialogInterface.BUTTON_POSITIVE;

public class EditEnemy extends AppCompatActivity implements ViewCharacteristicRow.IViewCharacteristicRow, EnemyBottomButtonsFragment.IScriptBottomButtonsFragment {
  /** редактировать имя врага */
  EditText enemyName;

  /** редактировать имя врага */
  TextView maxHealthValue;

  final int  HEALTH_POSITION = -1;
  final int  ORDERING_POSITION = -2;

  /** редактировать описание врага */
  EditText enemyDescription;

  Button abilityAddButton;

  /** класс для работы с характеристиками */
  Abilities abilities;

  /** класс для работы с данными противника */
  EnemyDBAdapter enemyDBAdapter = new EnemyDBAdapter();

  FragmentManager fragmentManager;

  AbilityDBAdapter abilityDBAdapter = new AbilityDBAdapter();

  InputDialog inputDialog;
  DeleteDialog deleteDialog;
  MultiChooseDialog multiChooseDialog;

  private void setDialogs(){
    inputDialog = new InputDialog(this, getSupportFragmentManager());
    deleteDialog = new DeleteDialog(this);
    multiChooseDialog = new MultiChooseDialog(this);
  }

  int scriptID;
  int enemyId;

  EnemyModel currentEnemy;
  AbilityModel healthAbility = new AbilityModel(HEALTH_POSITION, "Здоровье", 0, AbilityModel.ACHIEVE_CONST_TAGS.HEALTH);
  AbilityModel orderingAbility = new AbilityModel(ORDERING_POSITION, "Порядок хода", 0, AbilityModel.ACHIEVE_CONST_TAGS.ORDERING);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog_edit_enemy);

    scriptID = getIntent().getIntExtra("scriptId", 0);
    enemyId = getIntent().getIntExtra("enemyId", 0);

    abilityAddButton = findViewById(R.id.ENEMY_ABILITIES_ADD_BTN_ID);
    maxHealthValue = findViewById(R.id.MAX_HEALTH_VALUE_ID);

    abilityAddButton.setOnClickListener(v -> showAbilitiesPopup());



    if(scriptID > 0){
      currentEnemy = enemyDBAdapter.get(enemyId);
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
    abilities.setAbilitiesView(abilityDBAdapter.getListByParentId(enemyId));
    abilities.setAbilitiesListView(abilityDBAdapter.getSettingsAbilitiesList());

    EnemyBottomButtonsFragment controls = (EnemyBottomButtonsFragment) fragmentManager.findFragmentById(R.id.SCRIPT_ENEMY_BOTTOM_BUTTONS);
    if(controls != null){
      controls.setControlMode(false);
      updateView();
    }

    setDialogs();
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

    Intent intent = new Intent();

    if(currentEnemy.getId() != 0){
      enemyDBAdapter.update(currentEnemy);
      abilityDBAdapter.addAbilitiesByEnemyId(abilities.getUntaggedAbilities(), currentEnemy.getId());
      Toast.makeText(this, title + " обновлен", Toast.LENGTH_LONG).show();
      intent.putExtra("enemyId", currentEnemy.getId());
    } else {

      if(currentHealth == 0){
        Toast.makeText(this, "Начальное здоровье должно быть больше 0", Toast.LENGTH_LONG).show();
        return;
      }
      enemyDBAdapter.add(currentEnemy, scriptID);
      int lastAddedEnemy = enemyDBAdapter.get(-1).getId();
      currentEnemy.setId(lastAddedEnemy);
      abilityDBAdapter.addAbilitiesByEnemyId(abilities.getUntaggedAbilities(), currentEnemy.getId());
      Toast.makeText(this, title + " добавлен", Toast.LENGTH_LONG).show();
      intent.putExtra("enemyId", lastAddedEnemy);
    }

    setResult(RESULT_OK, intent);
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
    enemyDBAdapter.delete(enemyId);
    Toast.makeText(this, currentEnemy.getName() + " удален", Toast.LENGTH_LONG).show();
    Intent intent = new Intent();
    intent.putExtra("deleted", true);
    intent.putExtra("enemyId", enemyId);
    setResult(RESULT_OK, intent);
    finish();
  }

  private void showAbilitiesPopup(){
    ModelList abilitiesForPopup = abilities.getAbilitiesListView();
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

    multiChooseDialog.setListOfItems(abilitiesNames);
    multiChooseDialog.setTitle(R.string.abilities_settings);
    multiChooseDialog.setOnResolveListener((dialogInterface, id) -> {
      if(id == BUTTON_POSITIVE){
        accepted(multiChooseDialog.getSelectedItems());
      }
    });
    multiChooseDialog.show();
  }

  @Override
  public void setScriptBottomButtonsAction(EnemyBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {
    switch (scriptBottomButtonsAction){
      case save:
        saveNewPerson();
        break;
      case delete:
        if(enemyId > 0){
          deleteDialog.setOnResolveListener((dialogInterface, id) -> {
            deletePerson();
          });
          deleteDialog.show();
        } else {
          setResult(RESULT_CANCELED);
          finish();
        }

        break;
    }
  }

  public void accepted(boolean[] selectedItems) {
    ModelList abilitiesForPopup = abilities.getAbilitiesListView();
    int valueIdx = 0;
    for (int key: abilitiesForPopup.keySet()) {
      if(selectedItems[valueIdx]){
        DataModel item = abilitiesForPopup.get(key);
        abilities.setAbilityToList(item);
      }
      valueIdx += 1;
    }
    updateView();
  }
}

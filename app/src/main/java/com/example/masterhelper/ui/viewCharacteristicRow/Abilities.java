package com.example.masterhelper.ui.viewCharacteristicRow;

import android.app.Activity;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.masterhelper.R;
import com.example.masterhelper.models.AchieveModel;

import java.util.LinkedHashMap;

/** Класс для работы со способностями */
public class Abilities {
  /** Список характеристик */
  private LinkedHashMap<Integer, AchieveModel> achieves;

  /** Менеджер фрагментов из активности */
  FragmentManager fm;

  /** Строка добавления характеристики */
  int addNewFragmentId = R.id.ADD_NEW_CHARACTERISTIC_ID;
  ViewCharacteristicRow addNewFragment;

  /** список характеристик врага */
  int enemyAbilitiesRowWrapperId = R.id.ENEMY_ABILITIES_ROW_WRAPPER_ID;
  LinearLayout enemyAbilitiesRowWrapper;

  /** получить список характеристик */
  public LinkedHashMap<Integer, AchieveModel> getAchieves() {
    return achieves;
  }

  /** обновить список характеристик */
  public void setAchieves(LinkedHashMap<Integer, AchieveModel> achieves) {
    this.achieves = achieves;
  }

  /** конструктор класса */
  public Abilities(FragmentManager fm, Activity context, LinkedHashMap<Integer, AchieveModel> achieves){
    this.fm = fm;
    this.achieves = achieves;
    enemyAbilitiesRowWrapper = context.findViewById(enemyAbilitiesRowWrapperId);
  }

  /** инициализация строки добавления характеристик */
  public void initAddAchieveRow(long enemyID){
    addNewFragment = (ViewCharacteristicRow) fm.findFragmentById(addNewFragmentId);
    if(addNewFragment != null){
      addNewFragment.setIsNew(enemyID == 0);
    }
  }

  /** получить тег фрагмента */
  public String getAchieveRowTag(long rowId){
    return "TAG_"+rowId;
  }

  /** добавить новую характеристику в список существующих */
  private void addNewAbilityToView(AchieveModel achieve, FragmentTransaction fragmentTransaction){
    String tag = getAchieveRowTag(achieve.getId());
    if(fm.findFragmentByTag(tag) != null){
      return;
    }
    ViewCharacteristicRow viewCharacteristicRow = new ViewCharacteristicRow();
    fragmentTransaction.add(enemyAbilitiesRowWrapperId, viewCharacteristicRow, tag);
    viewCharacteristicRow.setDefaultValues(achieve, false);
  }


  /** обновить список характеристик */
  public void updateAbilities(LinkedHashMap<Integer, AchieveModel> achieves){
    setAchieves(achieves);
    if(enemyAbilitiesRowWrapper == null){
      return;
    }

    FragmentTransaction fragmentTransaction = fm.beginTransaction();
    for(AchieveModel achieve: achieves.values()){
      addNewAbilityToView(achieve, fragmentTransaction);
    }
    fragmentTransaction.commit();
  }

  /** Удалить характеристику из списка */
  public void deleteAbility(int rowId){
    achieves.remove(rowId);
    String tag = getAchieveRowTag(rowId);
    Fragment row = fm.findFragmentByTag(tag);
    if(row != null){
      FragmentTransaction fragmentTransaction = fm.beginTransaction();
      fragmentTransaction.remove(row);
      fragmentTransaction.commit();
    }
  }

}

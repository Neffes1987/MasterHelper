package com.example.masterhelper.ui.viewCharacteristicRow;

import android.app.Activity;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.masterhelper.R;
import com.example.masterhelper.models.AbilityModel;

import java.util.LinkedHashMap;

/** Класс для работы со способностями */
public class Abilities {
  /** Список характеристик */
  private LinkedHashMap<Integer, AbilityModel> achieves;

  /** Менеджер фрагментов из активности */
  FragmentManager fm;

  /** список характеристик врага */
  int enemyAbilitiesRowWrapperId = R.id.ENEMY_ABILITIES_ROW_WRAPPER_ID;
  LinearLayout enemyAbilitiesRowWrapper;


  /** обновить список характеристик */
  public void setAchieves(LinkedHashMap<Integer, AbilityModel> achieves) {
    this.achieves = achieves;
  }

  /** конструктор класса */
  public Abilities(FragmentManager fm, Activity context, LinkedHashMap<Integer, AbilityModel> achieves){
    this.fm = fm;
    this.achieves = achieves;
    enemyAbilitiesRowWrapper = context.findViewById(enemyAbilitiesRowWrapperId);
  }

  /** получить тег фрагмента */
  public String getAchieveRowTag(long rowId){
    return "TAG_"+rowId;
  }

  /** добавить новую характеристику в список существующих */
  private void addNewAbilityToView(AbilityModel achieve, FragmentTransaction fragmentTransaction){
    String tag = getAchieveRowTag(achieve.getId());
    if(fm.findFragmentByTag(tag) != null){
      return;
    }
    ViewCharacteristicRow viewCharacteristicRow = new ViewCharacteristicRow();
    fragmentTransaction.add(enemyAbilitiesRowWrapperId, viewCharacteristicRow, tag);
    viewCharacteristicRow.setDefaultValues(achieve);
  }


  /** обновить список характеристик */
  public void updateAbilities(LinkedHashMap<Integer, AbilityModel> achieves){
    setAchieves(achieves);
    if(enemyAbilitiesRowWrapper == null){
      return;
    }

    FragmentTransaction fragmentTransaction = fm.beginTransaction();
    for(AbilityModel achieve: achieves.values()){
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

package com.example.com.masterhelper.ui.viewCharacteristicRow;

import android.app.Activity;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.appconfig.models.AbilityModel;

import java.util.LinkedHashMap;

/** Класс для работы со способностями */
public class Abilities {
  /** Список характеристик */
  private LinkedHashMap<Integer, AbilityModel> abilitiesView = new LinkedHashMap<>();

  private ModelList abilitiesListView = new ModelList();

  /** Менеджер фрагментов из активности */
  FragmentManager fm;

  /** список характеристик врага */
  int enemyAbilitiesRowWrapperId = R.id.ENEMY_ABILITIES_ROW_WRAPPER_ID;
  LinearLayout enemyAbilitiesRowWrapper;

  public void setAbilityToList(DataModel item){
    abilitiesView.put(item.getId(), (AbilityModel) item);
  }

  public void setAbilityValue(int rowId, int value){
    AbilityModel abilityModel = abilitiesView.get(rowId);
    if(abilityModel != null){
      abilityModel.setValue(value);
    }
  }


  /** обновить список характеристик */
  public void setAbilitiesView(ModelList abilitiesView) {
    for (DataModel item: abilitiesView.values()) {
      setAbilityToList(item);
    }
  }

  /** конструктор класса */
  public Abilities(FragmentManager fm, Activity context){
    this.fm = fm;
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
  public void updateAbilities(){
    if(enemyAbilitiesRowWrapper == null){
      return;
    }

    FragmentTransaction fragmentTransaction = fm.beginTransaction();
    for(AbilityModel achieve: abilitiesView.values()){
      addNewAbilityToView(achieve, fragmentTransaction);
    }
    fragmentTransaction.commit();
  }

  /** Удалить характеристику из списка */
  public void deleteAbility(int rowId){
    abilitiesView.remove(rowId);
    String tag = getAchieveRowTag(rowId);
    Fragment row = fm.findFragmentByTag(tag);
    if(row != null){
      FragmentTransaction fragmentTransaction = fm.beginTransaction();
      fragmentTransaction.remove(row);
      fragmentTransaction.commit();
    }
  }


  public LinkedHashMap<Integer, AbilityModel> getUntaggedAbilities(){
    LinkedHashMap<Integer, AbilityModel> result = new LinkedHashMap<>();
    for (AbilityModel abilityModel: abilitiesView.values()) {
      if(abilityModel.getTag() == null){
        result.put(abilityModel.getId(), abilityModel);
      }
    }
    return result;
  }



  public void setAbilitiesListView(ModelList abilitiesListView) {
    this.abilitiesListView = abilitiesListView;
  }

  public ModelList getAbilitiesListView() {
    ModelList result = new ModelList();
    for (DataModel abilityModel: abilitiesListView.values()) {
      if(abilitiesView.get(abilityModel.getId()) == null){
        result.addToList(abilityModel);
      }
    }
    return result;
  }
}

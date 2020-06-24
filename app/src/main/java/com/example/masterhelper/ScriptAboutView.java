package com.example.masterhelper;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.models.ACHIEVE_CONST_TAGS;
import com.example.masterhelper.models.AchieveModel;
import com.example.masterhelper.ui.ScriptBottomButtonsFragment.ScriptBottomButtonsFragment;
import com.example.masterhelper.ui.viewCharacteristicRow.Abilities;
import com.example.masterhelper.ui.viewCharacteristicRow.ViewCharacteristicRow;

import java.util.LinkedHashMap;
import java.util.Objects;

public class ScriptAboutView extends AppCompatActivity implements ScriptBottomButtonsFragment.IScriptBottomButtonsFragment, ViewCharacteristicRow.IViewCharacteristicRow {
  /** */
  int activityScriptAboutViewLayout = R.layout.activity_script_about_view;

  /** редактировать описание врага */
  int enemyDescriptionId = R.id.ENEMY_DESCRIPTION_ID;
  TextView enemyDescription;

  /** класс для работы с характеристиками */
  Abilities abilities;

  String description = "slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg ";
  String title = "Имя врага, сгенерированное или прописанное";
  /** Характеристики врага */
  private final LinkedHashMap<Integer, AchieveModel> achieves = new LinkedHashMap<>();

  private void updateToolbarTitle(String title){
    // получаем указатель на тулбар активированного в главном компоненте
    Objects.requireNonNull(getSupportActionBar()).setTitle(title);
  }

  private void updateDescription(String description){
    if(enemyDescription == null) {
      enemyDescription = findViewById(enemyDescriptionId);
    }
    enemyDescription.setText(description);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScriptAboutViewLayout);
    updateToolbarTitle(title);
    updateDescription(description);

    FragmentManager fm = getSupportFragmentManager();
    achieves.put(achieves.size(), new AchieveModel(achieves.size(), "Здоровье", 10, ACHIEVE_CONST_TAGS.HEALTH));
    abilities = new Abilities(fm, this, achieves);
    abilities.updateAbilities(achieves);

    int enemyId = getIntent().getIntExtra("id", 0);
    boolean readonly = getIntent().getBooleanExtra("readonly", false);
    setMode(readonly);
    setListData(enemyId);
  }

  private void setMode(boolean readonly) {

  }

  void setListData(int enemyId){

  }

  @Override
  public void setScriptBottomButtonsAction(ScriptBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {
    Log.i("TAG", "onFragmentInteraction: " + scriptBottomButtonsAction);
  }

  @Override
  public void changeValue(int id, int value) {

  }

  @Override
  public void deleteRow(int rowId) {

  }

  @Override
  public void addNewRow(String title) {

  }

  public View.OnClickListener onClickBtn = v -> {
    int btnId = v.getId();
    switch (btnId){
      case R.id.DUPLICATE_BTN_ID:
        break;
      case R.id.DELETE_BUTTON_ID:
        break;
      case R.id.NEXT_BTN_ID:
        break;
    }
  };
}

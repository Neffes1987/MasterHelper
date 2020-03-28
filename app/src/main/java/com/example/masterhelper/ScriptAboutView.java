package com.example.masterhelper;

import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.ScriptBottomButtonsFragment.ScriptBottomButtonsFragment;
import com.example.masterhelper.ui.viewCharacteristicRow.ViewCharacteristicRow;

public class ScriptAboutView extends AppCompatActivity implements ScriptBottomButtonsFragment.IScriptBottomButtonsFragment, ViewCharacteristicRow.IViewCharacteristicRow {
  /** */
  int activityScriptAboutViewLayout = R.layout.activity_script_about_view;

  int healthFragmentId = R.id.HEALTH_FRAGMENT_ID;
  ViewCharacteristicRow healthFragment;
  int healthTitleString = R.string.enemy_health_title;

  int damageFragmentId = R.id.DAMAGE_FRAGMENT_ID;
  ViewCharacteristicRow damageFragment;
  int damageTitleString = R.string.enemy_damage_title;

  int orderingFragmentId = R.id.ORDERING_FRAGMENT_ID;
  ViewCharacteristicRow orderingFragment;
  int orderingTitleString = R.string.enemy_ordering_title;

  int enemyDescriptionId = R.id.ENEMY_DESCRIPTION_ID;
  TextView enemyDescription;

  int enemyNameId = R.id.ENEMY_NAME_ID;
  TextView enemyName;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScriptAboutViewLayout);
    setListData();
  }

  void setListData(){
    FragmentManager fm = getSupportFragmentManager();
    healthFragment = (ViewCharacteristicRow) fm.findFragmentById(healthFragmentId);
    damageFragment = (ViewCharacteristicRow) fm.findFragmentById(damageFragmentId);
    orderingFragment = (ViewCharacteristicRow) fm.findFragmentById(orderingFragmentId);

    enemyDescription = findViewById(enemyDescriptionId);
    enemyDescription.setText("slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg ");

    enemyName = findViewById(enemyNameId);
    enemyName.setText("Имя врага, сгенерированное или прописанное");

    if(healthFragment != null && healthFragment.getView() != null){
      healthFragment.setTitle(healthTitleString);
      healthFragment.setValue(0);
    }

    if(damageFragment != null && damageFragment.getView() != null){
      damageFragment.setTitle(damageTitleString);
      damageFragment.setValue(0);
    }

    if(orderingFragment != null && orderingFragment.getView() != null){
      orderingFragment.setTitle(orderingTitleString);
      orderingFragment.setValue(0);
    }
  }

  @Override
  public void setScriptBottomButtonsAction(ScriptBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {
    Log.i("TAG", "onFragmentInteraction: " + scriptBottomButtonsAction);
  }

  @Override
  public void setViewCharacteristicRowActions(int title, int value) {

  }
}

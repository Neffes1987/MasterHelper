package com.example.masterhelper;

import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.ScriptBottomButtonsFragment.ScriptBottomButtonsFragment;
import com.example.masterhelper.ui.viewCharacteristicRow.ViewCharacteristicRow;

import java.util.Objects;

public class ScriptAboutView extends AppCompatActivity implements ScriptBottomButtonsFragment.IScriptBottomButtonsFragment, ViewCharacteristicRow.IViewCharacteristicRow {
  /** */
  int activityScriptAboutViewLayout = R.layout.activity_script_about_view;


  String description = "slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjslfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg slfdkg;skfdjg'aj aslkfjg;slkdjf s;fkjg ;lsdkfjglk sjdfg ";
  String title = "Имя врага, сгенерированное или прописанное";

  private void updateToolbarTitle(String title){
    // получаем указатель на тулбар активированного в главном компоненте
    Objects.requireNonNull(getSupportActionBar()).setTitle(title);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScriptAboutViewLayout);
    updateToolbarTitle(title);
    FragmentManager fm = getSupportFragmentManager();

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

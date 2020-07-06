package com.example.masterhelper.ui.app.settings;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.DialogPopup;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.models.AbilityModel;
import com.example.masterhelper.ui.recyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.ui.viewCharacteristicRow.AbilityDBAdapter;

import java.util.LinkedHashMap;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class AbilityNamesList extends AppCompatActivity implements ICommonItemEvents {
  FragmentManager fragmentManager;

  /**  */
  EditText addNewNameText;

  /**  */
  ImageButton addNewNameBtn;

  AbilityDBAdapter abilityDBAdapter;

  /** Характеристики врага */
  private LinkedHashMap<Integer, AbilityModel> abilities = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ability_names_list);
    fragmentManager = getSupportFragmentManager();
    abilityDBAdapter = new AbilityDBAdapter();
    addNewNameText = findViewById(R.id.ABILITY_EDIT_NAME_ID);
    addNewNameBtn = findViewById(R.id.ABILITY_EDIT_ADD_BTN_ID);
    addNewNameBtn.setOnClickListener(v -> addNewRow());

    updateAbilitiesList();
  }


  /** обновить вьюху по списку сцен */
  void updateAbilitiesList(){
    abilities = abilityDBAdapter.getSettingsAbilitiesList();
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(R.id.ABILITY_LIST_ID);

    if(lsf != null && lsf.getView() != null){
      lsf.updateAbilitiesListAdapter(abilities);
    }
  }


  public void deleteRow(int rowId) {
    abilityDBAdapter.removeAbility(rowId);
    updateAbilitiesList();
  }

  public void addNewRow() {
    String title = addNewNameText.getText().toString().trim();
    if(title.length() == 0){
      Toast.makeText(this, R.string.add_enemy_warning_title_placeholder, Toast.LENGTH_LONG).show();
      return;
    }
    abilityDBAdapter.addAbility(title);
    updateAbilitiesList();
    addNewNameText.setText("");
  }

  @Override
  public void onClick(View elementFiredAction, int position) {
    AbilityModel row = (AbilityModel) abilities.values().toArray()[position];
    if(row != null && elementFiredAction.getId() == R.id.JOURNEY_EDIT_ID ){
      DialogPopup dialogPopup = new DialogPopup(getSupportFragmentManager());
      dialogPopup.setClickListener((dialogInterface, id) -> {
        if(id == BUTTON_POSITIVE){
          deleteRow(row.getId());
        }
      });
      dialogPopup.show();
    }
  }
}

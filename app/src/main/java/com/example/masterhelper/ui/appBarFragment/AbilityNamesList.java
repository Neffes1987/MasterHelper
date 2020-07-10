package com.example.masterhelper.ui.appBarFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.R;
import com.masterhelper.dbAdaptersFactory.AdaptersType;
import com.masterhelper.dbAdaptersFactory.DBAdapterFactory;
import com.masterhelper.dialogsFactory.DialogTypes;
import com.masterhelper.dialogsFactory.DialogsFactory;
import com.masterhelper.dialogsFactory.dialogs.CommonDialog;
import com.masterhelper.listFactory.CustomListItemsEnum;
import com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.masterhelper.appconfig.models.AbilityModel;
import com.masterhelper.listFactory.ListFactory;
import com.masterhelper.dbAdaptersFactory.adapters.AbilityDBAdapter;

import java.util.LinkedHashMap;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class AbilityNamesList extends AppCompatActivity implements ICommonItemEvents {
  FragmentManager fragmentManager;

  /**  */
  EditText addNewNameText;

  /**  */
  ImageButton addNewNameBtn;

  AbilityDBAdapter abilityDBAdapter = (AbilityDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.ability);

  /** Характеристики врага */
  private LinkedHashMap<Integer, AbilityModel> abilities = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ability_names_list);
    fragmentManager = getSupportFragmentManager();
    addNewNameText = findViewById(R.id.ABILITY_EDIT_NAME_ID);
    addNewNameBtn = findViewById(R.id.ABILITY_EDIT_ADD_BTN_ID);
    addNewNameBtn.setOnClickListener(v -> addNewRow());

    updateAbilitiesList();
  }


  /** обновить вьюху по списку сцен */
  void updateAbilitiesList(){
    abilities = abilityDBAdapter.getSettingsAbilitiesList();
    FragmentManager fm = getSupportFragmentManager();
    Fragment lsf =  fm.findFragmentById(R.id.ABILITY_LIST_ID);

    if(lsf != null && lsf.getView() != null){
      ListFactory<AbilityModel> list = (ListFactory<AbilityModel>) lsf;
      list.setItemType(CustomListItemsEnum.abilities);
      list.updateListAdapter(abilities);
    }
  }


  public void deleteRow(int rowId) {
    abilityDBAdapter.delete(rowId);
    updateAbilitiesList();
  }

  public void addNewRow() {
    String title = addNewNameText.getText().toString().trim();
    if(title.length() == 0){
      Toast.makeText(this, R.string.add_enemy_warning_title_placeholder, Toast.LENGTH_LONG).show();
      return;
    }
    abilityDBAdapter.add(new AbilityModel(title), 0);
    updateAbilitiesList();
    addNewNameText.setText("");
  }

  @Override
  public void onClick(View elementFiredAction, int position) {
    AbilityModel row = (AbilityModel) abilities.values().toArray()[position];
    if(row != null && elementFiredAction.getId() == R.id.JOURNEY_EDIT_ID ){
      CommonDialog dialog = DialogsFactory.createDialog(DialogTypes.delete);
      if(dialog != null){
        dialog.setOnResolveListener((dialogInterface, id) -> {
          if(id == BUTTON_POSITIVE){
            deleteRow(row.getId());
          }
        });
        dialog.show(this);
      }
    }
  }
}

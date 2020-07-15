package com.example.com.masterhelper.settings.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;
import com.example.com.masterhelper.core.factorys.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factorys.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.factorys.list.CustomListItemsEnum;
import com.example.com.masterhelper.core.factorys.list.ListFactory;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.settings.SettingsFactory;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;
import com.example.masterhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.example.com.masterhelper.core.factorys.dialogs.DialogTypes.oneFieldDialog;

public class SettingList extends AppCompatActivity implements ICommonItemEvents {
  FragmentManager fragmentManager;

  public static final String EXTRA_TYPE = "settingType";
  public static final String EXTRA_SELECTED_IDS = "selectedIds";
  public static final String EXTRA_SETTING_TITLE = "caption";

  private CustomListItemsEnum listType;

  FloatingActionButton addNewNameBtn;

  AbstractSetting settingsAdapter;
  String[] selectedIds = {};

  /** Характеристики врага */
  private ModelList settings = new ModelList();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings_list);

    String type = getIntent().getStringExtra(EXTRA_TYPE);

    int caption = getIntent().getIntExtra(EXTRA_SETTING_TITLE, R.string.screen_settings_default_caption);
    ActionBar bar = getSupportActionBar();
    if(bar != null){
      bar.setTitle(caption);
    }

    if(type == null){
      try {
        throw new Exception("не указан тип списка настроек");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      SettingsFactory factory = new SettingsFactory(type);
      settingsAdapter  = factory.getAdapter();
      listType = factory.getConvertListItemType();
      selectedIds = getIntent().getStringArrayExtra(EXTRA_SELECTED_IDS);
    }

    fragmentManager = getSupportFragmentManager();

    addNewNameBtn = findViewById(R.id.SETTING_EDIT_ADD_BTN_ID);
    addNewNameBtn.setOnClickListener(v -> onAddButtonPressed(0));
    updateList();
  }

  /** обновить вьюху по списку сцен */
  void updateList(){
    settings = settingsAdapter.list();
    settings.setSelectedItems(selectedIds);
    FragmentManager fm = getSupportFragmentManager();
    Fragment lsf =  fm.findFragmentById(R.id.EXISTED_SETTINGS_LIST_ID);

    if(lsf != null && lsf.getView() != null){
      ListFactory list = (ListFactory) lsf;
      list.updateListAdapter(settings, listType);
    }
  }


  public void deleteRow(int rowId) {
    settingsAdapter.delete(rowId);
    updateList();
  }

  @Override
  public void onClick(View elementFiredAction, int position) {
    DataModel row = settings.getItemByPosition(position);
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


  /** вызвать диалог редактирования нового путешествия */
  public void onAddButtonPressed(int id) {
    CommonDialog dialog = DialogsFactory.createDialog(oneFieldDialog);
    if(dialog != null){
      dialog.show(this, null);
    }
  }

  /** обработчик результатов диалогов создания и редактирования */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);
    if(resultCode != RESULT_OK){
      return;
    }
    String newName = result.getStringExtra("name");
    String newDescription = result.getStringExtra("description");
    int id = result.getIntExtra("id", -1);
    if(newName != null && newName.trim().length() == 0){
      return;
    }
    settingsAdapter.create(newName, newDescription);
    updateList();
  }

}

package com.example.com.masterhelper.settings.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.factories.dialogs.ui.CreateNewItemDialog;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.factories.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factories.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factories.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.factories.list.CustomListItemsEnum;
import com.example.com.masterhelper.core.factories.list.ListFactory;
import com.example.com.masterhelper.core.factories.list.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.settings.SettingsFactory;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;
import com.example.masterhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class SettingList extends AppCompatActivity implements ICommonItemEvents {
  FragmentManager fragmentManager;

  public static final String EXTRA_TYPE = "settingType";
  public static final String EXTRA_SELECTED_IDS = "selectedIds";
  public static final String EXTRA_SETTING_TITLE = "caption";

  private CustomListItemsEnum listType;
  SettingsFactory factory;

  int editItemPosition;

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
      factory = new SettingsFactory(type);
      settingsAdapter  = factory.getAdapter();
      listType = factory.getConvertListItemType();
      selectedIds = getIntent().getStringArrayExtra(EXTRA_SELECTED_IDS);
    }

    fragmentManager = getSupportFragmentManager();

    addNewNameBtn = findViewById(R.id.SETTING_EDIT_ADD_BTN_ID);
    addNewNameBtn.setOnClickListener(v -> onAddButtonPressed());
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
    if(row != null && elementFiredAction.getId() == R.id.ITEM_DELETE_BUTTON){
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
    if(row != null && elementFiredAction.getId() == R.id.ITEM_EDIT_ID){
      CommonDialog dialog = factory.getDialog();
      if(dialog != null){
        editItemPosition = position;
        dialog.show(this, row);
      }
    }
  }


  /** вызвать диалог редактирования нового путешествия */
  public void onAddButtonPressed() {
    CommonDialog dialog = factory.getDialog();
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
    String newName = result.getStringExtra(CreateNewItemDialog.NAME);
    int id = result.getIntExtra(CreateNewItemDialog.ID, 0);
    String newDescription = result.getStringExtra(CreateNewItemDialog.DESCRIPTION);
    ArrayList<String> newSelectedItems = result.getStringArrayListExtra(CreateNewItemDialog.SELECTED_ITEMS);
    if(newName != null && newName.trim().length() == 0){
      return;
    }
    switch (requestCode){
      case CommonDialog.DIALOG_CREATE_ACTIVITY_RESULT:
        settingsAdapter.create(newName, newDescription);
        break;
      case CommonDialog.DIALOG_UPDATE_ACTIVITY_RESULT:
        settingsAdapter.update(id, newName, newDescription, null);
        break;
      case CommonDialog.DIALOG_CREATE_SETTING_ACTIVITY_RESULT:
        settingsAdapter.create(newName, newDescription, newSelectedItems.toArray(new String[0]));
        break;
      case CommonDialog.DIALOG_UPDATE_SETTING_ACTIVITY_RESULT:
        editItemPosition = 0;
        settingsAdapter.update(id, newName, newDescription, newSelectedItems.toArray(new String[0]));
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + resultCode);
    }
    updateList();
  }

}

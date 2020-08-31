package com.example.com.masterhelper.settings.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.factories.dialogs.ui.CreateNewItemDialog;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.factories.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factories.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factories.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.listFactory.ListFactory;
import com.example.com.masterhelper.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.settings.SettingsFactory;
import com.example.com.masterhelper.settings.SettingsType;
import com.example.com.masterhelper.settings.adapters.SettingsListDBAdapter;
import com.example.com.masterhelper.settings.models.SettingModel;
import com.example.masterhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class SettingList extends AppCompatActivity implements ICommonItemEvents {
  FragmentManager fragmentManager;

  public static final String EXTRA_TYPE = "settingType";
  public static final String EXTRA_RECORD_TYPE = "settingRecordType";
  public static final String EXTRA_SELECTED_IDS = "selectedIds";
  public static final String EXTRA_SELECTED_LIST_ITEMS_IDS = "selectedListItemsIds";
  public static final String EXTRA_SETTING_TITLE = "caption";
  public static final String EXTRA_DIALOG_TITLE = "dialog_caption";

  private static final ArrayList<String> selectedListItemsIds = new ArrayList<>();

  SettingsFactory factory;

  int editItemId;

  FloatingActionButton addNewNameBtn;
  FloatingActionButton applySelectedItems;

  SettingsListDBAdapter settingsAdapter;
  CommonAdapter viewAdapter;
  String[] selectedIds = {};

  int parentId;
  private String type;
  private String recordType;


  private void initSettingsFactory(){
    int caption = getIntent().getIntExtra(EXTRA_DIALOG_TITLE, 0);
    if(type == null || caption == 0){
      try {
        throw new Exception("не указан тип списка настроек или название диалога");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {

      factory = new SettingsFactory(SettingsType.valueOf(type), caption);
      selectedIds = getIntent().getStringArrayExtra(EXTRA_SELECTED_IDS);
    }
  }

  private void setSelectable(){
    if(SettingsType.valueOf(type) == SettingsType.selectable){
      applySelectedItems = findViewById(R.id.SETTINGS_APPLY_BTN_ID);
      applySelectedItems.show();
      applySelectedItems.setOnClickListener(this::onApplyButtonPressed);
    }
  }

  private void setListTitle(){
    int caption = getIntent().getIntExtra(EXTRA_SETTING_TITLE, R.string.screen_settings_default_caption);
    ActionBar bar = getSupportActionBar();
    if(bar != null){
      bar.setTitle(caption);
    }
  }

  private void initViewAdapter(){
    settingsAdapter = new SettingsListDBAdapter();
    viewAdapter = new CommonAdapter(settingsAdapter.list(recordType), R.layout.fragment_view_list_item_row, this);
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings_list);
    type = getIntent().getStringExtra(EXTRA_TYPE);
    recordType = getIntent().getStringExtra(EXTRA_RECORD_TYPE);

    initSettingsFactory();
    setSelectable();
    setListTitle();
    initViewAdapter();

    addNewNameBtn = findViewById(R.id.SETTING_EDIT_ADD_BTN_ID);
    addNewNameBtn.setOnClickListener(v -> onAddButtonPressed());
    setList();
  }

  /** обновить вьюху по списку сцен */
  void setList(){
    fragmentManager = getSupportFragmentManager();
    viewAdapter.setSelectedItems(selectedIds);
    FragmentManager fm = getSupportFragmentManager();
    Fragment lsf =  fm.findFragmentById(R.id.EXISTED_SETTINGS_LIST_ID);

    if(lsf != null && lsf.getView() != null){
      ListFactory list = (ListFactory) lsf;
      list.setAdapter(viewAdapter);
    }
  }

  public void deleteRow(int rowId) {
    settingsAdapter.delete(rowId);
    viewAdapter.deleteItem(rowId);
  }

  @Override
  public void onClick(View elementFiredAction, int itemId) {
    DataModel row = viewAdapter.getItemById(itemId);
    if(elementFiredAction.getId() == R.id.ITEM_SELECTOR_ID){
      String id = itemId+"";
      if(selectedListItemsIds.contains(id)){
        selectedListItemsIds.remove(id);
      } else {
        selectedListItemsIds.add(id);
      }
    }
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
        editItemId = itemId;
        dialog.show(this, row);
      }
    }
  }


  /**   */
  public void onAddButtonPressed() {
    CommonDialog dialog = factory.getDialog();
    if(dialog != null){
      dialog.show(this, null);
    }
  }

  /**   */
  public void onApplyButtonPressed(View v) {
    Intent intent = new Intent();
    intent.putStringArrayListExtra(EXTRA_SELECTED_LIST_ITEMS_IDS, selectedListItemsIds);
    setResult(RESULT_OK, intent);
    finish();
  }

  /** обработчик результатов диалогов создания и редактирования */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    super.onActivityResult(requestCode, resultCode, result);
    if(resultCode != RESULT_OK){
      return;
    }

    if(result == null){
      setList();
      return;
    }

    String newName = result.getStringExtra(CreateNewItemDialog.NAME);
    int id = result.getIntExtra(CreateNewItemDialog.ID, 0);

    String newDescription = result.getStringExtra(CreateNewItemDialog.DESCRIPTION);
    ArrayList<String> newSelectedItems = result.getStringArrayListExtra(CreateNewItemDialog.SELECTED_ITEMS);
    if(newName != null && newName.trim().length() == 0){
      return;
    }

    SettingModel model = new SettingModel(id, newName, newDescription, recordType);
    switch (requestCode){
      case CommonDialog.DIALOG_CREATE_ACTIVITY_RESULT:
        settingsAdapter.add(model,parentId);
        Toast.makeText(this, newName+" добавлен", Toast.LENGTH_LONG).show();
        break;
      case CommonDialog.DIALOG_UPDATE_ACTIVITY_RESULT:
        settingsAdapter.update(model);
        Toast.makeText(this, newName+" обновлен", Toast.LENGTH_LONG).show();
        break;
      case CommonDialog.DIALOG_CREATE_SETTING_ACTIVITY_RESULT:
        settingsAdapter.create(newName, newDescription, parentId, newSelectedItems.toArray(new String[0]));
        Toast.makeText(this, newName+" добавлен", Toast.LENGTH_LONG).show();
        break;
      case CommonDialog.DIALOG_UPDATE_SETTING_ACTIVITY_RESULT:
        editItemId = -1;
        settingsAdapter.update(id, newName, newDescription, newSelectedItems.toArray(new String[0]));
        Toast.makeText(this, newName+" обновлен", Toast.LENGTH_LONG).show();
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + resultCode);
    }
    setList();
  }

}

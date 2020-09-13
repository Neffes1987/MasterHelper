package com.example.com.masterhelper.settings.ui;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.components.buttons.ComponentFloatButtonPrimary;
import com.example.com.masterhelper.core.components.dialogs.dialogs.DeleteDialog;
import com.example.com.masterhelper.core.components.dialogs.dialogs.InputDialog;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.CommonItem;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.listFactory.ListFactory;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.settings.SettingsType;
import com.example.com.masterhelper.settings.adapters.SettingsListDBAdapter;
import com.example.com.masterhelper.settings.models.SettingModel;
import com.example.masterhelper.R;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class SettingList extends AppCompatActivity implements ICommonItemEvents {
  FragmentManager fragmentManager;

  public static final String EXTRA_RECORD_TYPE = "settingRecordType";
  public static final String EXTRA_SETTING_TITLE = "caption";

  private static final ArrayList<String> selectedListItemsIds = new ArrayList<>();

  ComponentFloatButtonPrimary addNewNameBtn;

  SettingsListDBAdapter settingsAdapter;
  CommonAdapter viewAdapter;
  String[] selectedIds = {};

  private String recordType;

  InputDialog inputDialog;
  DeleteDialog deleteDialog;

  private void setDialogs(){
    inputDialog = new InputDialog(this, getSupportFragmentManager());
    deleteDialog = new DeleteDialog(this);
  }


  private void setListTitle(){
    int caption = getIntent().getIntExtra(EXTRA_SETTING_TITLE, R.string.screen_settings_default_caption);
    ActionBar bar = getSupportActionBar();
    if(bar != null){
      bar.setTitle(caption);
    }
  }

  public CommonItem getCommonItemInstance(CommonAdapter adapter) {
    SettingsItem item = new SettingsItem(SettingsType.global);
    item.attachAdapter(adapter);
    return item;
  }

  private void initViewAdapter(){
    settingsAdapter = new SettingsListDBAdapter();
    viewAdapter = new CommonAdapter(settingsAdapter.list(recordType), R.layout.fragment_view_list_item_row, this);
    viewAdapter.setCommonItemInstanceGetter(this::getCommonItemInstance);
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_screen_settings);
    recordType = getIntent().getStringExtra(EXTRA_RECORD_TYPE);

    setListTitle();
    initViewAdapter();

    View addNewNameBtnWrapper = findViewById(R.id.SETTING_EDIT_ADD_BTN_ID);
    addNewNameBtn = new ComponentFloatButtonPrimary(addNewNameBtnWrapper);
    addNewNameBtn.setListener(v -> onAddButtonPressed());
    setList();
    setDialogs();
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
      deleteDialog.setOnResolveListener((dialogInterface, id) -> {
        if(id == BUTTON_POSITIVE){
          deleteRow(row.getId());
        }
      });
      deleteDialog.show();
    }
    if(row != null && elementFiredAction.getId() == R.id.ITEM_EDIT_ID){
      inputDialog.setOnResolveListener((d, w) -> {
        String name = inputDialog.getName();
        String description = inputDialog.getDescription();
        row.setName(name);
        row.setDescription(description);
        settingsAdapter.update(row);
        viewAdapter.updateItem(row);
      });
      inputDialog.show(row);
    }
  }


  /**   */
  public void onAddButtonPressed() {
    inputDialog.setTitle(R.string.add_setting_dialog_caption);
    inputDialog.setOnResolveListener((d, w) -> {
      String name = inputDialog.getName();
      String description = inputDialog.getDescription();
      SettingModel model = new SettingModel(name, description, recordType);
      settingsAdapter.add(model, 0);
      viewAdapter.addItem(model,true);
    });
    inputDialog.show();
  }
}

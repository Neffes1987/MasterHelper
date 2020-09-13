package com.example.com.masterhelper.journey.ui;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.example.com.masterhelper.core.components.dialogs.dialogs.DeleteDialog;
import com.example.com.masterhelper.core.components.dialogs.dialogs.InputDialog;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.CommonItem;
import com.example.com.masterhelper.settings.SettingsType;
import com.example.com.masterhelper.settings.ui.SettingsItem;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;
import com.example.com.masterhelper.settings.ui.SettingList;
import com.example.masterhelper.R;
import com.example.com.masterhelper.journey.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.journey.models.JourneyModel;
import com.example.com.masterhelper.appbar.IAppBarFragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.listFactory.ListFactory;
import com.example.com.masterhelper.journey.ui.popupMenu.PopupMenuAdapter;
import com.example.com.masterhelper.journey.ui.popupMenu.PopupMenuEvents;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class JourneysListView extends AppCompatActivity implements ICommonItemEvents, IAppBarFragment, PopupMenuEvents {
  /** ид выбранного путешествия */
  int selectedJourneyId;

  /** указатель на кнопку создания новой сценой */
  ImageButton journeyCreateBtn;

  /** указатель на диалог создания нового путешествия */
  PopupMenuAdapter journeysPopup;

  /** указатель на тулбар */
  Toolbar toolbar;

  /** диалог добавления нового приключения */
  InputDialog journeyDialog;
  DeleteDialog deleteDialog;

  /** хелпер для управлением таблицей путешествий в бд */
  AbstractSetting journeyDBAdapter = new JourneyDBAdapter();


  CommonAdapter listAdapter;
  ListFactory lsf;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_screen_view_projects);

    journeyCreateBtn = findViewById(R.id.JOURNEY_CREATE_BTN);
    journeyCreateBtn.setOnClickListener(v -> onCreateJourneyButtonPressed());
    initToolBar();
    updateJourneysList();
    initJourneyDialog();
  }

  private void initJourneyDialog(){
    journeyDialog = new InputDialog(this, getSupportFragmentManager());
    journeyDialog.hideDescription();

    deleteDialog = new DeleteDialog(this);
  }

  public CommonItem getCommonItemInstance(CommonAdapter adapter) {
    SettingsItem item = new SettingsItem(SettingsType.showDescription);
    item.attachAdapter(adapter);
    return item;
  }

  private void updateJourneysList(){
    FragmentManager fm = getSupportFragmentManager();
    listAdapter = new CommonAdapter(journeyDBAdapter.list(), R.layout.fragment_view_list_item_row, this);
    listAdapter.setCommonItemInstanceGetter(this::getCommonItemInstance);
    lsf = (ListFactory) fm.findFragmentById(R.id.JOURNEYS_ID);
    if(lsf != null && lsf.getView() != null){
      lsf.setAdapter(listAdapter);
    }
  }

  private void initToolBar(){
    toolbar = findViewById(R.id.TOOLBAR_ID);
    toolbar.setTitle(R.string.SCREEN_NAME_JOURNEYS_TEXT);
    setSupportActionBar(toolbar);
  }

  /** вызвать диалог создания новго путешествия */
  public void onCreateJourneyButtonPressed() {
    journeyDialog.setTitle(R.string.journey_create_title);
    journeyDialog.setOnResolveListener((dialog, which) -> {
      String newName = journeyDialog.getName();
      JourneyModel item = new JourneyModel(newName, 0);
      int newId = journeyDBAdapter.add(item,0);
      item.setId(newId);
      listAdapter.addItem(item, true);
    });
    journeyDialog.show();
  }

  /** вызвать диалог редактирования нового путешествия */
  public void onUpdateJourneyButtonPressed(int id) {
    journeyDialog.setTitle(R.string.journey_update_title);
    DataModel item = listAdapter.getItemById(id);
    journeyDialog.setOnResolveListener((dialog, which) -> {
      String newName = journeyDialog.getName();
      journeyDBAdapter.update(id, newName, "", null);
      item.setName(newName);
      listAdapter.updateItem(item);
    });
    journeyDialog.show(item);
  }

  /** провалиться в выбранное путешествие */
  public void onSelectJourneyPressed(int id) {
    Intent intent = new Intent(JourneysListView.this, JourneyItemView.class);
    intent.putExtra("id", id);
    startActivity(intent);
  }

  public void addNewGoalToJourney(int id){
    Intent intent = new Intent(this, SettingList.class);
    //intent.putExtra(SettingList.EXTRA_TYPE, SettingsFactory.SettingsFactoryType.goal.name());
    //intent.putExtra(SettingList.EXTRA_PARENT_ID, id);
    intent.putExtra(SettingList.EXTRA_SETTING_TITLE, R.string.force_goal_title);
    startActivity(intent);
  }

  /** обработчик вызова музыкальных настроек */
  @Override
  public void onItemSelected(MenuItem selectedView) {}

  /** показать диалог настроек путешествия */
  void onShowJourneySettingsPopup(View v){
    try {
      journeysPopup = new PopupMenuAdapter(this, v, false);
      journeysPopup.popupMenu.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**  */
  @Override
  public void onClick(View elementFiredAction, int id) {
    DataModel journeyModel = listAdapter.getItemById(id);
    selectedJourneyId = journeyModel.getId();
    switch (elementFiredAction.getId()){
      case R.id.ITEM_TITLE_ID:
      case R.id.ROW_ITEM_CLICKABLE_ID:
        onSelectJourneyPressed(journeyModel.getId());
        break;
      case R.id.ITEM_EDIT_ID:
        onShowJourneySettingsPopup(elementFiredAction);
        break;
    }
  }

  private void deleteJourney(){
    journeyDBAdapter.delete(selectedJourneyId);
    listAdapter.deleteItem(selectedJourneyId);
    selectedJourneyId = -1;
  }

  /** обработчик выбора пункта меню путешествия*/
  @Override
  public void onPopupMenuSelected(MenuItem selectedMenuItem) {
    switch (selectedMenuItem.getItemId()){
      case R.id.POPUP_MENU_DELETE_ID:
        deleteDialog.setOnResolveListener((dialogInterface, id) -> {
          if(id == BUTTON_POSITIVE){
            deleteJourney();
          }
        });
        deleteDialog.show();
        break;
      case R.id.POPUP_MENU_UPDATE_ID:
        onUpdateJourneyButtonPressed(selectedJourneyId);
        break;
      case R.id.POPUP_ADD_GOAL_ID:
        addNewGoalToJourney(selectedJourneyId);
        break;
    }
  }
}

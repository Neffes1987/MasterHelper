package com.example.com.masterhelper.journey.ui;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.settings.SettingsFactory;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;
import com.example.com.masterhelper.settings.ui.SettingList;
import com.example.masterhelper.R;
import com.example.com.masterhelper.journey.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.core.factories.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factories.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factories.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.listFactory.CustomListItemsEnum;
import com.example.com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.journey.models.JourneyModel;
import com.example.com.masterhelper.appbar.IAppBarFragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.listFactory.ListFactory;
import com.example.com.masterhelper.journey.ui.popupMenu.PopupMenuAdapter;
import com.example.com.masterhelper.journey.ui.popupMenu.PopupMenuEvents;


import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.example.com.masterhelper.core.factories.dialogs.DialogTypes.oneFieldDialog;

public class JourneysListView extends AppCompatActivity implements ICommonItemEvents, IAppBarFragment, PopupMenuEvents {
  /** ид выбранного путешествия */
  int selectedJourneyId;

  /** указатель на кнопку создания новой сценой */
  ImageButton journeyCreateBtn;

  /** указатель на диалог создания нового путешествия */
  PopupMenuAdapter journeysPopup;

  /** указатель на тулбар */
  Toolbar toolbar;

  /** хелпер для управлением таблицей путешествий в бд */
  AbstractSetting journeyDBAdapter = new JourneyDBAdapter();

  /** временный кеш списка путешествий */
  ModelList journeys = new ModelList();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_projects);

    journeyCreateBtn = findViewById(R.id.JOURNEY_CREATE_BTN);
    journeyCreateBtn.setOnClickListener(v -> onCreateJourneyButtonPressed());
    initToolBar();
    updateJourneysList();
  }

  private void initToolBar(){
    toolbar = findViewById(R.id.TOOLBAR_ID);
    toolbar.setTitle(R.string.SCREEN_NAME_JOURNEYS_TEXT);
    setSupportActionBar(toolbar);
  }

  /** вызвать диалог создания новго путешествия */
  public void onCreateJourneyButtonPressed() {
    CommonDialog dialog = DialogsFactory.createDialog(oneFieldDialog);
    if(dialog != null){
      dialog.setTitle(R.string.journey_create_title);
      dialog.show(this, null);
    }
  }

  /** вызвать диалог редактирования нового путешествия */
  public void onUpdateJourneyButtonPressed(int id) {
    CommonDialog dialog = DialogsFactory.createDialog(oneFieldDialog);
    if(dialog != null){
      dialog.setTitle(R.string.journey_update_title);
      dialog.show(this, journeys.get(id));
    }
  }

  /** провалиться в выбранное путешествие */
  public void onSelectJourneyPressed(int id) {
    Intent intent = new Intent(JourneysListView.this, JourneyItemView.class);
    intent.putExtra("id", id);
    startActivity(intent);
  }

  public void addNewGoalToJourney(int id){
    Intent intent = new Intent(this, SettingList.class);
    intent.putExtra(SettingList.EXTRA_TYPE, SettingsFactory.SettingsFactoryType.goal.name());
    intent.putExtra(SettingList.EXTRA_PARENT_ID, id);
    intent.putExtra(SettingList.EXTRA_SETTING_TITLE, R.string.force_goal_motivation_title);
    startActivity(intent);
  }

  /** обновить вьюху списка путешествий */
  void updateJourneysList(){
    journeys = journeyDBAdapter.list();
    FragmentManager fm = getSupportFragmentManager();
    ListFactory lsf = (ListFactory) fm.findFragmentById(R.id.JOURNEYS_ID);
    if(lsf != null && lsf.getView() != null){
      lsf.updateListAdapter(journeys, CustomListItemsEnum.journey);
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
    int id = result.getIntExtra("id", -1);
    if(newName != null && newName.trim().length() == 0){
      return;
    }
    JourneyModel item = new JourneyModel(newName, id);
    switch (requestCode){
      case CommonDialog.DIALOG_CREATE_ACTIVITY_RESULT:
        journeyDBAdapter.add(item,0);
        break;
      case CommonDialog.DIALOG_UPDATE_ACTIVITY_RESULT:
        journeyDBAdapter.update(id, newName, "", null);
        break;
    }
    updateJourneysList();
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
  public void onClick(View elementFiredAction, int position) {
    DataModel journeyModel = journeys.getItemByPosition(position);
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
    selectedJourneyId = -1;
    updateJourneysList();
  }

  /** обработчик выбора пункта меню путешествия*/
  @Override
  public void onPopupMenuSelected(MenuItem selectedMenuItem) {
    switch (selectedMenuItem.getItemId()){
      case R.id.POPUP_MENU_DELETE_ID:
        CommonDialog dialog = DialogsFactory.createDialog(DialogTypes.delete);
        if(dialog != null){
          dialog.setOnResolveListener((dialogInterface, id) -> {
            if(id == BUTTON_POSITIVE){
              deleteJourney();
            }
          });
          dialog.show(this);
        }
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

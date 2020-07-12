package com.example.com.masterhelper.ui.journey;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CreateNewItemDialog;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.factorys.DBAdapters.AdaptersType;
import com.example.com.masterhelper.core.factorys.DBAdapters.DBAdapterFactory;
import com.example.com.masterhelper.core.factorys.DBAdapters.adapters.JourneyDBAdapter;
import com.example.com.masterhelper.core.factorys.dialogs.DialogTypes;
import com.example.com.masterhelper.core.factorys.dialogs.DialogsFactory;
import com.example.com.masterhelper.core.factorys.dialogs.dialogs.CommonDialog;
import com.example.com.masterhelper.core.factorys.list.CustomListItemsEnum;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.item.ICommonItemEvents;
import com.example.com.masterhelper.core.appconfig.models.JourneyModel;
import com.example.com.masterhelper.ui.appBarFragment.IAppBarFragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.com.masterhelper.core.factorys.list.ListFactory;
import com.example.com.masterhelper.ui.popupMenu.PopupMenuAdapter;
import com.example.com.masterhelper.ui.popupMenu.PopupMenuEvents;

import java.util.LinkedHashMap;
import java.util.Objects;

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

  /** хелпер для управлением таблицей путешествий в бд */
  JourneyDBAdapter journeyDBAdapter = (JourneyDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.journey);

  /** временный кеш списка путешествий */
  LinkedHashMap<Integer, JourneyModel> journeys = new LinkedHashMap<>();

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
    Intent intent = new Intent(JourneysListView.this, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.TITLE, R.string.journey_create_title);
    intent.putExtra(CreateNewItemDialog.HIDE_DESCRIPTION, 1);
    startActivityForResult(intent, 1);
  }

  /** вызвать диалог редактирования нового путешествия */
  public void onUpdateJourneyButtonPressed(int id) {
    Intent intent = new Intent(JourneysListView.this, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.HIDE_DESCRIPTION, 1);
    intent.putExtra(CreateNewItemDialog.TITLE, R.string.journey_update_title);
    intent.putExtra(CreateNewItemDialog.IS_UPDATE, 1);
    intent.putExtra(CreateNewItemDialog.ID, id);
    intent.putExtra(CreateNewItemDialog.OLD_NAME, Objects.requireNonNull(journeys.get(id)).getTitle());
    startActivityForResult(intent, 2);
  }

  /** провалиться в выбранное путешествие */
  public void onSelectJourneyPressed(int id) {
    Intent intent = new Intent(JourneysListView.this, JourneyItemView.class);
    intent.putExtra("id", id);
    startActivity(intent);
  }

  /** обновить вьюху списка путешествий */
  void updateJourneysList(){
    journeys = journeyDBAdapter.getListByParentId(0);
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
      case 1:
        journeyDBAdapter.add(item,0);
        break;
      case 2:
        journeyDBAdapter.update(item);
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
    JourneyModel journeyModel = (JourneyModel) journeys.values().toArray()[position];
    selectedJourneyId = journeyModel.getId();
    switch (elementFiredAction.getId()){
      case R.id.JOURNEY_TITLE_ID:
        onSelectJourneyPressed(journeyModel.getId());
        break;
      case R.id.JOURNEY_EDIT_ID:
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
    }
  }
}

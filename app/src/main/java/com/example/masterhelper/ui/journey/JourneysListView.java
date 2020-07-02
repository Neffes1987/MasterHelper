package com.example.masterhelper.ui.journey;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.masterhelper.CreateNewItemDialog;
import com.example.masterhelper.DialogPopup;
import com.example.masterhelper.ui.app.settings.AbilityNamesList;
import com.example.masterhelper.ui.app.settings.MusicSettingsScreen;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.models.JourneyModel;
import com.example.masterhelper.ui.appBarFragment.IAppBarFragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.recyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.ui.popupMenu.PopupMenuAdapter;
import com.example.masterhelper.ui.popupMenu.PopupMenuEvents;

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
  JourneyDBAdapter journeyDBAdapter;

  /** временный кеш списка путешествий */
  LinkedHashMap<Integer, JourneyModel> journeys = new LinkedHashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_projects);
    toolbar = findViewById(R.id.TOOLBAR_ID);
    toolbar.setTitle(R.string.SCREEN_NAME_JOURNEYS_TEXT);
    journeyCreateBtn = findViewById(R.id.JOURNEY_CREATE_BTN);
    journeyCreateBtn.setOnClickListener(v -> onCreateJourneyButtonPressed());

    journeyDBAdapter  = new JourneyDBAdapter(this);

    setSupportActionBar(toolbar);
    updateJourneysList();
  }

  /** вызвать диалог создания новго путешествия */
  public void onCreateJourneyButtonPressed() {
    Intent intent = new Intent(JourneysListView.this, CreateNewItemDialog.class);
    intent.putExtra("title", R.string.journey_create_title);
    startActivityForResult(intent, 1);
  }

  /** вызвать диалог редактирования нового путешествия */
  public void onUpdateJourneyButtonPressed(int id) {
    Intent intent = new Intent(JourneysListView.this, CreateNewItemDialog.class);
    intent.putExtra("title", R.string.journey_update_title);
    intent.putExtra("id", id);
    intent.putExtra("oldName", Objects.requireNonNull(journeys.get(id)).getTitle());
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
    journeys = journeyDBAdapter.getJourneysList();
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(R.id.JOURNEYS_ID);
    if(lsf != null && lsf.getView() != null){
      lsf.updateJourneyListAdapter(journeys);
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
    switch (requestCode){
      case 1:
        journeyDBAdapter.addJourney(newName);
        break;
      case 2:
        journeyDBAdapter.updateJourney(id, newName);
        break;
    }
    updateJourneysList();
  }

  /** обработчик вызова музыкальных настроек */
  @Override
  public void onItemSelected(MenuItem selectedView) {
    Intent intent;
    switch (selectedView.getItemId()){
      case R.id.ACTION_SETTINGS:
        intent = new Intent(JourneysListView.this, MusicSettingsScreen.class);
        startActivity(intent);
        break;
      case R.id.ABILITIES_SETTINGS:
        intent = new Intent(JourneysListView.this, AbilityNamesList.class);
        startActivity(intent);
        break;
    }
  }

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
    journeyDBAdapter.deleteJourney(selectedJourneyId);
    selectedJourneyId = -1;
    updateJourneysList();
  }

  /** обработчик выбора пункта меню путешествия*/
  @Override
  public void onPopupMenuSelected(MenuItem selectedMenuItem) {
    switch (selectedMenuItem.getItemId()){
      case R.id.POPUP_MENU_DELETE_ID:
        DialogPopup dialogPopup = new DialogPopup(getSupportFragmentManager());
        dialogPopup.setClickListener((dialogInterface, id) -> {
          if(id == BUTTON_POSITIVE){
            deleteJourney();
          }
        });
        dialogPopup.show();
        break;
      case R.id.POPUP_MENU_UPDATE_ID:
        onUpdateJourneyButtonPressed(selectedJourneyId);
        break;
    }
  }
}

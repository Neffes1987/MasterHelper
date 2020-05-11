package com.example.masterhelper;

import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.JourneysContract;
import com.example.masterhelper.models.JourneyModel;
import com.example.masterhelper.ui.AppBarFragment.IAppBarFragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerViewFragment;
import com.example.masterhelper.ui.popupMenu.PopupMenuAdapter;
import com.example.masterhelper.ui.popupMenu.PopupMenuEvents;

import java.util.LinkedHashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ICommonItemEvents, IAppBarFragment, PopupMenuEvents {
  /** */
  int journeysScreenId = R.id.JOURNEYS_ID;

  /** */
  int selectedJourneyId;

  /** */
  int journeyCreateBtnId = R.id.JOURNEY_CREATE_BTN;
  ImageButton journeyCreateBtn;

  /** */
  PopupMenuAdapter journeysPopup;

  /** */
  int activityScreenViewProjectsLayout = R.layout.activity_screen_view_projects;

  /** */
  int toolbarId = R.id.TOOLBAR_ID;
  Toolbar toolbar;

  /** */
  int ScreenTitleStringString = R.string.SCREEN_NAME_JOURNEYS_TEXT;

  LinkedHashMap<Integer, JourneyModel> data = new LinkedHashMap<>();

  private DbHelpers dbHelpers;

  private LinkedHashMap<Integer, JourneyModel> getJourneysList(){
    String sqlQuery = JourneysContract.getListQuery(JourneysContract.TABLE_NAME, null, null, JourneysContract._ID + " DESC", 0);
    LinkedHashMap<Integer, JourneyModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
      int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
      JourneyModel journeyModel = new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
      result.put(journeyModel.getId(),journeyModel);
    }
    queryResult.close();
    data = result;
    return result;
  }

  private void addJourney(String newItemName){

    String sqlQuery = JourneysContract.addItemQuery(new JourneyModel(newItemName));
    dbHelpers.addNewItem(sqlQuery);
    setListData();
  }

  private void deleteJourney(int journeyId){
    String sqlQuery = JourneysContract.deleteItemQuery(journeyId);
    dbHelpers.deleteItem(sqlQuery);
    setListData();
  }

  private void updateJourney(int journeyId, String newTitle){
    String sqlQuery = JourneysContract.updateItemQuery(journeyId, new JourneyModel(newTitle));
    dbHelpers.updateItem(sqlQuery);
    setListData();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScreenViewProjectsLayout);
    toolbar = findViewById(toolbarId);
    toolbar.setTitle(ScreenTitleStringString);
    journeyCreateBtn = findViewById(journeyCreateBtnId);
    journeyCreateBtn.setOnClickListener(OnCreateNewItem);

    setSupportActionBar(toolbar);
    dbHelpers  = new DbHelpers(this);
    setListData();
  }

  public void onCreateJourneyButtonPressed() {
    Intent intent = new Intent(MainActivity.this, CreateNewItem.class);
    intent.putExtra("title", R.string.journey_create_title);
    startActivityForResult(intent, 1);
  }

  public void onUpdateJourneyButtonPressed(int id) {
    Intent intent = new Intent(MainActivity.this, CreateNewItem.class);
    intent.putExtra("title", R.string.journey_update_title);
    intent.putExtra("id", id);
    intent.putExtra("oldName", Objects.requireNonNull(data.get(id)).getTitle());
    startActivityForResult(intent, 2);
  }


  public void onItemButtonPressed(int id) {
    Intent intent = new Intent(MainActivity.this, ProjectScreen.class);
    intent.putExtra("id", id);
    startActivity(intent);
  }

  void setListData(){
    FragmentManager fm = getSupportFragmentManager();
    RecyclerViewFragment lsf = (RecyclerViewFragment) fm.findFragmentById(journeysScreenId);
    if(lsf != null && lsf.getView() != null){
      lsf.updateJourneyListAdapter(getJourneysList());
    }
  }

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
        addJourney(newName);
        break;
      case 2:
        updateJourney(id, newName);
        break;
    }
  }

  @Override
  public void onItemSelected(MenuItem selectedView) {
    Intent intent = new Intent(MainActivity.this, MusicSettingsScreen.class);
    startActivity(intent);
  }

  void onStartItemPopup(View v){
    try {
      journeysPopup = new PopupMenuAdapter(this, v, false);
      journeysPopup.popupMenu.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Override
  public void onClick(View elementFiredAction, int position) {
    JourneyModel journeyModel = (JourneyModel) data.values().toArray()[position];
    selectedJourneyId = journeyModel.getId();
    switch (elementFiredAction.getId()){
      case R.id.JOURNEY_TITLE_ID:
        onItemButtonPressed(journeyModel.getId());
        break;
      case R.id.JOURNEY_EDIT_ID:
        onStartItemPopup(elementFiredAction);
        break;
    }
  }

  View.OnClickListener OnCreateNewItem = v -> onCreateJourneyButtonPressed();

  @Override
  public void onPopupMenuSelected(MenuItem selectedMenuItem) {
    switch (selectedMenuItem.getItemId()){
      case R.id.POPUP_MENU_DELETE_ID:
        deleteJourney(selectedJourneyId);
        break;
      case R.id.POPUP_MENU_UPDATE_ID:
        onUpdateJourneyButtonPressed(selectedJourneyId);
        break;
    }
    selectedJourneyId = -1;
  }
}

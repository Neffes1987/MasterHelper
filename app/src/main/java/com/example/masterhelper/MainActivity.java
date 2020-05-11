package com.example.masterhelper;

import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.Journeys;
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

public class MainActivity extends AppCompatActivity implements ICommonItemEvents, IAppBarFragment, PopupMenuEvents {
  /** */
  int journeysScreenId = R.id.JOURNEYS_ID;

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
    String sqlQuery = Journeys.getListQuery(Journeys.TABLE_NAME, null, null, Journeys._ID + " DESC", 0);
    LinkedHashMap<Integer, JourneyModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(Journeys.COLUMN_TITLE);
      int idColumnIndex = queryResult.getColumnIndex(Journeys._ID);
      JourneyModel journeyModel = new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
      result.put(journeyModel.getId(),journeyModel);
    }
    queryResult.close();
    data = result;
    return result;
  }

  private void addJourney(String newItemName){
    String sqlQuery = Journeys.addItemQuery(newItemName);
    dbHelpers.addNewItem(sqlQuery);
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

  public void onCreateButtonPressed() {
    Intent intent = new Intent(MainActivity.this, CreateNewItem.class);
    intent.putExtra("title", R.string.journey_create_title);
    startActivityForResult(intent, 1);
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

    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String newName = result.getStringExtra("name");
        if(newName != null && newName.trim().length() == 0){
          return;
        }
        addJourney(newName);
        setListData();
      }
    }
  }

  @Override
  public void onItemSelected(MenuItem selectedView) {
    Intent intent = new Intent(MainActivity.this, MusicSettingsScreen.class);
    startActivity(intent);
  }

  void onStartItemPopup(View v){
    try {
      journeysPopup = new PopupMenuAdapter(this, v);
      journeysPopup.popupMenu.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Override
  public void onClick(View elementFiredAction, int position) {
    JourneyModel journeyModel = (JourneyModel) data.values().toArray()[position];
    switch (elementFiredAction.getId()){
      case R.id.JOURNEY_TITLE_ID:
        onItemButtonPressed(journeyModel.getId());
        break;
      case R.id.JOURNEY_EDIT_ID:
        onStartItemPopup(elementFiredAction);
        break;
    }
  }

  View.OnClickListener OnCreateNewItem = v -> onCreateButtonPressed();

  @Override
  public void onPopupMenuSelected(MenuItem selectedMenuItem) {

  }
}

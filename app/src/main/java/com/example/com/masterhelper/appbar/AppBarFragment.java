package com.example.com.masterhelper.appbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import com.example.com.masterhelper.media.ui.MusicSettingsScreen;
import com.example.com.masterhelper.settings.ui.SettingList;
import com.example.masterhelper.R;

import static com.example.com.masterhelper.settings.SettingsFactory.*;


/**  */
public class AppBarFragment extends Fragment {
  /** верхнее меню */
  int appBarFragmentLayout = R.layout.fragment_app_bar;

  /** выпадающее меню  */
  int mainMenuMenu = R.menu.menu_main;

  IAppBarFragment mListener;

  public AppBarFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(appBarFragmentLayout, container, false);
  }

  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
    inflater.inflate(mainMenuMenu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent;
    switch (item.getItemId()){
      case R.id.ACTION_SETTINGS:
        intent = new Intent(getActivity(), MusicSettingsScreen.class);
        startActivity(intent);
        break;
      case R.id.ABILITIES_SETTINGS:
        intent = new Intent(getActivity(), SettingList.class);
        intent.putExtra(SettingList.EXTRA_TYPE, ABILITY_ITEM);
        intent.putExtra(SettingList.EXTRA_SETTING_TITLE, R.string.abilities_settings);
        startActivity(intent);
        break;
      case R.id.FORCES_SETTINGS:
          intent = new Intent(getActivity(), SettingList.class);
          intent.putExtra(SettingList.EXTRA_TYPE, FORCE_ITEM);
          intent.putExtra(SettingList.EXTRA_SETTING_TITLE, R.string.force_menu_item_title);
          startActivity(intent);
        break;
      case R.id.GOAL_SETTING_ID:
          intent = new Intent(getActivity(), SettingList.class);
          intent.putExtra(SettingList.EXTRA_TYPE, GOAL_ITEM);
          intent.putExtra(SettingList.EXTRA_SETTING_TITLE, R.string.force_goal_motivation_title);
          startActivity(intent);
        break;
      case R.id.ADVANCE_SETTING_ID:
          intent = new Intent(getActivity(), SettingList.class);
          intent.putExtra(SettingList.EXTRA_TYPE, ADVANCE_ITEM);
          intent.putExtra(SettingList.EXTRA_SETTING_TITLE, R.string.force_advantages_title);
          startActivity(intent);
        break;
      default:
        //mListener.onItemSelected(item);
    }
    return super.onOptionsItemSelected(item);
  }

}

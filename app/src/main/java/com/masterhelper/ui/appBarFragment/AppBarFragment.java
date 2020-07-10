package com.masterhelper.ui.appBarFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import com.example.masterhelper.R;


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
        intent = new Intent(getActivity(), AbilityNamesList.class);
        startActivity(intent);
        break;
      default:
        mListener.onItemSelected(item);
    }
    return super.onOptionsItemSelected(item);
  }

}
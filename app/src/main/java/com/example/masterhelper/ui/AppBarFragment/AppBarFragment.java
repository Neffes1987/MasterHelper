package com.example.masterhelper.ui.AppBarFragment;

import android.content.Context;
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

  /** кнопка настройки */
  int settingsButtonId = R.id.ACTION_SETTINGS;

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
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    mListener.onItemSelected(item);
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof IAppBarFragment) {
      mListener = (IAppBarFragment) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement IAppBarFragment");
    }
  }

}

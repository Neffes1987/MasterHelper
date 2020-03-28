package com.example.masterhelper.ui.AppBarFragment;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
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

    int id = item.getItemId();
    if (id == settingsButtonId) {
      Toast.makeText(getContext(), "ghbdtn", Toast.LENGTH_LONG).show();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}

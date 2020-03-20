package com.example.masterhelper.AppBarFragment;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.masterhelper.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class AppBarFragment extends Fragment {
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
    return inflater.inflate(R.layout.fragment_app_bar, container, false);
  }

  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
    inflater.inflate(R.menu.menu_main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Toast.makeText(getContext(), "ghbdtn", Toast.LENGTH_LONG).show();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}

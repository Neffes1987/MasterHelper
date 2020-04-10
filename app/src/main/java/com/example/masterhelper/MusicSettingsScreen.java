package com.example.masterhelper;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.ui.SoundsList.SoundsList;
import com.example.masterhelper.ui.SoundsList.model.ISoundsAdapterHolder;
import com.example.masterhelper.ui.SoundsList.model.SoundFileModel;

import java.util.Date;
import java.util.HashMap;

public class MusicSettingsScreen extends AppCompatActivity implements ISoundsAdapterHolder {

  private HashMap<Integer, SoundFileModel> data = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_music_settings_screen);

    FragmentManager fragmentManager = getSupportFragmentManager();
    SoundsList soundListFragment = (SoundsList) fragmentManager.findFragmentById(R.id.SOUND_LIST_FRAGMENT_ID);

    if(soundListFragment != null && soundListFragment.getView() != null){
      data.put(0, new SoundFileModel("ttt", new Date(), "1",1));
      soundListFragment.updateListAdapter(data);
    }
  }

  @Override
  public void onSoundSelected(int position, boolean isSelected) {

  }

  @Override
  public void onSoundStarted(int position) {

  }
}

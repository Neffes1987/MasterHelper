package com.example.masterhelper;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.ui.SoundsList.SoundsList;
import com.example.masterhelper.models.SoundFileModel;

import java.util.Date;
import java.util.HashMap;

public class MusicSettingsScreen extends AppCompatActivity implements ICommonItemEvents {

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
  public void onClick(View elementFiredAction, int position) {

  }
}

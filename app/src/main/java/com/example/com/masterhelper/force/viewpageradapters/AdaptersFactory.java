package com.example.com.masterhelper.force.viewpageradapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.masterhelper.R;

import java.util.ArrayList;

public class AdaptersFactory extends FragmentPagerAdapter {

  public AdaptersFactory(@NonNull FragmentManager fm) {
    super(fm);
  }

  private ArrayList<ModelList> adapterData = new ArrayList<>();
  private ArrayList<Integer> adapterTitles = new ArrayList<>();

  public enum AdapterTypes {
    goal,
    advantages,
    disadvantages,
    journeys,
    depended, owned,
  }

  public void setAdapterData(AdapterTypes type, ModelList list) {
    switch (type){
      case goal:
        adapterTitles.add(R.string.force_goal_motivation_title);
        break;
      case advantages:
        adapterTitles.add(R.string.force_advantages_title);
        break;

      case disadvantages:
        adapterTitles.add(R.string.force_disadvantages_title);
        break;
      case journeys:
        adapterTitles.add(R.string.force_journeys_title);
        break;
      case depended:
        adapterTitles.add(R.string.force_depended_title);
        break;
      case owned:
        adapterTitles.add(R.string.force_owned_title);
        break;
      default:
    }
    adapterData.add(list);
  }

  @Override
  public int getCount() {
    return adapterTitles.size();
  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    int title = adapterTitles.get(position);
    ModelList list = adapterData.get(position);
    return  AddNewRelation.newInstance(title, list);
  }
}
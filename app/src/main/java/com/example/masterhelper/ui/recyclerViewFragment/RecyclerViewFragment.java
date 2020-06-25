package com.example.masterhelper.ui.recyclerViewFragment;


import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.commonAdapter.item.CustomListItemsEnum;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.models.*;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;

import java.util.LinkedHashMap;

public class RecyclerViewFragment extends Fragment implements ICommonItemEvents, IRecycleFragment {

  /** */
  RecyclerView recyclerView;

  public RecyclerViewFragment() { }

  public void updateSceneListAdapter(LinkedHashMap<Integer, SceneRecycleDataModel> data){
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    CommonAdapter<SceneRecycleDataModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_view_scene_list_item, CustomListItemsEnum.scene, this);
    recyclerView.setAdapter(mAdapter);
  }

  public void updateScriptListAdapter(LinkedHashMap<Integer, ScriptRecycleDataModel> data) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    CommonAdapter<ScriptRecycleDataModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_view_script_list_item, CustomListItemsEnum.script, this);
    recyclerView.setAdapter(mAdapter);
  }

  public void updateJourneyListAdapter(LinkedHashMap<Integer, JourneyModel> data) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    CommonAdapter<JourneyModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_view_journey_item, CustomListItemsEnum.journey, this);
    recyclerView.setAdapter(mAdapter);
  }

  public void updateAbilitiesListAdapter(LinkedHashMap<Integer, AbilityModel> data) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    CommonAdapter<AbilityModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_view_journey_item, CustomListItemsEnum.abilities, this);
    recyclerView.setAdapter(mAdapter);
  }

  public void updateEnemiesListAdapter(LinkedHashMap<Integer, EnemyModel> data) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    CommonAdapter<EnemyModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_view_enemy_icon, CustomListItemsEnum.enemyIcon, this);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_recycler_view, container, false);
    recyclerView = fr.findViewById(R.id.RECYCLER_LIST_VIEW_ID);
    recyclerView.setHasFixedSize(true);
    return fr;
  }

  @Override
  public void onClick(View elementFiredAction, int position) {
    ICommonItemEvents screenActivity = (ICommonItemEvents) getActivity();
    assert screenActivity != null;
    screenActivity.onClick(elementFiredAction, position);
  }
}

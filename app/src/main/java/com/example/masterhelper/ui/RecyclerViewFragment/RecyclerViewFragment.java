package com.example.masterhelper.ui.RecyclerViewFragment;


import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.commonAdapter.item.CustomListItemsEnum;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.models.SceneRecycleDataModel;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.models.ScriptRecycleDataModel;

import java.util.HashMap;

public class RecyclerViewFragment extends Fragment implements ICommonItemEvents, IRecycleFragment {

  /** */
  RecyclerView recyclerView;

  /** */
  int fragmentRecyclerViewLayout = R.layout.fragment_recycler_view;

  /** */
  int recyclerListViewId = R.id.RECYCLER_LIST_VIEW_ID;

  public RecyclerViewFragment() { }

  public void updateSceneListAdapter(HashMap<Integer, SceneRecycleDataModel> data){
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    CommonAdapter<SceneRecycleDataModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_view_scene_list_item, CustomListItemsEnum.scene, this);
    recyclerView.setAdapter(mAdapter);
  }

  public void updateScriptListAdapter(HashMap<Integer, ScriptRecycleDataModel> data) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    CommonAdapter<ScriptRecycleDataModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_view_script_list_item, CustomListItemsEnum.script, this);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(fragmentRecyclerViewLayout, container, false);
    recyclerView = fr.findViewById(recyclerListViewId);
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

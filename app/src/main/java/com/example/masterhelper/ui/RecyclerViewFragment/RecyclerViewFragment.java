package com.example.masterhelper.ui.RecyclerViewFragment;


import com.example.masterhelper.ui.RecyclerViewFragment.adapters.scene.SceneAccordionAdapter;
import com.example.masterhelper.ui.RecyclerViewFragment.adapters.scene.ScriptAccordionAdapter;
import com.example.masterhelper.ui.RecyclerViewFragment.models.scene.SceneRecycleDataModel;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.RecyclerViewFragment.models.script.ScriptRecycleDataModel;

import java.util.HashMap;

public class RecyclerViewFragment extends Fragment implements IRecycleFragment {

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

    IRecycleAdapter screenActivity = (IRecycleAdapter) getActivity();
    SceneAccordionAdapter mAdapter = new SceneAccordionAdapter(data, screenActivity);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public void updateScriptListAdapter(HashMap<Integer, ScriptRecycleDataModel> data) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    IRecycleAdapter screenActivity = (IRecycleAdapter) getActivity();
    ScriptAccordionAdapter mAdapter = new ScriptAccordionAdapter(data, screenActivity);
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
}

package com.example.masterhelper.ui.SoundsList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.SoundsList.adapter.SoundsListAdapter;
import com.example.masterhelper.ui.SoundsList.model.ISoundsAdapterHolder;
import com.example.masterhelper.ui.SoundsList.model.SoundFileModel;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ISoundsAdapterHolder} interface
 * to handle interaction events.
 */
public class SoundsList extends Fragment implements ISoundsAdapterHolder {

  private ISoundsAdapterHolder mListener;

  private RecyclerView recyclerView;

  private View selfFragment;

  public SoundsList() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    selfFragment = inflater.inflate(R.layout.fragment_sounds_list, container, false);
    recyclerView = selfFragment.findViewById(R.id.SOUNDS_LIST_ID);
    return selfFragment;
  }

  public void updateListAdapter(HashMap<Integer, SoundFileModel> data){
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    SoundsListAdapter mAdapter = new SoundsListAdapter(data);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof ISoundsAdapterHolder) {
      mListener = (ISoundsAdapterHolder) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement ISoundsAdapterHolder");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onSoundSelected(int position, boolean isSelected) {
    mListener.onSoundSelected(position,isSelected);
  }

  @Override
  public void onSoundStarted(int position) {
    mListener.onSoundStarted(position);
  }
}

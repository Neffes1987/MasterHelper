package com.example.masterhelper.ui.SoundsList;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.commonAdapter.item.CustomListItemsEnum;
import com.example.masterhelper.commonAdapter.item.ICommonItemEvents;
import com.example.masterhelper.models.SoundFileModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ICommonItemEvents} interface
 * to handle interaction events.
 */
public class SoundsList extends Fragment implements ICommonItemEvents {

  private ICommonItemEvents mListener;

  private RecyclerView recyclerView;
  private FloatingActionButton createBtn;

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
    createBtn = selfFragment.findViewById(R.id.ADD_NEW_FILE_ID);
    createBtn.setOnClickListener(addSoundListener);
    return selfFragment;
  }

  public void updateListAdapter(HashMap<Integer, SoundFileModel> data){
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    CommonAdapter<SoundFileModel> mAdapter = new CommonAdapter<>(data, R.layout.fragment_sounds_item, CustomListItemsEnum.music, this);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof ICommonItemEvents) {
      mListener = (ICommonItemEvents) context;
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

  View.OnClickListener addSoundListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      mListener.onClick(v, -1);
    }
  };

  @Override
  public void onClick(View elementFiredAction, int position) {
    mListener.onClick(elementFiredAction, position);
  }
}

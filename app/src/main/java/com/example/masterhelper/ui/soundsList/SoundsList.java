package com.example.masterhelper.ui.soundsList;

import android.annotation.SuppressLint;
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

import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ICommonItemEvents} interface
 * to handle interaction events.
 */
public class SoundsList extends Fragment implements ICommonItemEvents {
  public static final int ADD_MUSIC_BTN = -1;
  public static final int ATTACH_MUSIC_BTN = -2;


  private ICommonItemEvents mListener;

  private RecyclerView recyclerView;

  FloatingActionButton createBtn;
  FloatingActionButton addBtn;

  public SoundsList() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View selfFragment = inflater.inflate(R.layout.fragment_sounds_list, container, false);
    recyclerView = selfFragment.findViewById(R.id.SOUNDS_LIST_ID);

    createBtn = selfFragment.findViewById(R.id.ADD_NEW_FILE_ID);
    addBtn = selfFragment.findViewById(R.id.ADD_FILES_TO_EVENT_ID);


    createBtn.setOnClickListener(addSoundListener);
    addBtn.setOnClickListener(attachSoundListener);
    return selfFragment;
  }

  @SuppressLint("RestrictedApi")
  public void updateListAdapter(LinkedHashMap<Integer, SoundFileModel> data, Boolean isGeneral){
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    if(isGeneral){
      createBtn.setVisibility(View.VISIBLE);
      addBtn.setVisibility(View.GONE);
    } else {
      createBtn.setVisibility(View.GONE);
      addBtn.setVisibility(View.VISIBLE);
    }

    CommonAdapter<SoundFileModel> mAdapter = new CommonAdapter<>(
      data,
      R.layout.fragment_sounds_item,
      isGeneral ? CustomListItemsEnum.musicGeneral : CustomListItemsEnum.music,
      this
    );
    recyclerView.setAdapter(mAdapter);
  }

  public void updateAdapterItem(int position){
    if(recyclerView.getAdapter() != null){
      recyclerView.getAdapter().notifyItemChanged(position);
    }
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
      mListener.onClick(v, ADD_MUSIC_BTN);
    }
  };

  View.OnClickListener attachSoundListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      mListener.onClick(v, ATTACH_MUSIC_BTN);
    }
  };

  @Override
  public void onClick(View elementFiredAction, int position) {
    mListener.onClick(elementFiredAction, position);
  }
}

package com.example.com.masterhelper.media.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.com.masterhelper.core.components.buttons.ComponentFloatButtonSecondary;
import com.example.com.masterhelper.core.components.buttons.ComponentFloatButtonPrimary;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.CommonItem;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.ICommonItemEvents;


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

  ComponentFloatButtonPrimary createBtn;
  ComponentFloatButtonSecondary addBtn;

  public SoundsList() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View selfFragment = inflater.inflate(R.layout.fragment_list_sounds, container, false);
    recyclerView = selfFragment.findViewById(R.id.SOUNDS_LIST_ID);

    View createBtnWrapper = selfFragment.findViewById(R.id.ADD_NEW_FILE_ID);
    createBtn = new ComponentFloatButtonPrimary(createBtnWrapper);

    View addBtnWrapper = selfFragment.findViewById(R.id.ADD_FILES_TO_EVENT_ID);
    addBtn = new ComponentFloatButtonSecondary(addBtnWrapper);


    createBtn.setListener(v -> mListener.onClick(v, ADD_MUSIC_BTN));
    addBtn.setListener(v -> mListener.onClick(v, ATTACH_MUSIC_BTN));
    return selfFragment;
  }

  @SuppressLint("RestrictedApi")
  public void updateListAdapter(ModelList data, Boolean isGeneral){
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    if(isGeneral){
      createBtn.setVisibility(true);
      addBtn.setVisibility(false);
    } else {
      createBtn.setVisibility(false);
      addBtn.setVisibility(true);
    }



    CommonAdapter mAdapter = new CommonAdapter(
      data,
      R.layout.fragment_view_list_item_sounds,
      this
    );

    mAdapter.setCommonItemInstanceGetter(currAdapter -> {
      CommonItem item = new MusicItem(isGeneral);
      item.attachAdapter(currAdapter);
      return item;
    });

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

  @Override
  public void onClick(View elementFiredAction, int position) {
    mListener.onClick(elementFiredAction, position);
  }

}

package com.example.com.masterhelper.listFactory;

import com.example.com.masterhelper.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;

public class ListFactory extends Fragment implements ICommonItemEvents {
  /** */
  RecyclerView recyclerView;

  int itemLayout = 0;
  CustomListItemsEnum itemType;

  CommonAdapter mAdapter;

  public void setAdapter(CommonAdapter mAdapter) {

    this.mAdapter = mAdapter;

    if(recyclerView != null) {
      recyclerView.setAdapter(mAdapter);
    }
  }

  public ListFactory() {}

  private void setItemType(CustomListItemsEnum itemType) {
    this.itemType = itemType;
    switch (itemType){
      case abilities:
      case setting:
      case relation:
      case setting_selectable:
      case journey:
        itemLayout = R.layout.fragment_view_row_item;
        break;
      case enemyIcon:
        itemLayout = R.layout.fragment_view_enemy_icon;
        break;
      case scene:
        itemLayout = R.layout.fragment_view_scene_list_item;
        break;
      case script:
        itemLayout = R.layout.fragment_view_script_list_item;
        break;
      case force:
        itemLayout = R.layout.fragment_view_forces_list_item;
        break;
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    View fr = inflater.inflate(R.layout.fragment_recycler_view, container, false);
    Object tag = fr.getTag();
    if(tag != null){
      recyclerView = fr.findViewWithTag(tag);
    } else {
      recyclerView = fr.findViewById(R.id.RECYCLER_LIST_VIEW_ID);
    }
    recyclerView.setLayoutManager(layoutManager);
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

package com.example.com.masterhelper.core.factories.list;


import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.factories.list.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.factories.list.commonAdapter.item.ICommonItemEvents;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;

public class ListFactory extends Fragment implements ICommonItemEvents, IListFactory {
  /** */
  RecyclerView recyclerView;
  int recyclerViewId = R.id.RECYCLER_LIST_VIEW_ID;

  int itemLayout = 0;
  CustomListItemsEnum itemType;

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

  public void updateListAdapter(ModelList data, CustomListItemsEnum itemType){
    setItemType(itemType);
    CommonAdapter mAdapter = new CommonAdapter(data, itemLayout, itemType, this);
    if(recyclerView != null) recyclerView.setAdapter(mAdapter);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    View fr = inflater.inflate(R.layout.fragment_recycler_view, container, false);
    recyclerView = fr.findViewById(R.id.RECYCLER_LIST_VIEW_ID);

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

package com.example.com.masterhelper.listFactory.commonAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.listFactory.commonAdapter.item.CommonItem;
import com.example.com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;


/**
 * Адаптор для работы с аккордионами внутри цеклического списка
 * */
public class CommonAdapter extends RecyclerView.Adapter<CommonHolder> implements ICommonItemEvents{
  /** */
  int fragmentId;

  /** */
  private ModelList mDataset;
  private ICommonItemEvents parentScreenView;

  private CommonItem commonItemView;

  /** Инициализируем набор данных для списка и передаем указатель на активность */
  public CommonAdapter(ModelList data, int listItemFragmentId, CommonItem commonItemView, ICommonItemEvents parentScreen) {
    mDataset = data;
    fragmentId = listItemFragmentId;
    parentScreenView = parentScreen;

    commonItemView.attachAdapter(this);
    this.commonItemView = commonItemView;
  }

  private int getItemPosition(int itemId){
    return mDataset.getPositionById(itemId);
  }

  public DataModel getItemById(int id){
    return mDataset.get(id);
  }

  public void deleteItem(int itemId){
    int position = getItemPosition(itemId);
    mDataset.remove(itemId);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, mDataset.size());
  }


  /** создаем UI слой для холдера и инициализируем его.
   * Указатель на сцену нужен, чтобы прокинуть коллбек на кнопки */
  @NonNull
  @Override
  public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(fragmentId, parent, false);
    try {
      return new CommonHolder(v, commonItemView);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull CommonHolder holder, int position) {
    DataModel itemData = mDataset.getItemByPosition(position);
    assert itemData != null;
    holder.initRecyclerView(itemData);
  }


  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    if(mDataset == null){
      return 0;
    }
    return mDataset.size();
  }

  @Override
  public void onClick(View elementFiredAction, int position) {
    parentScreenView.onClick(elementFiredAction, position);
  }
}

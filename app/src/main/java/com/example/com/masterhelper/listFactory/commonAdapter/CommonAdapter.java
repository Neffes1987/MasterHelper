package com.example.com.masterhelper.listFactory.commonAdapter;

import android.util.Log;
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

  private GetCommonItemInstance CommonItemInstance;

  public void setCommonItemInstanceGetter(GetCommonItemInstance instance) {
    this.CommonItemInstance = instance;
  }

  /** Инициализируем набор данных для списка и передаем указатель на активность */
  public CommonAdapter(ModelList data, int listItemFragmentId, ICommonItemEvents parentScreen) {
    mDataset = data;
    fragmentId = listItemFragmentId;
    parentScreenView = parentScreen;
    setHasStableIds(true);
  }

  private int getItemPosition(int itemId){
    return mDataset.getPositionById(itemId);
  }

  public DataModel getItemById(int id){
    return mDataset.get(id);
  }

  @Override
  public long getItemId(int position) {
    return mDataset.getItemByPosition(position).getId();
  }

  public void deleteItem(int itemId){
    int position = getItemPosition(itemId);
    mDataset.remove(itemId);
    notifyItemRemoved(position);
  }

  public void updateItem(DataModel updatedData){
    mDataset.update(updatedData);
    int position = getItemPosition(updatedData.getId());
    notifyItemChanged(position, updatedData);
  }

  public void addItem(DataModel updatedData, boolean toFirst){
    mDataset.addToList(updatedData, toFirst);
    int position = getItemPosition(updatedData.getId());
    notifyItemInserted(position);
  }


  /** создаем UI слой для холдера и инициализируем его.
   * Указатель на сцену нужен, чтобы прокинуть коллбек на кнопки */
  @NonNull
  @Override
  public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(fragmentId, parent, false);
    try {
      return new CommonHolder(v, CommonItemInstance.getCommonItemInstance(this));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull CommonHolder holder, int position) {
    DataModel itemData = mDataset.getItemByPosition(position);
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

  public interface GetCommonItemInstance{
    CommonItem getCommonItemInstance(CommonAdapter adapter);
  }
}

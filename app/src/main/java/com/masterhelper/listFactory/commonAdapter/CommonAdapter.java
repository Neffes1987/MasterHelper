package com.masterhelper.listFactory.commonAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.masterhelper.listFactory.CustomListItemsEnum;
import com.masterhelper.listFactory.commonAdapter.item.ICommonItemEvents;

import java.util.LinkedHashMap;

/**
 * Адаптор для работы с аккордионами внутри цеклического списка
 * */
public class CommonAdapter<Model> extends RecyclerView.Adapter<CommonHolder<Model>> implements ICommonItemEvents{
  /** */
  int fragmentId;

  /** */
  private LinkedHashMap<Integer, Model> mDataset;
  private ICommonItemEvents parentScreenView;

  CustomListItemsEnum commonItemType;

  /** Инициализируем набор данных для списка и передаем указатель на активность */
  public CommonAdapter(LinkedHashMap<Integer, Model> data, int listItemFragmentId,  CustomListItemsEnum viewType, ICommonItemEvents parentScreen) {
    mDataset = data;
    fragmentId = listItemFragmentId;
    commonItemType = viewType;
    parentScreenView = parentScreen;
  }

  /** создаем UI слой для холдера и инициализируем его.
   * Указатель на сцену нужен, чтобы прокинуть коллбек на кнопки */
  @NonNull
  @Override
  public CommonHolder<Model> onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(fragmentId, parent, false);
    try {
      return new CommonHolder<>(v, commonItemType, this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /** привязываем данные к сцены к холдеру адаптера
   *  берем данные из маппы и инициализируем холдер
   * */
  @Override
  public void onBindViewHolder(CommonHolder<Model> holder, final int position) {
    Model itemData = (Model) mDataset.values().toArray()[position];
    assert itemData != null;
    holder.initRecyclerView(itemData, position);
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

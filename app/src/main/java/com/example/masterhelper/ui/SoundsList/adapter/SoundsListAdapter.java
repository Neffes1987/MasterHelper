package com.example.masterhelper.ui.SoundsList.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.SoundsList.model.ISoundsAdapterHolder;
import com.example.masterhelper.ui.SoundsList.model.SoundFileModel;
import com.example.masterhelper.ui.SoundsList.model.SoundsAdapterHolder;

import java.util.HashMap;

/**
 * Адаптор для работы с аккордионами внутри цеклического списка
 * */
public class SoundsListAdapter extends RecyclerView.Adapter<SoundsAdapterHolder> implements ISoundsAdapterHolder {
  /** */
  int soundListItemFragment = R.layout.fragment_sounds_item;

  /** */
  private HashMap<Integer, SoundFileModel> mDataset;

  /** Инициализируем набор данных для списка и передаем указатель на активность */
  public SoundsListAdapter(HashMap<Integer, SoundFileModel> data) {
    mDataset = data;
  }

  /** создаем UI слой для холдера и инициализируем его.
   * Указатель на сцену нужен, чтобы прокинуть коллбек на кнопки */
  @NonNull
  @Override
  public SoundsAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
      .inflate(soundListItemFragment, parent, false);
    return new SoundsAdapterHolder(v);
  }

  /** привязываем данные к сцены к холдеру адаптера
   *  берем данные из маппы и инициализируем холдер
   * */
  @Override
  public void onBindViewHolder(SoundsAdapterHolder holder, final int position) {
    SoundFileModel itemData = mDataset.get(position);
    assert itemData != null;

    holder.updateHolderByData(itemData, position);
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
  public void onSoundSelected(int position, boolean isSelected) {

  }

  @Override
  public void onSoundStarted(int position) {

  }
}

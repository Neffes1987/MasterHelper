package com.example.masterhelper.RecyclerViewFragment.adapters.scene;

import com.example.masterhelper.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.RecyclerViewFragment.models.scene.SceneAdapterHolder;
import com.example.masterhelper.RecyclerViewFragment.models.scene.SceneRecycleDataModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;

import java.util.HashMap;

/**
 * Адаптор для работы с аккордионами внутри цеклического списка
 * */
public class SceneAccordionAdapter extends RecyclerView.Adapter<SceneAdapterHolder> {

  private HashMap<Integer, SceneRecycleDataModel> mDataset;
  private IRecycleAdapter screen;

  /** Инициализируем набор данных для списка и передаем указатель на активность */
  public SceneAccordionAdapter(HashMap<Integer, SceneRecycleDataModel> data, IRecycleAdapter screen) {
    mDataset = data;
    this.screen = screen;
  }

  /** создаем UI слой для холдера и инициализируем его.
   * Указатель на сцену нужен, чтобы прокинуть коллбек на кнопки */
  @NonNull
  @Override
  public SceneAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.fragment_view_scene_list_item, parent, false);
    return new SceneAdapterHolder(v, screen);
  }

  /** привязываем данные к сцены к холдеру адаптера
   *  берем данные из маппы и инициализируем холдер
   * */
  @Override
  public void onBindViewHolder(SceneAdapterHolder holder, final int position) {
    SceneRecycleDataModel itemData = mDataset.get(position);
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
}

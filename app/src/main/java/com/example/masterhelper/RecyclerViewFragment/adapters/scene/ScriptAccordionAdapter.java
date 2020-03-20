package com.example.masterhelper.RecyclerViewFragment.adapters.scene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.RecyclerViewFragment.models.script.ScriptAdapterHolder;
import com.example.masterhelper.RecyclerViewFragment.models.script.ScriptRecycleDataModel;

import java.util.HashMap;

/**
 * Адаптор для работы с аккордионами внутри цеклического списка
 * */
public class ScriptAccordionAdapter extends RecyclerView.Adapter<ScriptAdapterHolder> {

  private HashMap<Integer, ScriptRecycleDataModel> mDataset;
  private IRecycleAdapter screen;

  /** Инициализируем набор данных для списка и передаем указатель на активность */
  public ScriptAccordionAdapter(HashMap<Integer, ScriptRecycleDataModel> data, IRecycleAdapter screen) {
    mDataset = data;
    this.screen = screen;
  }

  /** создаем UI слой для холдера и инициализируем его.
   * Указатель на сцену нужен, чтобы прокинуть коллбек на кнопки */
  @NonNull
  @Override
  public ScriptAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.fragment_script_list_item, parent, false);
    return new ScriptAdapterHolder(v, screen);
  }

  /** привязываем данные к сцены к холдеру адаптера
   *  берем данные из маппы и инициализируем холдер
   * */
  @Override
  public void onBindViewHolder(ScriptAdapterHolder holder, final int position) {
    ScriptRecycleDataModel itemData = mDataset.get(position);
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

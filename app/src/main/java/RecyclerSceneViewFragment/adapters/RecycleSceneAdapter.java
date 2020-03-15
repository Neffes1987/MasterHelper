package RecyclerSceneViewFragment.adapters;

import RecyclerSceneViewFragment.IRecycleSceneAdapter;
import RecyclerSceneViewFragment.model.AdapterHolder;
import RecyclerSceneViewFragment.model.RecycleSceneDataModel;
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
public class RecycleSceneAdapter extends RecyclerView.Adapter<AdapterHolder> {

  private HashMap<Integer, RecycleSceneDataModel> mDataset;
  private IRecycleSceneAdapter screen;

  /** Инициализируем набор данных для списка и передаем указатель на активность */
  public RecycleSceneAdapter(HashMap<Integer, RecycleSceneDataModel> data, IRecycleSceneAdapter screen) {
    mDataset = data;
    this.screen = screen;
  }

  /** создаем UI слой для холдера и инициализируем его.
   * Указатель на сцену нужен, чтобы прокинуть коллбек на кнопки */
  @NonNull
  @Override
  public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.expand_scene_list_item, parent, false);
    return new AdapterHolder(v, screen);
  }

  /** привязываем данные к сцены к холдеру адаптера
   *  берем данные из маппы и инициализируем холдер
   * */
  @Override
  public void onBindViewHolder(AdapterHolder holder, final int position) {
    RecycleSceneDataModel itemData = mDataset.get(position);
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

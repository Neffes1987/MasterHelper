package com.example.masterhelper.commonAdapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.commonAdapter.item.CommonItem;
import com.example.masterhelper.commonAdapter.item.CustomListItemsEnum;
import com.example.masterhelper.commonAdapter.item.MusicItem;
import com.example.masterhelper.ui.SoundsList.model.SoundFileModel;

/** Промежуточный класс, который связывает адаптер и вьюху циклического списка*/
public class CommonHolder<Model> extends RecyclerView.ViewHolder {
  private CommonItem commonItem;
  /** @constructor генератор указателей на элементы UI для адаптера */
  public void initRecyclerView(Model item, int position) {
    commonItem.updateHolderByData((SoundFileModel)item, position);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public CommonHolder(View v, CustomListItemsEnum itemTemplateType, CommonAdapter<Model> adapter) {
    super(v);
    switch (itemTemplateType){
      case music:
        commonItem = new MusicItem<Model>(v, adapter);
        break;
    }
  }
}

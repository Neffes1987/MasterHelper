package com.example.masterhelper.commonAdapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.commonAdapter.item.*;

/** Промежуточный класс, который связывает адаптер и вьюху циклического списка*/
public class CommonHolder<Model> extends RecyclerView.ViewHolder {
  private CommonItem commonItem;
  /** @constructor генератор указателей на элементы UI для адаптера */
  public void initRecyclerView(Model item, int position) {
    commonItem.updateHolderByData(item, position);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public CommonHolder(View v, CustomListItemsEnum itemTemplateType, CommonAdapter<Model> adapter) {
    super(v);
    switch (itemTemplateType){
      case music:
        commonItem = new MusicItem<Model>(v, adapter);
        break;
      case scene:
        commonItem = new SceneItem<Model>(v, adapter);
        break;
      case script:
        commonItem = new ScriptItem<Model>(v, adapter);
        break;
    }
  }
}

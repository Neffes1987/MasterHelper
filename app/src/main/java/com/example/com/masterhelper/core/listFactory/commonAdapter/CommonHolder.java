package com.example.com.masterhelper.core.listFactory.commonAdapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.com.masterhelper.core.listFactory.CustomListItemsEnum;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.*;

/** Промежуточный класс, который связывает адаптер и вьюху циклического списка*/
public class CommonHolder<Model> extends RecyclerView.ViewHolder {
  private CommonItem<Model> commonItem;
  /** @constructor передача параметров в элемент списка после инициализации */
  public void initRecyclerView(Model item, int position) {
    commonItem.updateHolderByData(item, position);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public CommonHolder(View v, CustomListItemsEnum itemTemplateType, CommonAdapter<Model> adapter) {
    super(v);
    switch (itemTemplateType){
      case music:
        commonItem = new MusicItem<>(v, adapter, false);
        break;
      case musicGeneral:
        commonItem = new MusicItem<>(v, adapter, true);
        break;
      case scene:
        commonItem = new SceneItem<>(v, adapter);
        break;
      case script:
        commonItem = new ScriptItem<>(v, adapter);
        break;
      case enemyIcon:
        commonItem = new EnemyIconItem<>(v, adapter);
        break;
      case journey:
        commonItem = new JourneyItem<>(v, adapter, true);
        break;
      case abilities:
        commonItem = new AbilityItem<>(v, adapter, true);
        break;
    }
  }
}

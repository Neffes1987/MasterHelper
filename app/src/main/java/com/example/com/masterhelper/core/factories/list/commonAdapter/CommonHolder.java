package com.example.com.masterhelper.core.factories.list.commonAdapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.factories.list.CustomListItemsEnum;
import com.example.com.masterhelper.core.factories.list.commonAdapter.item.*;

/** Промежуточный класс, который связывает адаптер и вьюху циклического списка*/
public class CommonHolder extends RecyclerView.ViewHolder {
  private CommonItem commonItem;
  /** @constructor передача параметров в элемент списка после инициализации */
  public void initRecyclerView(DataModel item, int position) {
    commonItem.updateHolderByData(item, position);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public CommonHolder(View v, CustomListItemsEnum itemTemplateType, CommonAdapter adapter) {
    super(v);
    switch (itemTemplateType){
      case music:
        commonItem = new MusicItem(v, adapter, false);
        break;
      case musicGeneral:
        commonItem = new MusicItem(v, adapter, true);
        break;
      case scene:
        commonItem = new SceneItem(v, adapter);
        break;
      case script:
        commonItem = new ScriptItem(v, adapter);
        break;
      case enemyIcon:
        commonItem = new EnemyIconItem(v, adapter);
        break;
      case journey:
        commonItem = new SettingsItem(v, adapter);
        break;
      case abilities:
        commonItem = new SettingsItem(v, adapter, true, true, true);
        break;
      case setting:
        commonItem = new SettingsItem(v, adapter, true, false, true);
        break;
      case force:
        commonItem = new ForceItem(v, adapter);
    }
  }
}

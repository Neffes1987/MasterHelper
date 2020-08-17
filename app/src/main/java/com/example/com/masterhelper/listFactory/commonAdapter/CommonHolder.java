package com.example.com.masterhelper.listFactory.commonAdapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.listFactory.commonAdapter.item.*;

/** Промежуточный класс, который связывает адаптер и вьюху циклического списка*/
public class CommonHolder extends RecyclerView.ViewHolder {
  private CommonItem commonItem;
  /** @constructor передача параметров в элемент списка после инициализации */
  public void initRecyclerView(DataModel item) {
    commonItem.updateHolderByData(item);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public CommonHolder(View v, CommonItem commonItem) {
    super(v);
    commonItem.attachItemView(v);
    this.commonItem = commonItem;
    /**
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
        commonItem = new SettingsItem(v, adapter, SettingsItem.SettingsType.journey);
        break;
      case abilities:
        commonItem = new SettingsItem(v, adapter, SettingsItem.SettingsType.abilities);
        break;
      case setting:
        commonItem = new SettingsItem(v, adapter, SettingsItem.SettingsType.global);
        break;
      case setting_selectable:
        commonItem = new SettingsItem(v, adapter, SettingsItem.SettingsType.selectable);
        break;
      case relation:
        commonItem = new SettingsItem(v, adapter, SettingsItem.SettingsType.relation);
        break;
      case force:
        commonItem = new ForceItem(v, adapter);
    } */
  }
}

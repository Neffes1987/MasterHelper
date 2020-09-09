package com.example.com.masterhelper.core.listFactory.commonAdapter.item;

import android.view.View;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class CommonItem implements ICommonItem {
  public CommonAdapter adapter;

  protected View itemView;
  protected int id;

  public CommonItem() {}

  protected View.OnClickListener commonListener =  new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      ((ICommonItemEvents) adapter).onClick(v, id);
    }
  };

  public void attachItemView(View v){
    itemView = v;
  }

  public void attachAdapter(CommonAdapter adapter){
    this.adapter = adapter;
  }

  public void updateHolderByData(DataModel itemData) {
    id = itemData.getId();
  }

  public void toggleVisibility(View v){
    int currentToggle = v.getVisibility();
    v.setVisibility(currentToggle == View.GONE ? View.VISIBLE : View.GONE);
  }


}

package com.example.com.masterhelper.listFactory.commonAdapter.item;

import android.view.View;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.listFactory.commonAdapter.CommonAdapter;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class CommonItem implements ICommonItem {
  public CommonAdapter adapter;

  protected View itemView;

  public CommonItem() {}

  public View.OnClickListener commonListener = v -> {};

  public void attachItemView(View v){
    itemView = v;
  }

  public void attachAdapter(CommonAdapter adapter){
    this.adapter = adapter;
  }

  public void updateHolderByData(DataModel itemData) {}

  public void toggleVisibility(View v){
    int currentToggle = v.getVisibility();
    v.setVisibility(currentToggle == View.GONE ? View.VISIBLE : View.GONE);
  }


}

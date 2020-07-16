package com.example.com.masterhelper.core.factories.list.commonAdapter.item;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.factories.list.commonAdapter.CommonAdapter;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class CommonItem extends RecyclerView.ViewHolder implements ICommonItem {
  public CommonAdapter adapter;
  public int position;

  /**
   * @param v - вьюха элемент списка
   * @param adapter - адаптер в рамках которого содержится элемент списка
   * @constructor генератор указателей на элементы UI для адаптера
   */
  public CommonItem(View v, CommonAdapter adapter) {
    super(v);
    this.adapter = adapter;
  }

  public void updateHolderByData(DataModel itemData, int position) {}

  public void setPosition(int position) {
    this.position = position;
  }

  public void toggleVisibility(View v){
    int currentToggle = v.getVisibility();
    v.setVisibility(currentToggle == View.GONE ? View.VISIBLE : View.GONE);
  }

  View.OnClickListener commonListener =  new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      ((ICommonItemEvents) adapter).onClick(v, position);
    }
  };
}
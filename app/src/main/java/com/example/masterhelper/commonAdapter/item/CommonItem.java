package com.example.masterhelper.commonAdapter.item;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.commonAdapter.CommonAdapter;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class CommonItem<Model> extends RecyclerView.ViewHolder implements ICommonItem<Model> {
  public CommonAdapter<Model> adapter;
  public int position;

  /**
   * @param v - вьюха элемент списка
   * @param adapter - адаптер в рамках которого содержится элемент списка
   * @constructor генератор указателей на элементы UI для адаптера
   */
  public CommonItem(View v, CommonAdapter<Model> adapter) {
    super(v);
    this.adapter = adapter;
  }
  public void updateHolderByData(Model itemData, int position) {}

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

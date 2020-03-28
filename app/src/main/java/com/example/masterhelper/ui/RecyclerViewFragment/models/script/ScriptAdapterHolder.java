package com.example.masterhelper.ui.RecyclerViewFragment.models.script;

import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerAccordionEvents;

/** Модель для управления интерфейсом внутри аккордиона для цеклического списка*/
public class ScriptAdapterHolder extends RecyclerView.ViewHolder{

  /** текстовое поле в с именем сцены */
  private TextView title;

  /** текстовое поле с описание сцены */
  private TextView description;

  /** блок тела аккордиона, чтобы сворачивать разворачицвать его */
  private LinearLayout body;

  /** виджет прогресса по сцене, виден всегда */
  private ImageView hasBattleActionIcon;

  /** иконка окончания сцены, появляется в заголовке аккордиона */
  private ImageView isFinishedIcon;

  /** Кнопка переключения состояния аккордиона */
  public AppCompatImageButton expandButton;

  /** Кнопка запуска сцены */
  public AppCompatImageView startScene;

  /** кнопка редактирования сцены */
  public AppCompatImageButton editBtn;

  /** кнопка удаления сцены */
  public AppCompatImageButton deleteBtn;

  /** кнопка перемещения сцены выше по списку */
  public AppCompatImageButton upOrderBtn;

  /** кнопка перемещения сцены ниже по списку */
  public AppCompatImageButton downOrderBtn;
  private int position;
  private IRecycleAdapter screen;

  /** установить название сцены в виджет
   * @param title - имя сцены
   * */
  private void setTitle(String title) {
    this.title.setText(title);
  }

  /** передать описание сцены в виджет
   * @param description - описание сцены
   * */
  private void setDescription(String description) {
    this.description.setText(description);
  }

  /** указать текущее количество пройденых сцен
   * @param isVisible - определяет боевая это сцена или ситуативная
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setHasBattleActionIcon(boolean isVisible) {
    hasBattleActionIcon.setVisibility(isVisible ? View.VISIBLE : View.GONE);
  }

  /** указать состояние флага пройденной сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setSceneIsDone(ScriptRecycleDataModel item) {
    this.isFinishedIcon.setVisibility(item.isFinished ? View.VISIBLE : View.GONE);
  }

  /** получить текущее положение холдера в списке холждеров адаптера
   * @param position - текущая позиция холдера сцены в списке
   * */
  private void setHolderPosition(int position){
    this.position = position;
  }

  /** инициализировать все поля холдера данными, а так же назначить порядковый номер сцены в списке
   * @param itemData - набор данных для инициализации сцены
   * @param position - текущая позиция холдера сцены в списке
   * */
  public void updateHolderByData(ScriptRecycleDataModel itemData, int position){
    setTitle(itemData.title);
    setDescription(itemData.description);
    setHolderPosition(position);
    setHasBattleActionIcon(itemData.hasBattleActionIcon);
    setSceneIsDone(itemData);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public ScriptAdapterHolder(View v, IRecycleAdapter screen) {
    super(v);
    this.screen = screen;

    title = v.findViewById(R.id.SCRIPT_TITLE_ID);
    description = v.findViewById(R.id.SCRIPT_DESCRIPTION_ID);

    body = v.findViewById(R.id.ACCORDION_SCRIPT_BODY_ID);
    body.setVisibility(View.GONE);

    isFinishedIcon = v.findViewById(R.id.IS_SCRIPT_DONE_FLAG_ID);

    startScene = v.findViewById(R.id.SCRIPT_START_BTN_ID);
    startScene.setOnClickListener(startSceneListener);

    editBtn = v.findViewById(R.id.SCRIPT_EDIT_BTN_ID);
    editBtn.setOnClickListener(editSceneListener);

    deleteBtn = v.findViewById(R.id.SCRIPT_DELETE_BTN_ID);
    deleteBtn.setOnClickListener(deleteSceneListener);

    upOrderBtn = v.findViewById(R.id.SCRIPT_UP_ORDER_BTN_ID);
    upOrderBtn.setOnClickListener(upOrderSceneListener);

    downOrderBtn = v.findViewById(R.id.SCRIPT_DOWN_ORDER_BTN_ID);
    downOrderBtn.setOnClickListener(downOrderSceneListener);

    hasBattleActionIcon = v.findViewById(R.id.SCRIPT_HAS_BATTLE_ACTION_ICON_ID);

    expandButton = v.findViewById(R.id.SCRIPT_TOGGLER_ID);
    expandButton.setOnClickListener(toggleScriptListener);
  }

  View.OnClickListener toggleScriptListener = new View.OnClickListener() {
    /** переключение состояния аккордиона */
    @Override
    public void onClick(View v) {
      int newState = body.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
      body.setVisibility(newState);
    }
  };

  View.OnClickListener editSceneListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      screen.onChangeItem(position, RecyclerAccordionEvents.edit, "1");
    }
  };

  View.OnClickListener startSceneListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      screen.onChangeItem(position, RecyclerAccordionEvents.start, "1");
    }
  };

  View.OnClickListener deleteSceneListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      screen.onChangeItem(position, RecyclerAccordionEvents.delete, "1");
    }
  };

  View.OnClickListener upOrderSceneListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      screen.onChangeItem(position, RecyclerAccordionEvents.up, "1");
    }
  };

  View.OnClickListener downOrderSceneListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      screen.onChangeItem(position, RecyclerAccordionEvents.down, "1");
    }
  };
}

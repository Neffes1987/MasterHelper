package com.example.masterhelper.ui.RecyclerViewFragment.models.scene;

import com.example.masterhelper.ui.RecyclerViewFragment.IRecycleAdapter;
import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerAccordionEvents;

/** Модель для управления интерфейсом внутри аккордиона для цеклического списка*/
public class SceneAdapterHolder extends RecyclerView.ViewHolder{

  /** текстовое поле в с именем сцены */
  private TextView title;

  /** текстовое поле с описание сцены */
  private TextView description;

  /** текстовое поле с указанием процденых/всех скриптов в сцене */
  private TextView screenStepsValue;

  /** блок тела аккордиона, чтобы сворачивать разворачицвать его */
  private LinearLayout body;

  /** виджет прогресса по сцене, виден всегда */
  private ProgressBar progressBar;

  /** включить\выключить музыку в сцене */
  private Switch isMusicToggledFlag;

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

  /** передать текущее состояние флага включения музыки в виджет
   * @param isMusicToggledFlag - флаг состояния переключения фоновой музыки
   * */
  private void setIsMusicToggledFlag(Boolean isMusicToggledFlag) {
    this.isMusicToggledFlag.setChecked(isMusicToggledFlag);
  }

  /** указать текущее количество пройденых сцен
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setScreenStepsValue(SceneRecycleDataModel item) {
    String scripts = item.scriptsFinished +"/"+ item.scriptsTotal;
    this.screenStepsValue.setText(scripts);
  }

  /** указать состояние флага пройденной сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setSceneIsDone(SceneRecycleDataModel item) {
    if(item.scriptsTotal <= item.scriptsFinished && item.scriptsTotal != 0) {
      this.isFinishedIcon.setVisibility(View.VISIBLE);
    } else {
      this.isFinishedIcon.setVisibility(View.GONE);
    }
  }

  /** получить текущее положение холдера в списке холждеров адаптера
   * @param position - текущая позиция холдера сцены в списке
   * */
  private void setHolderPosition(int position){
    this.position = position;
  }

  /** инициализировать виджеты прогрессы сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setProgressBar(SceneRecycleDataModel item) {
    this.progressBar.setMax(item.scriptsTotal);
    this.progressBar.setProgress(item.scriptsFinished);
    setSceneIsDone(item);
    setScreenStepsValue(item);
  }

  /** инициализировать все поля холдера данными, а так же назначить порядковый номер сцены в списке
   * @param itemData - набор данных для инициализации сцены
   * @param position - текущая позиция холдера сцены в списке
   * */
  public void updateHolderByData(SceneRecycleDataModel itemData, int position){
    setTitle(itemData.title);
    setDescription(itemData.description);
    setIsMusicToggledFlag(itemData.isMusicStarted);
    setProgressBar(itemData);
    setHolderPosition(position);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public SceneAdapterHolder(View v, IRecycleAdapter screen) {
    super(v);
    this.screen = screen;

    title = v.findViewById(R.id.SCENE_TITLE_ID);
    description = v.findViewById(R.id.SCENE_DESCRIPTION_ID);
    screenStepsValue = v.findViewById(R.id.SCREEN_STEPS_VALUE_ID);

    body = v.findViewById(R.id.SCREEN_ACCORDION_BODY_ID);
    body.setVisibility(View.GONE);

    progressBar = v.findViewById(R.id.SCENE_PROGRESS_ID);

    isFinishedIcon = v.findViewById(R.id.SCREEN_IS_DONE_FLAG_ID);

    startScene = v.findViewById(R.id.SCENE_START_BTN_ID);
    startScene.setOnClickListener(startSceneListener);

    editBtn = v.findViewById(R.id.SCREEN_EDIT_BTN_ID);
    editBtn.setOnClickListener(editSceneListener);

    deleteBtn = v.findViewById(R.id.SCREEN_DELETE_BTN_ID);
    deleteBtn.setOnClickListener(deleteSceneListener);

    upOrderBtn = v.findViewById(R.id.SCREEN_UP_ORDER_BTN_ID);
    upOrderBtn.setOnClickListener(upOrderSceneListener);

    downOrderBtn = v.findViewById(R.id.SCREEN_DOWN_ORDER_BTN_ID);
    downOrderBtn.setOnClickListener(downOrderSceneListener);

    isMusicToggledFlag = v.findViewById(R.id.SCREEN_BACKGROUND_MUSIC_TOGGLER_ID);

    expandButton = v.findViewById(R.id.SCREEN_TOGGLER_ID);
    expandButton.setOnClickListener(toggleSceneListener);
  }

  View.OnClickListener toggleSceneListener = new View.OnClickListener() {
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

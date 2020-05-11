package com.example.masterhelper.commonAdapter.item;

import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.models.SceneRecycleDataModel;

/** Модель для управления интерфейсом внутри аккордиона для цеклического списка*/
public class SceneItem<Model> extends CommonItem<Model>{

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
  public void updateHolderByData(Model itemData, int position){
    SceneRecycleDataModel scene = (SceneRecycleDataModel) itemData;
    setTitle(scene.title);
    setDescription(scene.description);
    setIsMusicToggledFlag(scene.isMusicStarted);
    setProgressBar(scene);
    setPosition(position);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public SceneItem(View v, CommonAdapter<Model> adapter) {
    super(v, adapter);

    title = v.findViewById(R.id.SCENE_TITLE_ID);
    description = v.findViewById(R.id.SCENE_DESCRIPTION_ID);
    screenStepsValue = v.findViewById(R.id.SCREEN_STEPS_VALUE_ID);

    body = v.findViewById(R.id.SCREEN_ACCORDION_BODY_ID);
    body.setVisibility(View.GONE);

    progressBar = v.findViewById(R.id.SCENE_PROGRESS_ID);

    isFinishedIcon = v.findViewById(R.id.SCREEN_IS_DONE_FLAG_ID);

    startScene = v.findViewById(R.id.SCENE_START_BTN_ID);
    startScene.setOnClickListener(commonListener);

    editBtn = v.findViewById(R.id.SCENE_EDIT_BTN_ID);
    editBtn.setOnClickListener(commonListener);

    deleteBtn = v.findViewById(R.id.SCENE_DELETE_BTN_ID);
    deleteBtn.setOnClickListener(commonListener);

    upOrderBtn = v.findViewById(R.id.SCENE_UP_ORDER_BTN_ID);
    upOrderBtn.setOnClickListener(commonListener);

    downOrderBtn = v.findViewById(R.id.SCENE_DOWN_ORDER_BTN_ID);
    downOrderBtn.setOnClickListener(commonListener);

    isMusicToggledFlag = v.findViewById(R.id.SCREEN_BACKGROUND_MUSIC_TOGGLER_ID);

    expandButton = v.findViewById(R.id.SCREEN_TOGGLER_ID);
    expandButton.setOnClickListener(itemToggle);
  }

  View.OnClickListener itemToggle =  new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      toggleVisibility(body);
    }
  };

}

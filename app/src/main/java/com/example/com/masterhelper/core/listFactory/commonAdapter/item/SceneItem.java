package com.example.com.masterhelper.core.listFactory.commonAdapter.item;

import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageButton;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.listFactory.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.appconfig.models.SceneModel;

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

  /** блок заголовка аккордиона */
  private LinearLayout titleBar;

  /** виджет прогресса по сцене, виден всегда */
  private ProgressBar progressBar;

  /** иконка окончания сцены, появляется в заголовке аккордиона */
  private ImageView isFinishedIcon;

  /** Кнопка переключения состояния аккордиона */
  public AppCompatImageButton expandButton;

  /** Кнопка запуска сцены */
  public AppCompatImageButton startScene;

  /** кнопка редактирования сцены */
  public AppCompatImageButton editBtn;

  /** кнопка удаления сцены */
  public AppCompatImageButton deleteBtn;

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
    if(description.length() > 0){
      this.description.setText(description);
      this.description.setVisibility(View.VISIBLE);
    } else {
      this.description.setVisibility(View.GONE);
    }

  }

  /** указать текущее количество пройденых сцен
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setScreenStepsValue(SceneModel item) {
    String scripts = item.scriptsFinished +"/"+ item.scriptsTotal;
    this.screenStepsValue.setText(scripts);
  }

  /** указать состояние флага пройденной сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setSceneIsDone(SceneModel item) {
    if(item.scriptsTotal <= item.scriptsFinished && item.scriptsTotal != 0) {
      this.isFinishedIcon.setVisibility(View.VISIBLE);
      titleBar.setBackgroundResource(R.color.colorPrimaryDark);
    } else {
      this.isFinishedIcon.setVisibility(View.GONE);
      titleBar.setBackgroundResource( R.color.colorPrimary);
    }
  }

  /** инициализировать виджеты прогрессы сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setProgressBar(SceneModel item) {
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
    SceneModel scene = (SceneModel) itemData;
    setTitle((position+1) +": "+scene.title);
    setDescription(scene.description);
    setProgressBar(scene);
    setPosition(position);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public SceneItem(View v, CommonAdapter<Model> adapter) {
    super(v, adapter);

    title = v.findViewById(R.id.SCENE_TITLE_ID);
    description = v.findViewById(R.id.SCENE_DESCRIPTION_ID);
    screenStepsValue = v.findViewById(R.id.SCREEN_STEPS_VALUE_ID);

    titleBar = v.findViewById(R.id.SCENE_TITLE_BAR_ID);
    titleBar.setOnClickListener(itemToggle);

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

    expandButton = v.findViewById(R.id.SCREEN_TOGGLER_ID);
  }

  View.OnClickListener itemToggle =  new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      toggleVisibility(body);
      if(body.getVisibility() == View.VISIBLE){
        expandButton.setRotation(180);
      } else {
        expandButton.setRotation(0);
      }
    }
  };

}

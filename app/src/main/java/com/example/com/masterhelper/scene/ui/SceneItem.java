package com.example.com.masterhelper.scene.ui;

import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageButton;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.listFactory.commonAdapter.item.CommonItem;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.models.SceneModel;

/** Модель для управления интерфейсом внутри аккордиона для цеклического списка*/
public class SceneItem extends CommonItem {

  /** текстовое поле с описание сцены */
  private TextView description;

  /** блок заголовка аккордиона */
  private LinearLayout titleBar;

  /** Кнопка запуска сцены */
  public AppCompatImageButton startScene;

  /** кнопка редактирования сцены */
  public AppCompatImageButton editBtn;

  /** кнопка удаления сцены */
  public AppCompatImageButton deleteBtn;
  private LinearLayout body;
  private View expandButton;

  /** установить название сцены в виджет
   * @param title - имя сцены
   * */
  private void setTitle(String title) {
    TextView title1 = itemView.findViewById(R.id.SCENE_TITLE_ID);
    title1.setText(title);
  }

  /** передать описание сцены в виджет
   * @param description - описание сцены
   * */
  private void setDescription(String description) {
    if(description.length() > 0){
      this.description = itemView.findViewById(R.id.SCENE_DESCRIPTION_ID);
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
    String scripts = item.getSceneProgress();
    TextView screenStepsValue = itemView.findViewById(R.id.SCREEN_STEPS_VALUE_ID);
    screenStepsValue.setText(scripts);
  }

  /** указать состояние флага пройденной сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setSceneIsDone(SceneModel item) {
    ImageView isFinishedIcon = itemView.findViewById(R.id.SCREEN_IS_DONE_FLAG_ID);
    if(item.checkSceneIsFinished()) {
      isFinishedIcon.setVisibility(View.VISIBLE);
      titleBar.setBackgroundResource(R.color.colorPrimaryDark);
    } else {
      isFinishedIcon.setVisibility(View.GONE);
      titleBar.setBackgroundResource( R.color.colorPrimary);
    }
  }

  /** инициализировать виджеты прогрессы сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setProgressBar(SceneModel item) {
    titleBar = itemView.findViewById(R.id.SCENE_TITLE_BAR_ID);
    titleBar.setOnClickListener(itemToggle);
    ProgressBar progressBar = itemView.findViewById(R.id.SCENE_PROGRESS_ID);
    progressBar.setMax(item.getScriptsTotal());
    progressBar.setProgress(item.getScriptsFinished());
    setSceneIsDone(item);
    setScreenStepsValue(item);
  }

  /** инициализировать все поля холдера данными, а так же назначить порядковый номер сцены в списке
   * @param itemData - набор данных для инициализации сцены
   * */
  public void updateHolderByData(DataModel itemData) {
    super.updateHolderByData(itemData);
    SceneModel scene = (SceneModel) itemData;
    setTitle(scene.getName());
    setDescription(scene.getDescription());
    setProgressBar(scene);
    setControls();

    body = itemView.findViewById(R.id.SCREEN_ACCORDION_BODY_ID);
    expandButton = itemView.findViewById(R.id.SCREEN_TOGGLER_ID);
    body.setVisibility(View.GONE);
  }

  public void setControls(){
    startScene = itemView.findViewById(R.id.SCENE_START_BTN_ID);
    startScene.setOnClickListener(commonListener);

    editBtn = itemView.findViewById(R.id.SCENE_EDIT_BTN_ID);
    editBtn.setOnClickListener(commonListener);

    deleteBtn = itemView.findViewById(R.id.SCENE_DELETE_BTN_ID);
    deleteBtn.setOnClickListener(commonListener);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public SceneItem() {}

  View.OnClickListener itemToggle = v -> {
    toggleVisibility(body);
    if(body.getVisibility() == View.VISIBLE){
      expandButton.setRotation(180);
    } else {
      expandButton.setRotation(0);
    }
  };

}

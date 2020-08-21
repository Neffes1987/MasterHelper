package com.example.com.masterhelper.scene.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.listFactory.commonAdapter.item.CommonItem;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.models.ScriptModel;

/** Модель для управления интерфейсом внутри аккордиона для цеклического списка*/
public class ScriptItem extends CommonItem {

  /** блок тела аккордиона, чтобы сворачивать разворачицвать его */
  private LinearLayout body;

  /** Кнопка переключения состояния аккордиона */
  private AppCompatImageButton expandButton;


  /** установить название сцены в виджет
   * @param title - имя сцены
   * */
  private void setTitle(String title) {
    TextView title1 = itemView.findViewById(R.id.SCRIPT_TITLE_ID);
    title1.setText(title);
  }

  /** передать описание сцены в виджет
   * @param description - описание сцены
   * */
  private void setDescription(String description) {
    TextView description1 = itemView.findViewById(R.id.SCRIPT_DESCRIPTION_ID);
    description1.setText(description);
  }

  /** указать состояние флага пройденной сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setSceneIsDone(ScriptModel item) {
    boolean isFinished = item.isFinished;
    ImageView isFinishedIcon = itemView.findViewById(R.id.IS_SCRIPT_DONE_FLAG_ID);
    isFinishedIcon.setVisibility(isFinished ? View.VISIBLE : View.GONE);
    LinearLayout titleBar = itemView.findViewById(R.id.SCRIPT_TITLE_BAR_ID);
    titleBar.setOnClickListener(itemToggle);
    titleBar.setBackgroundResource(!isFinished ? R.color.colorPrimary : R.color.colorPrimaryDark);
  }

  /** инициализировать все поля холдера данными, а так же назначить порядковый номер сцены в списке
   * @param itemData - набор данных для инициализации сцены
   * */
  public void updateHolderByData(DataModel itemData) {
    super.updateHolderByData(itemData);
    body = itemView.findViewById(R.id.ACCORDION_SCRIPT_BODY_ID);
    body.setVisibility(View.GONE);
    ScriptModel script = (ScriptModel) itemData;
    setTitle(script.getName());
    setDescription(script.getDescription());
    setSceneIsDone(script);
    setBattleButtonVisible(script.hasBattleActionIcon);
    setControls();
  }

  private void setBattleButtonVisible(boolean visible){
    AppCompatImageButton startScene = itemView.findViewById(R.id.SCRIPT_START_BTN_ID);
    startScene.setOnClickListener(commonListener);
    startScene.setVisibility(visible ? View.VISIBLE : View.GONE);

  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public ScriptItem() {}

  private void setControls(){
    View v = itemView;

    AppCompatImageButton editBtn = v.findViewById(R.id.SCRIPT_EDIT_BTN_ID);
    editBtn.setOnClickListener(commonListener);

    AppCompatImageButton deleteBtn = v.findViewById(R.id.SCRIPT_DELETE_BTN_ID);
    deleteBtn.setOnClickListener(commonListener);

    AppCompatImageButton doneBtn = v.findViewById(R.id.SCRIPT_BTN_DONE_ID);
    doneBtn.setOnClickListener(commonListener);

    expandButton = v.findViewById(R.id.SCRIPT_TOGGLER_ID);
    expandButton.setOnClickListener(itemToggle);
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

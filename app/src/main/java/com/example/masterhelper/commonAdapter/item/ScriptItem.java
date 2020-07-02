package com.example.masterhelper.commonAdapter.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.models.ScriptRecycleDataModel;

/** Модель для управления интерфейсом внутри аккордиона для цеклического списка*/
public class ScriptItem<Model> extends CommonItem<Model>{

  /** текстовое поле в с именем сцены */
  private TextView title;

  /** текстовое поле с описание сцены */
  private TextView description;

  /** блок тела аккордиона, чтобы сворачивать разворачицвать его */
  private LinearLayout body;

  /** блок шапки аккордиона */
  private LinearLayout titleBar;

  /** блок шапки аккордиона */
  private LinearLayout battleScriptBar;

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

  /** кнопка удаления сцены */
  public AppCompatImageButton doneBtn;

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

  /** указать состояние флага пройденной сцены
   * @param item - набор данных для инициализации сцены
   * - scriptsFinished - сколько скриптов выполнено
   * - scriptsTotal - сколько всего скриптов в сцене
   * */
  private void setSceneIsDone(ScriptRecycleDataModel item) {
    boolean isFinished = item.isFinished;
    this.isFinishedIcon.setVisibility(isFinished ? View.VISIBLE : View.GONE);
    titleBar.setBackgroundResource(!isFinished ? R.color.colorPrimary : R.color.colorPrimaryDark);
  }

  /** инициализировать все поля холдера данными, а так же назначить порядковый номер сцены в списке
   * @param itemData - набор данных для инициализации сцены
   * @param position - текущая позиция холдера сцены в списке
   * */
  public void updateHolderByData(Model itemData, int position){
    ScriptRecycleDataModel script = (ScriptRecycleDataModel) itemData;
    setTitle(script.title);
    setDescription(script.description);
    setPosition(position);
    setSceneIsDone(script);
    setBattleScriptBarVisible(script.hasBattleActionIcon);
  }

  private void setBattleScriptBarVisible(boolean visible){
    battleScriptBar.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  /** @constructor генератор указателей на элементы UI для адаптера */
  public ScriptItem(View v, CommonAdapter<Model> adapter) {
    super(v, adapter);

    title = v.findViewById(R.id.SCRIPT_TITLE_ID);

    titleBar = v.findViewById(R.id.SCRIPT_TITLE_BAR_ID);

    battleScriptBar = v.findViewById(R.id.SCRIPT_BATTLE_EVENT_WRAPPER_ID);

    description = v.findViewById(R.id.SCRIPT_DESCRIPTION_ID);

    body = v.findViewById(R.id.ACCORDION_SCRIPT_BODY_ID);
    body.setVisibility(View.GONE);

    isFinishedIcon = v.findViewById(R.id.IS_SCRIPT_DONE_FLAG_ID);

    startScene = v.findViewById(R.id.SCRIPT_START_BTN_ID);
    startScene.setOnClickListener(commonListener);

    editBtn = v.findViewById(R.id.SCRIPT_EDIT_BTN_ID);
    editBtn.setOnClickListener(commonListener);

    deleteBtn = v.findViewById(R.id.SCRIPT_DELETE_BTN_ID);
    deleteBtn.setOnClickListener(commonListener);

    doneBtn = v.findViewById(R.id.SCRIPT_BTN_DONE_ID);
    doneBtn.setOnClickListener(commonListener);

    expandButton = v.findViewById(R.id.SCRIPT_TOGGLER_ID);
    expandButton.setOnClickListener(itemToggle);
  }

  View.OnClickListener itemToggle =  new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      toggleVisibility(body);
    }
  };
}

package com.example.masterhelper.ui.recyclerViewFragment;

import com.example.masterhelper.models.SceneRecycleDataModel;
import com.example.masterhelper.models.ScriptRecycleDataModel;

import java.util.LinkedHashMap;


public interface IRecycleFragment {
  /** инициализировать фрагмент элементами списка Сцен */
  void updateSceneListAdapter(LinkedHashMap<Integer, SceneRecycleDataModel> data);

  /** инициализировать фрагмент элементами списка Скриптов */
  void updateScriptListAdapter(LinkedHashMap<Integer, ScriptRecycleDataModel> data);
}

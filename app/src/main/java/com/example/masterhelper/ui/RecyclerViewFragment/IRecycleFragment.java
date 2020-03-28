package com.example.masterhelper.ui.RecyclerViewFragment;

import com.example.masterhelper.ui.RecyclerViewFragment.models.scene.SceneRecycleDataModel;
import com.example.masterhelper.ui.RecyclerViewFragment.models.script.ScriptRecycleDataModel;

import java.util.HashMap;


public interface IRecycleFragment {
  /** инициализировать фрагмент элементами списка Сцен */
  void updateSceneListAdapter(HashMap<Integer, SceneRecycleDataModel> data);

  /** инициализировать фрагмент элементами списка Скриптов */
  void updateScriptListAdapter(HashMap<Integer, ScriptRecycleDataModel> data);
}

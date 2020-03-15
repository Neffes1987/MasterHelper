package RecyclerSceneViewFragment;


import RecyclerSceneViewFragment.adapters.RecycleSceneAdapter;
import RecyclerSceneViewFragment.model.RecycleSceneDataModel;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerSceneViewFragment extends Fragment {
  RecyclerView recyclerView;

  public RecyclerSceneViewFragment() { }

  public void updateListAdapter(HashMap<Integer, RecycleSceneDataModel> data){
    // use a linear layout manager
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    IRecycleSceneAdapter screenActivity = (IRecycleSceneAdapter) getActivity();
    RecycleSceneAdapter mAdapter = new RecycleSceneAdapter(data, screenActivity);
    recyclerView.setAdapter(mAdapter);
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_recycler_view, container, false);
    recyclerView = fr.findViewById(R.id.recycler_list_view);
    recyclerView.setHasFixedSize(true);
    return fr;
  }
}

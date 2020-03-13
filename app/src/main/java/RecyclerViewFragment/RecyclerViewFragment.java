package RecyclerViewFragment;


import android.os.Bundle;
import android.util.Log;
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
public class RecyclerViewFragment extends Fragment {
  RecycleListScreenFragmentData item = new RecycleListScreenFragmentData("title", "name", 10, 12, false, false);

  HashMap<Integer, RecycleListScreenFragmentData> data = new HashMap<>();


  public RecyclerViewFragment() {

    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_recycler_view, container, false);
    RecyclerView recyclerView = (RecyclerView) fr.findViewById(R.id.recycler_list_view);
    recyclerView.setHasFixedSize(true);

    // use a linear layout manager
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    data.put(0, item);
    data.put(1, item);
    data.put(2, item);
    data.put(3, item);

    // specify an adapter (see also next example)
    RecycleAdapter mAdapter = new RecycleAdapter(data);
    recyclerView.setAdapter(mAdapter);

    return fr;
  }

}

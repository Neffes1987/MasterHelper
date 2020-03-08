package ListFragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class ListScreenFragment extends Fragment {
  public String[] Defaults = new String[]{"Запись1", "Запись2", "Запись3"};

  public ListScreenFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_list_screen, container, false);


    //Заполняем список и назначаем обработчики событий
    ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Defaults);
    ListView listView = fragmentView.findViewById(R.id.screen_list_id);
    listView.setOnItemClickListener(onListItemPressed);
    listView.setAdapter(adapter);

    FloatingActionButton createBtn = fragmentView.findViewById(R.id.createItemBtn);
    createBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        IListFragmentInterface listener = (IListFragmentInterface) getActivity();
        listener.onCreateButtonPressed();
      }
    });



    return fragmentView;
  }

  AdapterView.OnItemClickListener onListItemPressed = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IListFragmentInterface listener = (IListFragmentInterface) getActivity();
        listener.onItemButtonPressed(id+"");
    }
  };
}

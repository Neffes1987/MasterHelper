package com.example.masterhelper.ListFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class ListScreenFragment extends Fragment {
  public String[] data = new String[]{"Запись1", "Запись2", "Запись3"};

  public ListScreenFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_list_screen, container, false);


    //Заполняем список и назначаем обработчики событий
    setListViewHandler(fragmentView);
    setCreateBtnHandler(fragmentView);
    setSearchStrHandlers(fragmentView);
    return fragmentView;
  }


  void setListViewHandler(View fragmentView){
    ListView listView = fragmentView.findViewById(R.id.screen_list_id);
    listView.setOnItemClickListener(onListItemPressed);
    updateListValues(fragmentView, data);
  }

  public void updateListValues(View fragmentView, String[] data){
    ListAdapter adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, data);
    ListView listView = fragmentView.findViewById(R.id.screen_list_id);
    listView.setAdapter(adapter);
  }

  void setCreateBtnHandler(View fragmentView){
    FloatingActionButton createBtn = fragmentView.findViewById(R.id.createItemBtn);
    createBtn.setOnClickListener(clickHandler);
  }

  void setSearchStrHandlers(View fragmentView){
    final EditText searchStr = fragmentView.findViewById(R.id.searchStr);
    searchStr.addTextChangedListener(watcher);
  }

  View.OnClickListener clickHandler = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      IListFragmentInterface listener = (IListFragmentInterface) getActivity();
      assert listener != null;
      listener.onCreateButtonPressed();
    }
  };

  TextWatcher watcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
      IListFragmentInterface listener = (IListFragmentInterface) getActivity();
      assert listener != null;
      listener.onSearchStringChanged(s.toString());
    }
  };

  AdapterView.OnItemClickListener onListItemPressed = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IListFragmentInterface listener = (IListFragmentInterface) getActivity();
      assert listener != null;
      listener.onItemButtonPressed(id+"");
    }
  };
}

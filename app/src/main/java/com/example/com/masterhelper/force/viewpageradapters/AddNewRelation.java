package com.example.com.masterhelper.force.viewpageradapters;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.com.masterhelper.core.factories.list.CustomListItemsEnum;
import com.example.com.masterhelper.core.factories.list.ListFactory;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.masterhelper.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IAddNewRelation} interface
 * to handle interaction events.
 * Use the {@link AddNewRelation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewRelation extends Fragment {

  private IAddNewRelation mListener;

  private ListFactory listFactory = new ListFactory();


  private int title;

  private ModelList list;

  public AddNewRelation(int title,  ModelList list) {
    this.title = title;
    this.list = list;
  }

  public static AddNewRelation newInstance(int title, ModelList list) {
    return new AddNewRelation(title, list);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fr = inflater.inflate(R.layout.fragment_add_new_relation, container, false);
    TextView caption = fr.findViewById(R.id.RELATION_BLOCK_TITLE_ID);
    caption.setText(title);
    listFactory.updateListAdapter(list, CustomListItemsEnum.relation);
    return fr;
  }

  public void onButtonPressed(View v) {
    if (mListener != null) {
      mListener.relationChanged(v);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof IAddNewRelation) {
      mListener = (IAddNewRelation) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement IAddNewRelation");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface IAddNewRelation {
    void relationChanged(View v);
  }
}

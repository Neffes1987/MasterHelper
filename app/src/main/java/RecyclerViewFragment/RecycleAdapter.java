package RecyclerViewFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;

import java.util.HashMap;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
  private HashMap<Integer, RecycleListScreenFragmentData> mDataset;
  private RecyclerViewFragment parent;

  // Provide a reference to the views for each data item
  // Complex data items may need more than one view per item, and
  // you provide access to all the views for a data item in a view holder
  public static class MyViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView title;
    public TextView description;
    public TextView screenStepsValue;

    public LinearLayout body;

    public ProgressBar progressBar;

    public Switch isMusicToggledFlag;

    public ImageView isFinishedIcon;

    public AppCompatImageButton expandButton;
    public AppCompatImageView startScene;
    public AppCompatImageButton editBtn;
    public AppCompatImageButton deleteBtn;
    public AppCompatImageButton upOrderBtn;
    public AppCompatImageButton upDownOrderBtn;

    public MyViewHolder(View v) {
      super(v);

      title = v.findViewById(R.id.itemTitle);
      description = v.findViewById(R.id.scene_description);
      screenStepsValue = v.findViewById(R.id.screen_steps_value);

      body = v.findViewById(R.id.accordion_body);
      body.setVisibility(View.GONE);

      progressBar = v.findViewById(R.id.scene_progress);

      isFinishedIcon = v.findViewById(R.id.isDoneFlag);

      expandButton = v.findViewById(R.id.itemToggler);
      startScene = v.findViewById(R.id.start_scene_btn);
      editBtn = v.findViewById(R.id.editItemBtn);
      deleteBtn = v.findViewById(R.id.deleteItemBtn);
      upOrderBtn = v.findViewById(R.id.upOrderBtn);
      upDownOrderBtn = v.findViewById(R.id.upDownOrderBtn);

      isMusicToggledFlag = v.findViewById(R.id.background_music_toggler);
    }
  }

  // Provide a suitable constructor (depends on the kind of dataset)
  public RecycleAdapter(HashMap<Integer, RecycleListScreenFragmentData> data, RecyclerViewFragment fragment) {
    mDataset = data;
    parent = fragment;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.expand_scene_list_item, parent, false);

    MyViewHolder vh = new MyViewHolder(v);
    return vh;
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(MyViewHolder holder, final int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element

    RecycleListScreenFragmentData itemData = mDataset.get(position);
    holder.title.setText(itemData.title);
    holder.description.setText(itemData.description);
    holder.isMusicToggledFlag.setChecked(itemData.isMusicStarted);

    int isDone = itemData.scriptsTotal - itemData.scriptsFinished == 0 ? View.VISIBLE : View.INVISIBLE;
    String scripts = itemData.scriptsFinished +"/"+ itemData.scriptsTotal;

    holder.isFinishedIcon.setVisibility(isDone);
    holder.progressBar.setMax(itemData.scriptsTotal);
    holder.progressBar.setProgress(itemData.scriptsFinished);
    holder.screenStepsValue.setText(scripts);

    holder.expandButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        parent.onChangeItem(position, "isExpand", "");
      }
    });
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    if(mDataset == null){
      return 0;
    }
    return mDataset.size();
  }
}

package RecyclerViewFragment;

public class RecycleListScreenFragmentData {
  public String title;
  public String description;

  public int scriptsTotal;
  public int scriptsFinished;

  public boolean isExpand;
  public boolean isMusicStarted;


  public RecycleListScreenFragmentData(String title, String description, int scriptsFinished, int scriptsTotal, boolean isExpand, boolean isMusicStarted ){
    this.title = title;
    this.description = description;
    this.scriptsFinished = scriptsFinished;
    this.scriptsTotal = scriptsTotal;
    this.isExpand = isExpand;
    this.isMusicStarted = isMusicStarted;
  }
}

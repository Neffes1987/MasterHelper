package com.example.masterhelper;

import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.masterhelper.ui.ScriptBottomButtonsFragment.ScriptBottomButtonsFragment;
import com.example.masterhelper.ui.viewCharacteristicRow.ViewCharacteristicRow;

public class ScriptTextDescriptionScreen extends AppCompatActivity  implements ScriptBottomButtonsFragment.IScriptBottomButtonsFragment, ViewCharacteristicRow.IViewCharacteristicRow {
  /** */
  int activityScriptTextDescriptionScreenLayout = R.layout.activity_script_text_description_screen;

  /** */
  int scriptDescriptionId = R.id.SCRIPT_DESCRIPTION_ID;
  TextView scriptDescription;

  String mockedText = "alsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsghalsjgijsfgij fijg;lsdfjg ;sldkfjg ;wlsjfgh ;sdf s;dflj s;ldfjh;l ksjdfhl js;ldkfj ;ldjfs ;sdjgfh;sd flhjdf hjls;dfkj hl;dsjfhl kjsl  sldkfjh l;kdjshgl;kjsd;lhg j;ldsjfhg l;kjdfshlkjdlghj sldkj gl;skjd;hlkjsdlkjhglsdkjgh ;lskdjgl;h jlskgjdhhl kjdlgkjhl;dkjglksjlkgjhslkgdjhslkdg lsjdg lkjslkdjgh dsgh";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityScriptTextDescriptionScreenLayout);
    scriptDescription = findViewById(scriptDescriptionId);
    scriptDescription.setText(mockedText);
  }

  @Override
  public void setScriptBottomButtonsAction(ScriptBottomButtonsFragment.ScriptBottomButtonsActions scriptBottomButtonsAction) {
    Log.i("TAG", "onFragmentInteraction: " + scriptBottomButtonsAction);
  }

  @Override
  public void changeValue(long id, int value) {

  }

  @Override
  public void deleteRow(long rowId) {

  }

  @Override
  public void addNewRow(String title) {

  }
}

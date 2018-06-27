package com.depromeet.onsong;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;

public class ChooseGenreActivity extends BaseActivity {
  @BindDrawable(R.drawable.gradient_ballad) Drawable gradientBallad;
  @BindDrawable(R.drawable.gradient_dance) Drawable gradientDance;
  @BindDrawable(R.drawable.gradient_hiphop) Drawable gradientHiphop;
  @BindDrawable(R.drawable.gradient_indie) Drawable gradientIndie;
  @BindDrawable(R.drawable.gradient_jazz) Drawable gradientJazz;
  @BindDrawable(R.drawable.gradient_pop) Drawable gradientPop;
  @BindDrawable(R.drawable.gradient_rnb) Drawable gradientRnb;
  @BindDrawable(R.drawable.gradient_rock) Drawable gradientRock;

  @BindView(R.id.layout_main) ConstraintLayout layoutMain;
  List<Drawable> drawableList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_choose_genre);

    initDrawables();
    rolling();
  }

  private void initDrawables() {
    drawableList = new ArrayList<>();

    drawableList.add(gradientBallad);
    drawableList.add(gradientDance);
    drawableList.add(gradientHiphop);
    drawableList.add(gradientIndie);
    drawableList.add(gradientJazz);
    drawableList.add(gradientPop);
    drawableList.add(gradientRnb);
    drawableList.add(gradientRock);
  }

  private void rolling() {
    Collections.shuffle(drawableList);
    layoutMain.setBackground(drawableList.get(0));
  }

}

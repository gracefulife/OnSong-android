package com.depromeet.onsong.genre;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

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
  @BindView(R.id.recycler_genre) RecyclerView recyclerGenre;

  List<Drawable> drawableList;
  List<String> genres;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_choose_genre);

    initVariables();
    initViews();
    initDrawables();
    rolling();
  }

  private void initVariables() {
    genres = Stream.of("Ballad", "HipHop", "Dance", "R&B Soul", "Pop", "Jazz", "Indie", "Rock")
        .collect(Collectors.toList());
  }

  private void initViews() {
    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
    layoutManager.setFlexDirection(FlexDirection.ROW);
    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
    layoutManager.setFlexWrap(FlexWrap.WRAP);
    recyclerGenre.setLayoutManager(layoutManager);

    GenreRecyclerAdapter genreRecyclerAdapter = new GenreRecyclerAdapter(genres);
    recyclerGenre.setAdapter(genreRecyclerAdapter);
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

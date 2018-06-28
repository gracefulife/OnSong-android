package com.depromeet.onsong.genre;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.groupon.grox.Store;

import java.util.Collections;

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

  Store<GenreState> genreStateStore;

  @Override protected int getLayoutRes() {
    return R.layout.activity_choose_genre;
  }

  @Override protected void initStore() {
    genreStateStore = new Store<>(new GenreState(
        Stream.of(
            new GenreState.GenreColorPair("Ballad", R.drawable.gradient_ballad),
            new GenreState.GenreColorPair("HipHop", R.drawable.gradient_hiphop),
            new GenreState.GenreColorPair("Dance", R.drawable.gradient_dance),
            new GenreState.GenreColorPair("R&B Soul", R.drawable.gradient_rnb),
            new GenreState.GenreColorPair("Pop", R.drawable.gradient_pop),
            new GenreState.GenreColorPair("Jazz", R.drawable.gradient_jazz),
            new GenreState.GenreColorPair("Indie", R.drawable.gradient_indie),
            new GenreState.GenreColorPair("Rock", R.drawable.gradient_rock))
            .collect(Collectors.toList()), 0
    ));
    Collections.shuffle(genreStateStore.getState().genres);
  }

  @Override protected void initView() {
    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
    layoutManager.setFlexDirection(FlexDirection.ROW);
    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
    layoutManager.setFlexWrap(FlexWrap.WRAP);
    recyclerGenre.setLayoutManager(layoutManager);

    GenreRecyclerAdapter genreRecyclerAdapter = new GenreRecyclerAdapter(genreStateStore);
    recyclerGenre.setAdapter(genreRecyclerAdapter);
  }

  @Override protected void subscribeStore() {
    genreStateStore.subscribe(state -> layoutMain.setBackground(
        ContextCompat.getDrawable(this, state.genres.get(state.selected).second)
    ));
  }
}

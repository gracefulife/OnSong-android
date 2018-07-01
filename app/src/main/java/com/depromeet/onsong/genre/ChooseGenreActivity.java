package com.depromeet.onsong.genre;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.playlist.PlaylistActivity;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.groupon.grox.Store;

import java.util.Collections;

import butterknife.BindView;

import static com.depromeet.onsong.utils.SpannableUtils.toSpannable;
import static com.depromeet.onsong.utils.TransitionUtils.transitionOnBackground;

public class ChooseGenreActivity extends BaseActivity {
  @BindView(R.id.layout_main) ConstraintLayout layoutMain;
  @BindView(R.id.text_want) TextView textWant;
  @BindView(R.id.recycler_genre) RecyclerView recyclerGenre;
  @BindView(R.id.image_next) ImageView imageNext;

  Store<GenreState> genreStateStore;

  GenreRecyclerAdapter genreRecyclerAdapter;

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
    String title = getString(R.string.genre_title);
    textWant.setText(
        toSpannable(this, title, "fonts/NotoSansKR-Bold-Hestia.otf",
            TextUtils.indexOf(title, "취향"),
            TextUtils.indexOf(title, "을")
        ),
        TextView.BufferType.SPANNABLE
    );

    FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
    layoutManager.setFlexDirection(FlexDirection.ROW);
    layoutManager.setJustifyContent(JustifyContent.FLEX_START);
    layoutManager.setFlexWrap(FlexWrap.WRAP);
    recyclerGenre.setLayoutManager(layoutManager);
    recyclerGenre.setOverScrollMode(View.OVER_SCROLL_NEVER);

    genreRecyclerAdapter = new GenreRecyclerAdapter(genreStateStore);
    recyclerGenre.setAdapter(genreRecyclerAdapter);

    imageNext.setOnClickListener(v ->
        startActivity(PlaylistActivity.intent(this,
            genreStateStore.getState().genres
                .get(genreStateStore.getState().chosen))
        )
    );
  }

  @Override protected void subscribeStore() {
    genreStateStore.subscribe(state -> {
      transitionOnBackground(
          layoutMain,
          ContextCompat.getDrawable(this, R.color.genreContentsAccent),
          ContextCompat.getDrawable(this, state.genres.get(state.chosen).genreDrawableRes)
      );
      genreRecyclerAdapter.notifyDataSetChanged();
    });
  }
}

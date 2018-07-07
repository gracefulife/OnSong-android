package com.depromeet.onsong.home;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.domain.Music;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.groupon.grox.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.depromeet.onsong.home.CuratedMusicState.CATEGORIZED_NEW_ARTIST;
import static com.depromeet.onsong.home.CuratedMusicState.CATEGORIZED_POPULAR;
import static com.depromeet.onsong.home.CuratedMusicState.CURATED_FAVORITE;
import static com.depromeet.onsong.home.CuratedMusicState.CURATED_FOR_YOU;

public class HomeActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {
  @BindView(R.id.recycler_curated) RecyclerView recyclerCurated;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.line_curated_for_you) View lineCuratedForYou;
  @BindView(R.id.text_curated_for_you) TextView textCuratedForYou;
  @BindView(R.id.text_curated_favorite) TextView textCuratedFavorite;
  @BindView(R.id.layout_curated) ConstraintLayout layoutCurated;
  @BindView(R.id.line_categorized_for_you) View lineCategorizedForYou;
  @BindView(R.id.text_categorized_popular) TextView textCategorizedPopular;
  @BindView(R.id.text_categorized_new_artist) TextView textCategorizedNewArtist;
  @BindView(R.id.recycler_categorized) RecyclerView recyclerCategorized;
  @BindView(R.id.layout_categorized) ConstraintLayout layoutCategorized;
  @BindView(R.id.line_on_live_for_you) View lineOnLiveForYou;
  @BindView(R.id.text_on_live_title) TextView textOnLiveTitle;
  @BindView(R.id.image_next) ImageView imageNext;
  @BindView(R.id.recycler_on_live) RecyclerView recyclerOnLive;
  @BindView(R.id.layout_on_live) ConstraintLayout layoutOnLive;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.nav_view) NavigationView navView;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

  CuratedRecyclerAdapter curatedRecyclerAdapter;
  SnapHelper curatedRecyclerSnapHelper;
  CategorizedRecyclerAdapter categorizedRecyclerAdapter;

  Store<CuratedMusicState> curatedMusicStateStore;

  @Override protected int getLayoutRes() {
    return R.layout.activity_home;
  }

  @Override protected void initStore() {
    List<CuratedMusicState.CategoryMusicsPair> curatedMusics = new ArrayList<>();
    curatedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CURATED_FOR_YOU, Stream.of(
        new Music("Whatever", "Ugly Duck", "HipHop", "", 60),
        new Music("So what", "Beenzino", "HipHop", "", 60),
        new Music("Seventeen", "Rich Brian", "HipHop", "", 60),
        new Music("XXX", "Kendrick Lamar", "HipHop", "", 60)
    ).collect(Collectors.toList())));
    curatedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CURATED_FAVORITE, Stream.of(
        new Music("Whatever", "Ugly Duck", "HipHop", "", 60),
        new Music("So what", "Beenzino", "HipHop", "", 60),
        new Music("Seventeen", "Rich Brian", "HipHop", "", 60),
        new Music("XXX", "Kendrick Lamar", "HipHop", "", 60)
    ).collect(Collectors.toList())));
    List<CuratedMusicState.CategoryMusicsPair> categorizedMusics = new ArrayList<>();
    categorizedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CATEGORIZED_POPULAR, Stream.of(
        new Music("Whatever2", "Ugly Duck", "HipHop", "", 60),
        new Music("So what2", "Beenzino", "HipHop", "", 60),
        new Music("Seventeen2", "Rich Brian", "HipHop", "", 60)
    ).collect(Collectors.toList())));
    categorizedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CATEGORIZED_NEW_ARTIST, Stream.of(
        new Music("Whatever2", "Ugly Duck", "HipHop", "", 60),
        new Music("So what2", "Beenzino", "HipHop", "", 60),
        new Music("XXX2", "Kendrick Lamar", "HipHop", "", 60)
    ).collect(Collectors.toList())));

    curatedMusicStateStore = new Store<>(
        new CuratedMusicState(
            curatedMusics, categorizedMusics,
            new CuratedMusicState.ChosenCategoryPair(CURATED_FOR_YOU, CATEGORIZED_POPULAR)
        )
    );
  }

  @Override protected void initTitle() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setElevation(0);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

    toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.black22));

    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override protected void initView() {
    // fab
    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show());

    // curated
    LinearLayoutManager curatedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    recyclerCurated.setLayoutManager(curatedLayoutManager);
    recyclerCurated.setOverScrollMode(View.OVER_SCROLL_NEVER);

    curatedRecyclerSnapHelper = new LinearSnapHelper();
    curatedRecyclerSnapHelper.attachToRecyclerView(recyclerCurated);

    curatedRecyclerAdapter = new CuratedRecyclerAdapter(curatedMusicStateStore);
    recyclerCurated.setAdapter(curatedRecyclerAdapter);

    textCuratedForYou.setOnClickListener(v -> curatedMusicStateStore.dispatch(oldState -> new CuratedMusicState(
        oldState.curatedMusics, oldState.categorizedMusics,
        new CuratedMusicState.ChosenCategoryPair(CURATED_FOR_YOU, oldState.chosenCategoryPair.categorized)
    )));
    textCuratedFavorite.setOnClickListener(v -> curatedMusicStateStore.dispatch(oldState -> new CuratedMusicState(
        oldState.curatedMusics, oldState.categorizedMusics,
        new CuratedMusicState.ChosenCategoryPair(CURATED_FAVORITE, oldState.chosenCategoryPair.categorized)
    )));

    // categorized
    LinearLayoutManager categorizedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    recyclerCategorized.setLayoutManager(categorizedLayoutManager);
    recyclerCategorized.setOverScrollMode(View.OVER_SCROLL_NEVER);

    categorizedRecyclerAdapter = new CategorizedRecyclerAdapter(curatedMusicStateStore);
    recyclerCategorized.setAdapter(categorizedRecyclerAdapter);

    textCategorizedPopular.setOnClickListener(v -> curatedMusicStateStore.dispatch(oldState -> new CuratedMusicState(
        oldState.curatedMusics, oldState.categorizedMusics,
        new CuratedMusicState.ChosenCategoryPair(oldState.chosenCategoryPair.curated, CATEGORIZED_POPULAR)
    )));
    textCategorizedNewArtist.setOnClickListener(v -> curatedMusicStateStore.dispatch(oldState -> new CuratedMusicState(
        oldState.curatedMusics, oldState.categorizedMusics,
        new CuratedMusicState.ChosenCategoryPair(oldState.chosenCategoryPair.curated, CATEGORIZED_NEW_ARTIST)
    )));
  }

  @Override protected void subscribeStore() {
    curatedMusicStateStore.subscribe(state -> {
      curatedRecyclerAdapter.notifyDataSetChanged();

      // TODO 텍스트 알파 50%아님. 조정 필요
      CuratedMusicState.ChosenCategoryPair pair = state.chosenCategoryPair;
      textCuratedForYou.setTextColor(ContextCompat.getColor(this,
          CURATED_FOR_YOU.equals(pair.curated) ? R.color.black22 : R.color.blackA50R22
      ));
      textCuratedFavorite.setTextColor(ContextCompat.getColor(this,
          CURATED_FAVORITE.equals(pair.curated) ? R.color.black22 : R.color.blackA50R22
      ));
      textCategorizedPopular.setTextColor(ContextCompat.getColor(this,
          CATEGORIZED_POPULAR.equals(pair.categorized) ? R.color.black22 : R.color.blackA50R22
      ));
      textCategorizedNewArtist.setTextColor(ContextCompat.getColor(this,
          CATEGORIZED_NEW_ARTIST.equals(pair.categorized) ? R.color.black22 : R.color.blackA50R22
      ));
    });
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.home, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}

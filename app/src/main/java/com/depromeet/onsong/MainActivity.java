package com.depromeet.onsong;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
  @BindDrawable(R.drawable.gradient_ballad) Drawable gradientBallad;
  @BindDrawable(R.drawable.gradient_dance) Drawable gradientDance;
  @BindDrawable(R.drawable.gradient_hiphop) Drawable gradientHiphop;
  @BindDrawable(R.drawable.gradient_indie) Drawable gradientIndie;
  @BindDrawable(R.drawable.gradient_jazz) Drawable gradientJazz;
  @BindDrawable(R.drawable.gradient_pop) Drawable gradientPop;
  @BindDrawable(R.drawable.gradient_rnb) Drawable gradientRnb;
  @BindDrawable(R.drawable.gradient_rock) Drawable gradientRock;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.layout_main) CoordinatorLayout layoutMain;

  List<Drawable> drawableList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    fab.setOnClickListener(view -> Snackbar.make(view, "색 변경", Snackbar.LENGTH_LONG)
        .setAction("Action", v -> rolling()).show());

    initDrawables();
    rolling();
  }

  private void rolling() {
    Collections.shuffle(drawableList);
    layoutMain.setBackground(drawableList.get(0));
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

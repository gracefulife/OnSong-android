package com.depromeet.onsong;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {
  protected CompositeDisposable compositeDisposable;

  protected abstract @LayoutRes int getLayoutRes();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutRes());
    compositeDisposable = new CompositeDisposable();

    initStore();
    initView();
    subscribeStore();
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  protected abstract void initStore();

  protected abstract void initView();

  protected abstract void subscribeStore();

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (compositeDisposable != null) {
      compositeDisposable.dispose();
    }
  }
}

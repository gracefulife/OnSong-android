package com.depromeet.onsong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {
  protected CompositeDisposable compositeDisposable;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    compositeDisposable = new CompositeDisposable();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (compositeDisposable != null) {
      compositeDisposable.dispose();
    }
  }

  @Override public void setContentView(View view) {
    super.setContentView(view);
    ButterKnife.bind(this);
  }
}

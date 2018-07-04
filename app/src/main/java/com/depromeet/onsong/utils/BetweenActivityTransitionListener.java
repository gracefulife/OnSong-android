package com.depromeet.onsong.utils;

import android.transition.Transition;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BetweenActivityTransitionListener implements Transition.TransitionListener {
  private final Runnable callback;

  @Override
  public void onTransitionEnd(Transition transition) {
    callback.run();
    transition.removeListener(this);
  }

  @Override
  public void onTransitionStart(Transition transition) {
  }

  @Override
  public void onTransitionCancel(Transition transition) {
    transition.removeListener(this);
  }

  @Override
  public void onTransitionPause(Transition transition) {
  }

  @Override
  public void onTransitionResume(Transition transition) {
  }
}

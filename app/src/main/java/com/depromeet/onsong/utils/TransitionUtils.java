package com.depromeet.onsong.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.ViewGroup;

public abstract class TransitionUtils {
  public static void transitionOnBackground(ViewGroup target, Drawable defaultOrCurrentDrawable, Drawable nextDrawable) {
    Drawable currentDrawable = target.getBackground() == null ?
        defaultOrCurrentDrawable : target.getBackground();

    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{
        currentDrawable, nextDrawable
    });
    transitionDrawable.setCrossFadeEnabled(true);
    target.setBackground(transitionDrawable);
    transitionDrawable.startTransition(400);
  }
}

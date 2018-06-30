package com.depromeet.onsong.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import io.github.inflationx.calligraphy3.CalligraphyTypefaceSpan;
import io.github.inflationx.calligraphy3.TypefaceUtils;

public abstract class SpannableUtils {

  public static Spannable toSpannable(String text, int color, int start, int end) {
    SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(text);
    spannableBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableBuilder;
  }

  public static Spannable toSpannable(Context context, String text, String typefaceUrl, int start, int end) {
    SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(text);
    CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), typefaceUrl));
    spannableBuilder.setSpan(typefaceSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableBuilder;
  }
}

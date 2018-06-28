package com.depromeet.onsong.genre;

import android.support.annotation.DrawableRes;
import android.util.Pair;

import com.annimon.stream.Optional;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class GenreState {
  public final List<GenreColorPair> genres;
  public final int selected;

  public static GenreState choose(List<GenreColorPair> genres, int selected) {
    return new GenreState(genres, selected);
  }

  @ToString
  @EqualsAndHashCode
  @AllArgsConstructor
  public static class GenreColorPair {
    public final String first;
    public final Integer second;
  }
}

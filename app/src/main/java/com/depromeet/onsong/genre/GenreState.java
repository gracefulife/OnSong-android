package com.depromeet.onsong.genre;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class GenreState {
  public final List<GenreColorPair> genres;
  public final int chosen;

  public static GenreState choose(List<GenreColorPair> genres, int chosen) {
    return new GenreState(genres, chosen);
  }

  @ToString
  @EqualsAndHashCode
  @AllArgsConstructor
  public static class GenreColorPair implements Serializable {
    public final String genreName;
    public final Integer genreDrawableRes;
  }
}

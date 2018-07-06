package com.depromeet.onsong.home;

import com.depromeet.onsong.domain.Music;

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
public class CuratedMusicState {
  public static final String CURATED_FOR_YOU = "for_you";
  public static final String CURATED_FAVORITE = "favorite";
  public static final String CATEGORIZED_POPULAR = "popular";
  public static final String CATEGORIZED_NEW_ARTIST = "new_artist";

  public final List<CategoryMusicsPair> curatedMusics;
  public final List<CategoryMusicsPair> categorizedMusics;
  public final ChosenCategoryPair chosenCategoryPair;

  public static CuratedMusicState choose(List<CategoryMusicsPair> curatedMusics,
                                         List<CategoryMusicsPair> categorizedMusics,
                                         ChosenCategoryPair chosenCategoryPair) {
    return new CuratedMusicState(curatedMusics, categorizedMusics, chosenCategoryPair);
  }

  @EqualsAndHashCode
  @AllArgsConstructor
  public static class CategoryMusicsPair implements Serializable {
    public final String category;
    public final List<Music> musics;
  }

  @EqualsAndHashCode
  @AllArgsConstructor
  public static class ChosenCategoryPair implements Serializable {
    public final String curated;
    public final String categorized;
  }
}

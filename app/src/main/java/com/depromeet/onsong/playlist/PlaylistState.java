package com.depromeet.onsong.playlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class PlaylistState {
  public static final String SCROLL_BY_SELECTION = "selection";
  public static final String SCROLL_BY_SNAP = "snap";
  public static final String SCROLL_BY_EMPTY = "";

  public final List<Music> musics;
  public final int chosen;
  public final String scrollBy;

  public static PlaylistState choose(List<Music> musics, int chosen, String scrollBy) {
    return new PlaylistState(musics, chosen, scrollBy);
  }
}

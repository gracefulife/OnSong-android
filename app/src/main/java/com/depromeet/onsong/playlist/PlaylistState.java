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
  public final List<Music> musics;
  public final int chosen;

  public static PlaylistState choose(List<Music> musics, int chosen) {
    return new PlaylistState(musics, chosen);
  }
}

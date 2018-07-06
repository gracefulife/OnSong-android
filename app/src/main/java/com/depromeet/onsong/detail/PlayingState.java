package com.depromeet.onsong.detail;

import com.depromeet.onsong.playlist.Music;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class PlayingState {
  public final Music music;
  public final boolean isPlaying;
  public final boolean isLike;
  public final String scrollBy;

//  public static PlayingState choose(List<Music> musics, int chosen, String scrollBy) {
//    return new PlayingState(musics, chosen, scrollBy);
//  }
}

package com.depromeet.onsong.home;

import com.depromeet.onsong.domain.LiveMusic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Grox + 불변 데이터 형식을 사용하므로,
 * 데이터를 외부에 공개해도 상관없다.
 */
@ToString
@AllArgsConstructor
public class LiveMusicState {
  public final List<LiveMusic> liveMusics;

  public static LiveMusicState choose(List<LiveMusic> liveMusics) {
    return new LiveMusicState(liveMusics);
  }
}

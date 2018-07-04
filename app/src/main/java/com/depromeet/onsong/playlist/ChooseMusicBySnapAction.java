package com.depromeet.onsong.playlist;

import com.groupon.grox.Action;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChooseMusicBySnapAction implements Action<PlaylistState> {
  private final int chosen;

  @Override
  public PlaylistState newState(PlaylistState oldState) {
    return PlaylistState.choose(oldState.musics, chosen, PlaylistState.SCROLL_BY_SNAP);
  }
}

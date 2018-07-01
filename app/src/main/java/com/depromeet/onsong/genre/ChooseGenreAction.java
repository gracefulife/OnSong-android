package com.depromeet.onsong.genre;

import com.groupon.grox.Action;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChooseGenreAction implements Action<GenreState> {
  private final int chosen;

  @Override
  public GenreState newState(GenreState oldState) {
    return GenreState.choose(oldState.genres, chosen);
  }
}

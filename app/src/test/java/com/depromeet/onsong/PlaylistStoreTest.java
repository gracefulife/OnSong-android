/*
 * Copyright (c) 2017, Groupon, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.depromeet.onsong;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.depromeet.onsong.playlist.ChooseMusicAction;
import com.depromeet.onsong.playlist.Music;
import com.depromeet.onsong.playlist.PlaylistState;
import com.groupon.grox.Store;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlaylistStoreTest {

  @Test
  public void grox_스테이트동작_테스트() {
    //GIVEN
    Store<PlaylistState> store = new Store<>(
        new PlaylistState(
            Stream.of(
                new Music("Whatever", "Ugly Duck", "HipHop", "", 60),
                new Music("So what", "Beenzino", "HipHop", "", 60),
                new Music("Seventeen", "Rich Brian", "HipHop", "", 60),
                new Music("XXX", "Kendrick Lamar", "HipHop", "", 60)
            ).collect(Collectors.toList()), 0
        )
    );

    //WHEN
    PlaylistState state = store.getState();

    //THEN
    assertThat(state.chosen, is(0));
    assertThat(state.musics.get(state.chosen).title, is("Whatever"));
  }

  @Test
  public void grox_스테이트_디스패치_테스트() {
    //GIVEN
    Store<PlaylistState> store = new Store<>(
        new PlaylistState(
            Stream.of(
                new Music("Whatever", "Ugly Duck", "HipHop", "", 60),
                new Music("So what", "Beenzino", "HipHop", "", 60),
                new Music("Seventeen", "Rich Brian", "HipHop", "", 60),
                new Music("XXX", "Kendrick Lamar", "HipHop", "", 60)
            ).collect(Collectors.toList()), 0
        )
    );

    //WHEN
    store.dispatch(oldState -> new PlaylistState(oldState.musics, 1));
    PlaylistState state = store.getState();

    //THEN
    assertThat(state.chosen, is(1));
    assertThat(state.musics.get(state.chosen).title, is("So what"));
  }

  @Test
  public void grox_스테이트_액션_테스트() {
    //GIVEN
    Store<PlaylistState> store = new Store<>(
        new PlaylistState(
            Stream.of(
                new Music("Whatever", "Ugly Duck", "HipHop", "", 60),
                new Music("So what", "Beenzino", "HipHop", "", 60),
                new Music("Seventeen", "Rich Brian", "HipHop", "", 60),
                new Music("XXX", "Kendrick Lamar", "HipHop", "", 60)
            ).collect(Collectors.toList()), 0
        )
    );

    //WHEN
    store.dispatch(new ChooseMusicAction(1));
    PlaylistState state = store.getState();

    //THEN
    assertThat(state.chosen, is(1));
    assertThat(state.musics.get(state.chosen).title, is("So what"));
  }

}
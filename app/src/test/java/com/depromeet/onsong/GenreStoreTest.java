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
import com.depromeet.onsong.genre.ChooseGenreAction;
import com.depromeet.onsong.genre.GenreState;
import com.groupon.grox.Action;
import com.groupon.grox.Store;
import com.groupon.grox.Store.Middleware;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class StoreTest {

  @Test
  public void grox_스테이트동작_테스트() {
    //GIVEN
    Store<GenreState> store = new Store<>(
        new GenreState(
            Stream.of(new GenreState.GenreColorPair("ballad", 1), new GenreState.GenreColorPair("rnb", 2))
                .collect(Collectors.toList()), 0
        )
    );

    //WHEN
    GenreState state = store.getState();

    //THEN
    assertThat(state.selected, is(0));
    assertThat(state.genres.get(state.selected).first, is("ballad"));
  }

  @Test
  public void grox_스테이트_디스패치_테스트() {
    //GIVEN
    Store<GenreState> store = new Store<>(
        new GenreState(
            Stream.of(new GenreState.GenreColorPair("ballad", 1), new GenreState.GenreColorPair("rnb", 2))
                .collect(Collectors.toList()), 0
        )
    );

    //WHEN
    store.dispatch(oldState -> new GenreState(oldState.genres, 1));
    GenreState state = store.getState();

    //THEN
    assertThat(state.selected, is(1));
    assertThat(state.genres.get(state.selected).first, is("rnb"));
  }

  @Test
  public void grox_스테이트_액션_테스트() {
    //GIVEN
    Store<GenreState> store = new Store<>(
        new GenreState(
            Stream.of(new GenreState.GenreColorPair("ballad", 1), new GenreState.GenreColorPair("rnb", 2))
                .collect(Collectors.toList()), 0
        )
    );

    //WHEN
    store.dispatch(new ChooseGenreAction(1));
    GenreState state = store.getState();

    //THEN
    assertThat(state.selected, is(1));
    assertThat(state.genres.get(state.selected).first, is("rnb"));
  }

}

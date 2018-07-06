package com.depromeet.onsong;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.depromeet.onsong.domain.LiveMusic;
import com.depromeet.onsong.home.LiveMusicState;
import com.groupon.grox.Store;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LiveMusicStoreTest {

  @Test
  public void grox_스테이트동작_테스트() {
    //GIVEN
    Store<LiveMusicState> store = new Store<>(
        new LiveMusicState(
            Stream.of(
                new LiveMusic("Whatever", "Ugly Duck", "HipHop", "", "", 60),
                new LiveMusic("So what", "Beenzino", "HipHop", "", "", 60),
                new LiveMusic("Seventeen", "Rich Brian", "HipHop", "", "", 60),
                new LiveMusic("XXX", "Kendrick Lamar", "HipHop", "", "", 60)
            ).collect(Collectors.toList())
        )
    );

    //WHEN
    LiveMusicState state = store.getState();

    //THEN
    assertThat(state.liveMusics, is(Stream.of(
        new LiveMusic("Whatever", "Ugly Duck", "HipHop", "", "", 60),
        new LiveMusic("So what", "Beenzino", "HipHop", "", "", 60),
        new LiveMusic("Seventeen", "Rich Brian", "HipHop", "", "", 60),
        new LiveMusic("XXX", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));
  }

}

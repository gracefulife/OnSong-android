package com.depromeet.onsong;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.depromeet.media.domain.Music;
import com.depromeet.onsong.home.CuratedMusicState;
import com.groupon.grox.Store;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.depromeet.onsong.home.CuratedMusicState.CATEGORIZED_NEW_ARTIST;
import static com.depromeet.onsong.home.CuratedMusicState.CATEGORIZED_POPULAR;
import static com.depromeet.onsong.home.CuratedMusicState.CURATED_FAVORITE;
import static com.depromeet.onsong.home.CuratedMusicState.CURATED_FOR_YOU;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CuratedMusicStoreTest {

  @Test
  public void grox_스테이트동작_테스트() {
    //GIVEN
    List<CuratedMusicState.CategoryMusicsPair> curatedMusics = new ArrayList<>();
    curatedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CURATED_FOR_YOU, Stream.of(
        new Music("Whatever", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));
    curatedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CURATED_FAVORITE, Stream.of(
        new Music("Whatever", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));
    List<CuratedMusicState.CategoryMusicsPair> categorizedMusics = new ArrayList<>();
    categorizedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CATEGORIZED_POPULAR, Stream.of(
        new Music("Whatever2", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what2", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen2", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX2", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));
    categorizedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CATEGORIZED_NEW_ARTIST, Stream.of(
        new Music("Whatever2", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what2", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen2", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX2", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));

    Store<CuratedMusicState> store = new Store<>(
        new CuratedMusicState(
            curatedMusics, categorizedMusics,
            new CuratedMusicState.ChosenCategoryPair(CURATED_FOR_YOU, CATEGORIZED_NEW_ARTIST)
        )
    );

    //WHEN
    CuratedMusicState state = store.getState();

    //THEN
    assertThat(state.chosenCategoryPair, is(new CuratedMusicState.ChosenCategoryPair(CURATED_FOR_YOU, CATEGORIZED_NEW_ARTIST)));
    assertEquals(state.curatedMusics.get(0).musics.get(0), new Music("Whatever", "Ugly Duck", "HipHop", "", "", 60));
  }

  @Test
  public void grox_스테이트_디스패치_테스트() {
    //GIVEN
    List<CuratedMusicState.CategoryMusicsPair> curatedMusics = new ArrayList<>();
    curatedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CURATED_FOR_YOU, Stream.of(
        new Music("Whatever", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));
    curatedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CURATED_FAVORITE, Stream.of(
        new Music("Whatever", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));
    List<CuratedMusicState.CategoryMusicsPair> categorizedMusics = new ArrayList<>();
    categorizedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CATEGORIZED_POPULAR, Stream.of(
        new Music("Whatever2", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what2", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen2", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX2", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));
    categorizedMusics.add(new CuratedMusicState.CategoryMusicsPair(
        CATEGORIZED_NEW_ARTIST, Stream.of(
        new Music("Whatever2", "Ugly Duck", "HipHop", "", "", 60),
        new Music("So what2", "Beenzino", "HipHop", "", "", 60),
        new Music("Seventeen2", "Rich Brian", "HipHop", "", "", 60),
        new Music("XXX2", "Kendrick Lamar", "HipHop", "", "", 60)
    ).collect(Collectors.toList())));

    Store<CuratedMusicState> store = new Store<>(
        new CuratedMusicState(
            curatedMusics, categorizedMusics,
            new CuratedMusicState.ChosenCategoryPair(CURATED_FOR_YOU, CATEGORIZED_NEW_ARTIST)
        )
    );

    //WHEN
    store.dispatch(oldState -> new CuratedMusicState(oldState.curatedMusics, oldState.categorizedMusics,
        new CuratedMusicState.ChosenCategoryPair(CURATED_FAVORITE, CATEGORIZED_NEW_ARTIST)));
    CuratedMusicState state = store.getState();

    //THEN
    assertThat(state.chosenCategoryPair,
        is(new CuratedMusicState.ChosenCategoryPair(CURATED_FAVORITE, CATEGORIZED_NEW_ARTIST)));
  }
}

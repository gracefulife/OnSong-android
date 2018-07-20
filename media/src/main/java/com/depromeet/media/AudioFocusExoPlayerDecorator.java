package com.depromeet.media;

import android.media.AudioManager;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.media.AudioAttributesCompat;

import com.annimon.stream.Stream;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AudioFocusExoPlayerDecorator implements ExoPlayer {
  private static final String TAG = AudioFocusExoPlayerDecorator.class.getSimpleName();

  private static final Float MEDIA_VOLUME_DEFAULT = 1.0f;
  private static final Float MEDIA_VOLUME_DUCK = 0.2f;

  @NonNull private AudioAttributesCompat audioAttributesCompat;
  @NonNull private AudioManager audioManager;
  @NonNull private SimpleExoPlayer player;

  private List<Player.EventListener> eventListeners = new ArrayList<>();
  private boolean shouldPlayWhenReady = false;

  private AudioManager.OnAudioFocusChangeListener audioFocusListener
      = focusChange -> {
    switch (focusChange) {
      case AudioManager.AUDIOFOCUS_GAIN:
        if (shouldPlayWhenReady || player.getPlayWhenReady()) {
          player.setPlayWhenReady(true);
          player.setVolume(MEDIA_VOLUME_DEFAULT);
        }
        shouldPlayWhenReady = false;
        break;
      case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
        if (player.getPlayWhenReady()) {
          player.setVolume(MEDIA_VOLUME_DUCK);
        }
        break;
      case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
        // Save the current state of playback so the _intention_ to play can be properly
        // reported to the app.
        shouldPlayWhenReady = player.getPlayWhenReady();
        player.setPlayWhenReady(false);
        break;
      case AudioManager.AUDIOFOCUS_LOSS:
        // This will chain through to abandonAudioFocus().
        break;
    }
  };

  @Override public void setPlayWhenReady(boolean playWhenReady) {
    if (playWhenReady) {
      requestAudioFocus();
    } else {
      if (shouldPlayWhenReady) {
        shouldPlayWhenReady = false;
        playerEventListener.onPlayerStateChanged(false, player.getPlaybackState());
      }
      player.setPlayWhenReady(false);
      abandonAudioFocus();
    }
  }

  @Override public Looper getPlaybackLooper() {
    return null;
  }

  @Override public void prepare(MediaSource mediaSource) {

  }

  @Override
  public void prepare(MediaSource mediaSource, boolean resetPosition, boolean resetState) {

  }

  @Override public PlayerMessage createMessage(PlayerMessage.Target target) {
    return null;
  }

  @Override public void sendMessages(ExoPlayerMessage... messages) {

  }

  @Override public void blockingSendMessages(ExoPlayerMessage... messages) {

  }

  @Override public void setSeekParameters(@Nullable SeekParameters seekParameters) {

  }

  @Nullable @Override public VideoComponent getVideoComponent() {
    return null;
  }

  @Nullable @Override public TextComponent getTextComponent() {
    return null;
  }

  // TODO to set
  @Override public void addListener(Player.EventListener listener) {
    if (listener != null && !eventListeners.contains(listener)) {
      eventListeners.add(listener);
    }
  }

  @Override public void removeListener(Player.EventListener listener) {
    if (listener != null && eventListeners.contains(listener)) {
      eventListeners.remove(listener);
    }
  }

  @Override public int getPlaybackState() {
    return 0;
  }

  @Nullable @Override public ExoPlaybackException getPlaybackError() {
    return null;
  }

  @Override public boolean getPlayWhenReady() {
    return player.getPlayWhenReady() || shouldPlayWhenReady;
  }

  @Override public void setRepeatMode(int repeatMode) {

  }

  @Override public int getRepeatMode() {
    return 0;
  }

  @Override public void setShuffleModeEnabled(boolean shuffleModeEnabled) {

  }

  @Override public boolean getShuffleModeEnabled() {
    return false;
  }

  @Override public boolean isLoading() {
    return false;
  }

  @Override public void seekToDefaultPosition() {

  }

  @Override public void seekToDefaultPosition(int windowIndex) {

  }

  @Override public void seekTo(long positionMs) {

  }

  @Override public void seekTo(int windowIndex, long positionMs) {

  }

  @Override public void setPlaybackParameters(@Nullable PlaybackParameters playbackParameters) {

  }

  @Override public PlaybackParameters getPlaybackParameters() {
    return null;
  }

  @Override public void stop() {

  }

  @Override public void stop(boolean reset) {

  }

  @Override public void release() {

  }

  @Override public int getRendererCount() {
    return 0;
  }

  @Override public int getRendererType(int index) {
    return 0;
  }

  @Override public TrackGroupArray getCurrentTrackGroups() {
    return null;
  }

  @Override public TrackSelectionArray getCurrentTrackSelections() {
    return null;
  }

  @Nullable @Override public Object getCurrentManifest() {
    return null;
  }

  @Override public Timeline getCurrentTimeline() {
    return null;
  }

  @Override public int getCurrentPeriodIndex() {
    return 0;
  }

  @Override public int getCurrentWindowIndex() {
    return 0;
  }

  @Override public int getNextWindowIndex() {
    return 0;
  }

  @Override public int getPreviousWindowIndex() {
    return 0;
  }

  @Nullable @Override public Object getCurrentTag() {
    return null;
  }

  @Override public long getDuration() {
    return 0;
  }

  @Override public long getCurrentPosition() {
    return 0;
  }

  @Override public long getBufferedPosition() {
    return 0;
  }

  @Override public int getBufferedPercentage() {
    return 0;
  }

  @Override public boolean isCurrentWindowDynamic() {
    return false;
  }

  @Override public boolean isCurrentWindowSeekable() {
    return false;
  }

  @Override public boolean isPlayingAd() {
    return false;
  }

  @Override public int getCurrentAdGroupIndex() {
    return 0;
  }

  @Override public int getCurrentAdIndexInAdGroup() {
    return 0;
  }

  @Override public long getContentPosition() {
    return 0;
  }

  private void requestAudioFocus() {
    audioManager.requestAudioFocus(audioFocusListener,
        audioAttributesCompat.getLegacyStreamType(),
        AudioManager.AUDIOFOCUS_GAIN);
  }

  private void abandonAudioFocus() {
    audioManager.abandonAudioFocus(audioFocusListener);
  }

  // event delegation
  private Player.EventListener playerEventListener = new Player.EventListener() {

    @Override public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onTimelineChanged(timeline, manifest, reason));
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onTracksChanged(trackGroups, trackSelections));
    }

    @Override public void onLoadingChanged(boolean isLoading) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onLoadingChanged(isLoading));
    }

    @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onPlayerStateChanged(playWhenReady, playbackState));
    }

    @Override public void onRepeatModeChanged(int repeatMode) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onRepeatModeChanged(repeatMode));
    }

    @Override public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onShuffleModeEnabledChanged(shuffleModeEnabled));
    }

    @Override public void onPlayerError(ExoPlaybackException error) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onPlayerError(error));
    }

    @Override public void onPositionDiscontinuity(int reason) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onPositionDiscontinuity(reason));
    }

    @Override public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
      Stream.of(eventListeners).forEach(eventListener ->
          eventListener.onPlaybackParametersChanged(playbackParameters));
    }

    @Override public void onSeekProcessed() {
      Stream.of(eventListeners).forEach(Player.EventListener::onSeekProcessed);
    }
  };
}

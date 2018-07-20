package com.depromeet.onsong.home;

import android.widget.ImageView;
import android.widget.TextView;

import com.depromeet.media.domain.Music;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerKeyWithViewsMap implements Serializable { // naming
  private int position;
  private Music music;
  private ImageView imageAlbum;
  private TextView textMusicTitle;
  private TextView textMusicArtist;
}

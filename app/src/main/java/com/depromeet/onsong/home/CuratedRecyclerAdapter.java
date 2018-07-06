package com.depromeet.onsong.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.depromeet.onsong.R;
import com.depromeet.onsong.domain.Music;
import com.groupon.grox.Store;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CuratedRecyclerAdapter extends RecyclerView.Adapter<CuratedRecyclerAdapter.ViewHolder> {
  // Event provider. TODO dispose
  public final PublishSubject<Pair<Integer, View>> onItemClickedEventProvider = PublishSubject.create();

  @lombok.NonNull final Store<CuratedMusicState> curatedMusicStateStore;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curated, parent, false)
    );
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    List<CuratedMusicState.CategoryMusicsPair> musicsPairs = curatedMusicStateStore.getState().curatedMusics;
    // TODO for you or favorite
    List<Music> musics = musicsPairs.get(0).musics;

    int[] drawables = new int[]{
        R.drawable.img_album_01, R.drawable.img_album_02, R.drawable.img_album_03, R.drawable.img_album_04,
        R.drawable.img_album_05, R.drawable.img_album_06, R.drawable.img_album_07, R.drawable.img_album_08
    };

    Glide.with(holder.imageAlbum)
        .load(drawables[position % drawables.length])
        .into(holder.imageAlbum);

    holder.imageAlbum.setOnClickListener(v -> onItemClickedEventProvider.onNext(new Pair<>(position, holder.imageAlbum)));

    holder.textMusicTitle.setText(musics.get(position).title);
    holder.textMusicArtist.setText(musics.get(position).artist);
  }

  @Override public int getItemCount() {
    return curatedMusicStateStore.getState().curatedMusics.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_album) ImageView imageAlbum;
    @BindView(R.id.text_music_title) TextView textMusicTitle;
    @BindView(R.id.text_music_artist) TextView textMusicArtist;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

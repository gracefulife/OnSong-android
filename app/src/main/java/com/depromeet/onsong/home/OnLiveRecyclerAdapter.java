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
import com.depromeet.onsong.domain.LiveMusic;
import com.groupon.grox.Store;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnLiveRecyclerAdapter extends RecyclerView.Adapter<OnLiveRecyclerAdapter.ViewHolder> {
  // Event provider. TODO dispose
  public final PublishSubject<Pair<Integer, View>> onItemClickedEventProvider = PublishSubject.create();

  @lombok.NonNull final Store<LiveMusicState> liveMusicStateStore;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_live, parent, false)
    );
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    List<LiveMusic> musics = liveMusicStateStore.getState().liveMusics;

    int[] drawables = new int[]{
        R.drawable.img_live_01, R.drawable.img_live_02
    };

    Glide.with(holder.imageThumbnail)
        .load(drawables[position % drawables.length])
        .into(holder.imageThumbnail);

    holder.imageThumbnail.setOnClickListener(v -> onItemClickedEventProvider.onNext(new Pair<>(position, holder.imageThumbnail)));

    holder.textTitle.setText(musics.get(position).title);
    holder.textArtist.setText(musics.get(position).artist);
  }

  @Override public int getItemCount() {
    return liveMusicStateStore.getState().liveMusics.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_thumbnail) ImageView imageThumbnail;
    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.text_artist) TextView textArtist;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

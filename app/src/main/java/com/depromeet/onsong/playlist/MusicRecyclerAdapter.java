package com.depromeet.onsong.playlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.depromeet.onsong.R;
import com.groupon.grox.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MusicRecyclerAdapter extends RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder> {
  final Store<PlaylistState> playlistStateStore;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_cover, parent, false)
    );
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    int[] drawables = new int[]{
        R.drawable.img_album, R.drawable.img_album_02, R.drawable.img_album_03, R.drawable.img_album_04
    };

    Glide.with(holder.imageAlbum)
        .load(drawables[position % drawables.length])
        .into(holder.imageAlbum);
    // holder.imageAlbum.setOnClickListener(v -> playlistStateStore.dispatch(new ChooseMusicAction(position))); 클릭 + 스냅핑
  }

  @Override public int getItemCount() {
    return playlistStateStore.getState().musics.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_album) ImageView imageAlbum;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

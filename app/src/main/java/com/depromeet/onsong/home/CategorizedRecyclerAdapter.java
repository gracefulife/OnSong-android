package com.depromeet.onsong.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
public class CategorizedRecyclerAdapter extends RecyclerView.Adapter<CategorizedRecyclerAdapter.ViewHolder> {
  // Event provider. TODO dispose
  public final PublishSubject<PlayerKeyWithViewsMap> onItemClickedEventProvider = PublishSubject.create();

  @lombok.NonNull final Store<CuratedMusicState> curatedMusicStateStore;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorized, parent, false)
    );
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    List<CuratedMusicState.CategoryMusicsPair> musicsPairs = curatedMusicStateStore.getState().categorizedMusics;
    // TODO for you or favorite
    List<Music> musics = musicsPairs.get(1).musics;
    Music music = musics.get(0); // FIXME SELECTED MUSIC

    int[] drawables = new int[]{
        R.drawable.img_jayvito, R.drawable.img_meego, R.drawable.img_childdiahn
    };

    Glide.with(holder.imageProfile)
        .load(drawables[position % drawables.length])
        .into(holder.imageProfile);

    holder.imageProfile.setOnClickListener(v -> onItemClickedEventProvider.onNext(
        new PlayerKeyWithViewsMap(position, music, holder.imageProfile, holder.textMusicTitle, holder.textMusicArtist))
    );

    holder.textMusicTitle.setText(musics.get(position).title);
    holder.textMusicArtist.setText(musics.get(position).artist);
  }

  @Override public int getItemCount() {
    // TODO 이건 categorizedMusics 의 0번 (즉 POPULAR) 의 길이.
    // TODO NEW_ARTIST 등 선택이 바뀌었을때 길이 재반영 하도록
    return curatedMusicStateStore.getState().categorizedMusics.get(0).musics.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_profile) ImageView imageProfile;
    @BindView(R.id.text_music_title) TextView textMusicTitle;
    @BindView(R.id.text_music_artist) TextView textMusicArtist;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

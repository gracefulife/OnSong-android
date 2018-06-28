package com.depromeet.onsong.genre;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.depromeet.onsong.R;
import com.groupon.grox.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenreRecyclerAdapter extends RecyclerView.Adapter<GenreRecyclerAdapter.ViewHolder> {
  final Store<GenreState> genreStateStore;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false)
    );
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.textContents.setText(String.format("#%s", genreStateStore.getState().genres.get(position)));
  }

  @Override public int getItemCount() {
    return genreStateStore.getState().genres.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_contents) TextView textContents;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

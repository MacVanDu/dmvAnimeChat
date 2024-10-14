package com.example.dumvanimechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NVAnimeAdapter extends RecyclerView.Adapter<NVAnimeAdapter.NVAnimeViewHolder> {
    private List<NVAnime> NVAnimes;
    private OnCharacterClickListener onCharacterClickListener;

    public NVAnimeAdapter(List<NVAnime> NVAnimes, OnCharacterClickListener onCharacterClickListener) {
        this.NVAnimes = NVAnimes;
        this.onCharacterClickListener = onCharacterClickListener;
    }

    @NonNull
    @Override
    public NVAnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
        return new NVAnimeViewHolder(view, onCharacterClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NVAnimeViewHolder holder, int position) {
        NVAnime NVAnime = NVAnimes.get(position);
        holder.characterName.setText(NVAnime.getName());
        holder.characterAbout.setText(NVAnime.getAbout());
        Picasso.get().load(NVAnime.getImageUrl()).into(holder.characterImage);
    }

    @Override
    public int getItemCount() {
        return NVAnimes.size();
    }

    public class NVAnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView characterImage;
        TextView characterName;

        TextView characterAbout;
        OnCharacterClickListener onCharacterClickListener;

        public NVAnimeViewHolder(@NonNull View itemView, OnCharacterClickListener onCharacterClickListener) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.characterImage);
            characterName = itemView.findViewById(R.id.characterName);
            characterAbout = itemView.findViewById(R.id.characterAbout);
            this.onCharacterClickListener = onCharacterClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCharacterClickListener.onCharacterClick(NVAnimes.get(getAdapterPosition()));
        }
    }

    public interface OnCharacterClickListener {
        void onCharacterClick(NVAnime NVAnime);
    }
}


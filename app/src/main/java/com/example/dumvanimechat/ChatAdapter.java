package com.example.dumvanimechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.Gravity;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {
    private List<TinNhan> tinNhans;

    public ChatAdapter(List<TinNhan> tinNhans) {
        this.tinNhans = tinNhans;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        TinNhan tinNhan = tinNhans.get(position);
        holder.messageText.setText(tinNhan.getText());

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.messageText.getLayoutParams();

        if (tinNhan.isNguoi()) {
            holder.messageText.setBackgroundResource(R.drawable.user_message_background);
            layoutParams.gravity = Gravity.END;
            holder.loadingSpinner.setVisibility(View.GONE);
        } else {
            holder.messageText.setBackgroundResource(R.drawable.character_message_background);
            layoutParams.gravity = Gravity.START;
            if (tinNhan.getText() == null) {
                holder.loadingSpinner.setVisibility(View.VISIBLE);
                holder.messageText.setVisibility(View.GONE);
            } else {
                holder.loadingSpinner.setVisibility(View.GONE);
                holder.messageText.setVisibility(View.VISIBLE);
            }
        }

        holder.messageText.setLayoutParams(layoutParams);
    }



    @Override
    public int getItemCount() {
        return tinNhans.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ProgressBar loadingSpinner;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
            loadingSpinner = itemView.findViewById(R.id.loading_spinner);
        }
    }
}


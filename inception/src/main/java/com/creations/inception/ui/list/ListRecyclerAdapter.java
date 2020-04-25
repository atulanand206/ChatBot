package com.creations.inception.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creations.inception.R;
import com.creations.inception.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.RecyclerViewHolder> {

    private List<User> users;
    private Context context;
    private OnItemClickListener listener;

    public ListRecyclerAdapter(List<User> users, Context context, OnItemClickListener itemClickListener) {
        this.users = users;
        this.context = context;
        this.listener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        User item = users.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    interface OnItemClickListener {
        void onUserClicked(User user);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private RelativeLayout layout;

        private User item;
        private OnItemClickListener listener;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.user);
            layout = itemView.findViewById(R.id.card_user);
        }

        public void bind(User item, OnItemClickListener listener) {
            this.listener = listener;
            this.item = item;
            textView.setText(item.getUser());
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_user :
                    listener.onUserClicked(item);

            }
        }
    }
}

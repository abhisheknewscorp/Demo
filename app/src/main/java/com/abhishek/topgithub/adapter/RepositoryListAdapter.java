package com.abhishek.topgithub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.topgithub.R;
import com.abhishek.topgithub.model.Repository;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.RepoViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private List<Repository> repositories;
    private Context context;

    public RepositoryListAdapter(Context context, List<Repository> data, OnItemClickListener listener) {
        this.repositories = data;
        this.onItemClickListener = listener;
        this.context = context;
    }

    @Override
    public RepositoryListAdapter.RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        return new RepoViewHolder(view, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        String images = repositories.get(position).getAvatar();
        Glide.with(context)
                .load(images)
                .into(holder.avatar);
        holder.name.setText(repositories.get(position).getName());
        holder.username.setText(repositories.get(position).getUsername());
        holder.bind(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }


    public interface OnItemClickListener {
        void onClick(Repository Item);
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        protected ImageView avatar;
        @BindView(R.id.tv_name)
        protected TextView name;
        @BindView(R.id.tv_username)
        TextView username;

        private Repository repo;

        public RepoViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(repo);
                }
            });

        }

        public void bind(final Repository repository) {
            this.repo = repository;
        }
    }
}


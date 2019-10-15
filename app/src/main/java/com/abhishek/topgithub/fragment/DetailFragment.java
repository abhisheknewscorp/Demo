
package com.abhishek.topgithub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.topgithub.R;
import com.abhishek.topgithub.base.BaseFragment;
import com.abhishek.topgithub.model.Repository;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;

public class DetailFragment extends BaseFragment {

    private static final String REPOSITORY_DETAIL = "repository_detail";

    @BindView(R.id.iv_detail_avatar)
    protected ImageView avatarImageView;
    @BindView(R.id.tv_detail_name)
    protected TextView nameTextView;
    @BindView(R.id.tv_detail_username)
    protected TextView usernameTextView;
    @BindView(R.id.tv_detail_url)
    protected TextView urlTextView;
    @BindView(R.id.tv_repo_name)
    protected TextView repoNameTextView;
    @BindView(R.id.tv_repo_desc)
    protected TextView descriptionTextView;
    @BindView(R.id.tv_repo_url)
    protected TextView repoUrlTextView;

    public static DetailFragment getInstance(@NonNull Repository repository) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(REPOSITORY_DETAIL, new Gson().toJson(repository));
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_detail;
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Repository repository = new Gson().fromJson(bundle.getString(REPOSITORY_DETAIL), Repository.class);
            showDetails(repository);
        }
    }

    protected void showDetails(Repository repository) {
        Glide.with(getContext())
                .load(repository.getAvatar())
                .into(avatarImageView);
        nameTextView.setText(repository.getName());
        usernameTextView.setText(repository.getUsername());
        urlTextView.setText(repository.getUrl());
        repoNameTextView.setText(repository.getRepo().getName());
        descriptionTextView.setText(repository.getRepo().getDescription());
        repoUrlTextView.setText(repository.getRepo().getUrl());
    }
}

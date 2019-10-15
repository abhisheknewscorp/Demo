

package com.abhishek.topgithub.listener;

import com.abhishek.topgithub.model.Repository;

public interface RepoFragmentListener {

    void loadRepositoryList();

    void showRepositoryDetails(Repository item);
}


package com.abhishek.topgithub.listener;

import com.abhishek.topgithub.model.Repository;

import java.util.List;

public interface MainActivityPresenterListener {

    void displayProgress();

    void removeProgress();

    void onSuccess(List<Repository> repositories);

    void showEmpty();

    void onError();
}

package com.victor.training.graphql.presenter

import com.victor.training.graphql.data.DataManager
import com.victor.training.graphql.data.models.DomainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GitHubPresenter {

    interface GitHubView {
        fun onResponse(responseModel: DomainModel)
        fun onError(error: Throwable)
    }


    private var mGitHubView: GitHubView? = null
    private val mDataManager: DataManager = DataManager()
    private val mCompositeDisposable = CompositeDisposable()


    fun setView(gitHubView: GitHubView) {
        mGitHubView = gitHubView
    }

    fun getSomeInfo() {
        mCompositeDisposable.add(mDataManager
            .getSomeDataFromApi()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                mGitHubView?.onResponse(it)
            },{
                it.printStackTrace()
                mGitHubView?.onError(it)
            }))
    }

    fun destroy() {
        mGitHubView = null
        mCompositeDisposable.clear()
    }
}
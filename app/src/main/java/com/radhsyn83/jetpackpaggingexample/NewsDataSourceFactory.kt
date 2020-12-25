package com.radhsyn83.jetpackpaggingexample

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService)
    : DataSource.Factory<Int, NewsModel.Article>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, NewsModel.Article> {
        val newsDataSource = NewsDataSource(networkService, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}
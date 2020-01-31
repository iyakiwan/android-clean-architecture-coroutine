package io.fajarca.home.domain.repository

import androidx.paging.DataSource
import io.fajarca.core.database.NewsEntity
import io.fajarca.core.vo.Result
import io.fajarca.home.data.response.NewsDto
import io.fajarca.home.domain.entities.News

interface NewsRepository {
    suspend fun getNewsFromApi(country : String, page : Int, pageSize : Int) : Result<NewsDto>
    suspend fun getNewsFromDb() : List<NewsEntity>
    suspend fun getNews(page: Int, pageSize: Int, onSuccess : () -> Unit, onError : () -> Unit) : List<News>
    suspend fun insertNews(topHeadlines : List<NewsEntity>)
    suspend fun getTopHeadlines() : List<News>
    fun findAllNews() : DataSource.Factory<Int, NewsEntity>
}



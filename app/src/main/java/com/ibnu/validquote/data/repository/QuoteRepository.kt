package com.ibnu.validquote.data.repository

import com.ibnu.validquote.data.model.Quote
import com.ibnu.validquote.data.remote.service.ApiResponse
import com.ibnu.validquote.data.source.QuoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository @Inject constructor(
    private val quoteDataSource: QuoteDataSource
) {
    suspend fun getQuotes(
        category: String,
    ): Flow<ApiResponse<List<Quote>>> =
        quoteDataSource.fetchQuoteArticles(category).flowOn(Dispatchers.IO)
}
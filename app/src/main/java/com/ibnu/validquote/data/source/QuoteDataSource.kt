package com.ibnu.validquote.data.source

import com.ibnu.validquote.data.model.Quote
import com.ibnu.validquote.data.remote.service.ApiResponse
import com.ibnu.validquote.data.remote.service.QuoteService
import com.ibnu.validquote.utils.ConstValue.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteDataSource @Inject constructor(
    private val quoteService: QuoteService
) {

    suspend fun fetchQuoteArticles(
        category: String,
    ): Flow<ApiResponse<List<Quote>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = quoteService.fetchQuotes(apiKey = API_KEY, category = category, limit = 10)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }
}
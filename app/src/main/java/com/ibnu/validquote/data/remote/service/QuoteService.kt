package com.ibnu.validquote.data.remote.service

import com.ibnu.validquote.data.model.Quote
import retrofit2.http.*

interface QuoteService {

    @GET("quotes")
    suspend fun fetchQuotes(
        @Header("X-Api-Key") apiKey: String,
        @Query("category") category: String,
        @Query("limit") limit: Int,
    ): List<Quote>

}
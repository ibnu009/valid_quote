package com.ibnu.validquote.presentation.quote.adapter

import com.ibnu.validquote.data.model.Quote

interface QuoteItemHandler {
    fun navigateToDetail(quote: Quote)
}
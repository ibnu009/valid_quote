package com.ibnu.validquote.presentation.quote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.validquote.data.model.Quote
import com.ibnu.validquote.data.remote.service.ApiResponse
import com.ibnu.validquote.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    fun getQuotes(category: String): LiveData<ApiResponse<List<Quote>>> {
        val result = MutableLiveData<ApiResponse<List<Quote>>>()
        viewModelScope.launch {
            quoteRepository.getQuotes(category).collect {
                result.postValue(it)
            }
        }
        return result
    }
}
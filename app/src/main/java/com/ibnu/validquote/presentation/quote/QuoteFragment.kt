package com.ibnu.validquote.presentation.quote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.validquote.data.model.Quote
import com.ibnu.validquote.data.remote.service.ApiResponse
import com.ibnu.validquote.databinding.FragmentQuoteBinding
import com.ibnu.validquote.presentation.quote.adapter.CategoryAdapter
import com.ibnu.validquote.presentation.quote.adapter.CategoryItemHandler
import com.ibnu.validquote.presentation.quote.adapter.QuoteAdapter
import com.ibnu.validquote.presentation.quote.adapter.QuoteItemHandler
import com.ibnu.validquote.utils.extension.showExitAppDialog
import com.ibnu.validquote.utils.helper.CategoryHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class QuoteFragment : Fragment(), QuoteItemHandler, CategoryItemHandler {

    private val viewModel: QuoteViewModel by viewModels()

    private var _binding: FragmentQuoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: QuoteAdapter
    private lateinit var categoriesAdapter: CategoryAdapter

    private var isAlreadyLoadingShimmering = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().showExitAppDialog()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateAdapters()
        categoriesAdapter.setData(CategoryHelper().getCategories())
        showQuotes("")
    }

    private fun initiateAdapters() {
        articleAdapter = QuoteAdapter(this)
        binding.rvQuote.apply {
            layoutManager =
                LinearLayoutManager(requireContext(),  LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }

        categoriesAdapter = CategoryAdapter(this)
        binding.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun showQuotes(category: String) {
        viewModel.getQuotes(category).observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    Timber.d("Loading")
                    showLoading(true)
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    Timber.d("Error ${response.errorMessage}")
                }
                is ApiResponse.Success -> {
                    showLoading(false)
                    binding.rvQuote.visibility = View.VISIBLE
                    articleAdapter.setData(response.data)
                }
                is ApiResponse.Empty -> {
                    binding.rvCategories.visibility = View.INVISIBLE
                    showLoading(false)
                }
                else -> {
                    showLoading(false)
                    Timber.d("Unknown Error")
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (!isAlreadyLoadingShimmering) {
            if (isLoading) {
                binding.shimmeringLoadingHome.startShimmer()
                binding.shimmeringLoadingHome.showShimmer(true)
                binding.loadingLayout.visibility = View.VISIBLE
            } else {
                binding.shimmeringLoadingHome.stopShimmer()
                binding.shimmeringLoadingHome.showShimmer(false)
                binding.loadingLayout.visibility = View.GONE
                isAlreadyLoadingShimmering = true
            }
        } else {
            if (isLoading) {
                binding.loadingCircular.visibility = View.VISIBLE
            } else {
                binding.loadingCircular.visibility = View.GONE
            }
        }
    }


    override fun onCategoryItemClicked(name: String) {
        showQuotes(name)
    }

    override fun navigateToDetail(quote: Quote) {
        val action = QuoteFragmentDirections.actionQuoteFragmentToQuoteWebViewFragment(quote)
        findNavController().navigate(action)
    }

}
package com.ibnu.validquote.presentation.quote.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibnu.validquote.data.model.Quote
import com.ibnu.validquote.databinding.ItemQuoteBinding
import com.ibnu.validquote.utils.extension.popTap

class QuoteAdapter(private val itemHandler: QuoteItemHandler) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    private var listQuote = ArrayList<Quote>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listQuote: List<Quote>) {
        this.listQuote.clear()
        this.listQuote.addAll(listQuote)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = listQuote[position]
        holder.bind(quote)
    }

    override fun getItemCount(): Int = listQuote.size

    inner class QuoteViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(quote: Quote) {

            binding.root.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    itemHandler.navigateToDetail(quote)
                }, 200)
            }

            binding.txvQuote.text = quote.quote
            binding.txvAuthor.text = "- ${quote.author}"
        }
    }
}
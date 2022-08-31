package com.ibnu.validquote.presentation.quote.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ibnu.validquote.R
import com.ibnu.validquote.data.model.Category
import com.ibnu.validquote.databinding.ItemCategoryBinding
import com.ibnu.validquote.utils.extension.popTap

class CategoryAdapter(private val itemHandler: CategoryItemHandler) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val listCategory = ArrayList<Category>()
    private var selectedPos = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listCategory: List<Category>) {
        this.listCategory.addAll(listCategory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = listCategory[position]
        holder.bind(category, position)
        holder.itemView.setOnClickListener {
            it.popTap()
            notifyItemChanged(selectedPos)
            selectedPos = holder.layoutPosition
            notifyItemChanged(selectedPos)
            Handler(Looper.getMainLooper()).postDelayed({
                itemHandler.onCategoryItemClicked(
                    category.value
                )
            }, 200)
        }
    }

    override fun getItemCount(): Int = listCategory.size

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, position: Int) {

            if (selectedPos == position){
                binding.txvCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {
                binding.txvCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey_400))
            }

            binding.txvCategoryName.text = category.name
        }
    }

}
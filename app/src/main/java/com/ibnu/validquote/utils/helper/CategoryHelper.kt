package com.ibnu.validquote.utils.helper

import com.ibnu.validquote.data.model.Category

class CategoryHelper {

    fun getCategories(): List<Category> {
        val categories = ArrayList<Category>()
        categories.add(Category(name = "All", value = ""))
        categories.add(Category(name = "Attitude", value = "attitude"))
        categories.add(Category(name = "Amazing", value = "amazing"))
        categories.add(Category(name = "Faith", value = "faith"))
        categories.add(Category(name = "Good", value = "good"))
        categories.add(Category(name = "Happiness", value = "happiness"))
        categories.add(Category(name = "Imagination", value = "imagination"))
        categories.add(Category(name = "Inspirational", value = "inspirational"))
        categories.add(Category(name = "Intelligence", value = "intelligence"))
        categories.add(Category(name = "Leadership", value = "leadership"))
        categories.add(Category(name = "Life", value = "life"))
        categories.add(Category(name = "Love", value = "love"))
        categories.add(Category(name = "Money", value = "money"))
        categories.add(Category(name = "Success", value = "success"))

        return categories
    }
}
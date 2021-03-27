package com.thomas.foody.viewmodels

import androidx.lifecycle.ViewModel
import com.thomas.foody.util.Constants.Companion.API_KEY
import com.thomas.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.thomas.foody.util.Constants.Companion.QUERY_API_KEY
import com.thomas.foody.util.Constants.Companion.QUERY_DIET
import com.thomas.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.thomas.foody.util.Constants.Companion.QUERY_NUMBER
import com.thomas.foody.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RecipesViewModel : ViewModel() {


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}
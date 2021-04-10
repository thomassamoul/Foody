package com.thomas.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thomas.foody.databinding.IngredientsRowLayoutBinding
import com.thomas.foody.models.ExtendedIngredient
import com.thomas.foody.util.RecipesDiffUtl

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredient) {
            binding.ingredient = ingredient
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): IngredientsAdapter.MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return IngredientsAdapter.MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredient = ingredientsList[position]

        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newData: List<ExtendedIngredient>) {
        val recipesDiffUtl = RecipesDiffUtl(ingredientsList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtl)
        ingredientsList = newData
        diffUtilResult.dispatchUpdatesTo(this)

    }
}
package com.thomas.foody.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thomas.foody.R
import com.thomas.foody.data.database.entities.FavoritesEntity
import com.thomas.foody.databinding.FavoriteRecipesRowLayoutBinding
import com.thomas.foody.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.thomas.foody.util.RecipesDiffUtl
import com.thomas.foody.viewmodels.MainViewModel

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private lateinit var actionMode: ActionMode
    private lateinit var rootView: View
    private var multiSelection = false

    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var favoritesEntity = emptyList<FavoritesEntity>()
    private var myViewHolder = arrayListOf<MyViewHolder>()

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favorite = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolder.add(holder)
        rootView = holder.binding.root
        val currentRecipe = favoritesEntity[position]
        holder.bind(currentRecipe)
        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        favoritesEntity[position].result
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true

                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.favoriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )
        holder.binding.favoriteRowCardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> actionMode.finish()
            1 -> actionMode.title = "${selectedRecipes.size} item selected"
            else -> actionMode.title = "${selectedRecipes.size} items selected"
        }
    }

    override fun getItemCount(): Int {
        return favoritesEntity.size
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        actionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favorite_recipe_menu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed.")
            multiSelection = false
            selectedRecipes.clear()
            actionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolder.forEach {
            changeRecipeStyle(it, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newData: List<FavoritesEntity>) {
        val favoriteDiffUtil = RecipesDiffUtl(favoritesEntity, newData)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteDiffUtil)
        favoritesEntity = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).setAction("Ok") {}.show()

    }

    fun clearContextualActionMode() {
        if (this::actionMode.isInitialized) {
            actionMode.finish()
        }
    }

}
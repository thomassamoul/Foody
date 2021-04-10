package com.thomas.foody.ui.fragments.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.foody.adapters.IngredientsAdapter
import com.thomas.foody.databinding.FragmentIngredientsBinding
import com.thomas.foody.models.Result
import com.thomas.foody.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment() {

    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        setupRecyclerView()
        myBundle?.extendedIngredients?.let{
            adapter.setData(it)
        }
        return binding.root

    }

    private fun setupRecyclerView() {
        binding.ingredientsRecyclerView.adapter = adapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
package com.example.modernfoodrecipesapp.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modernfoodrecipesapp.R
import com.example.modernfoodrecipesapp.databinding.FragmentFoodJokeBinding
import com.example.modernfoodrecipesapp.databinding.FragmentIngredientsBinding
import com.example.modernfoodrecipesapp.model.Result
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.RECIPES_RESULT_KEY

class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPES_RESULT_KEY)

        setupRecyclerView()

        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }


    }

    private fun setupRecyclerView() {
        with(binding) {
            ingredientsRecyclerView.adapter = mAdapter
            ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
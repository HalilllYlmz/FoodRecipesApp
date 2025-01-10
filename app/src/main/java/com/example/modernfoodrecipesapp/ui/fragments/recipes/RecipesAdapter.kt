package com.example.modernfoodrecipesapp.ui.fragments.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernfoodrecipesapp.R
import com.example.modernfoodrecipesapp.databinding.RecipesRowLayoutBinding
import com.example.modernfoodrecipesapp.model.FoodRecipe
import com.example.modernfoodrecipesapp.model.Result
import com.example.modernfoodrecipesapp.utilities.RecipesDiffUtil

class RecipesAdapter(
    private val onItemClick: (Result) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipes = emptyList<Result>()

    class ViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result, onItemClick: (Result) -> Unit) {
            binding.titleTextView.text = result.title
            binding.descriptionTextView.text = result.summery
            binding.heartTextView.text = result.aggregateLikes.toString()
            binding.clockTextView.text = result.readyInMinutes.toString()

            val isVegan = result.vegan
            if (isVegan) {
                binding.leafTextView.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.green
                    )
                )
                binding.leafImageView.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.green
                    )
                )
            }

            binding.recipeImageView.load(result.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }

            binding.rowCardView.setOnClickListener {
                onItemClick(result)
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentResult = recipes[position]
        holder.bind(currentResult, onItemClick)
    }

    override fun getItemCount() = recipes.size

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
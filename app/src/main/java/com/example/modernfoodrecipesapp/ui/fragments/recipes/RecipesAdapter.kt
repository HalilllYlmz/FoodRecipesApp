package com.example.modernfoodrecipesapp.ui.fragments.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfoodrecipesapp.databinding.RecipesRowLayoutBinding
import com.example.modernfoodrecipesapp.model.FoodRecipe
import com.example.modernfoodrecipesapp.model.Result

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipes = emptyList<Result>()

    class ViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            // ToDo
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
        holder.bind(currentResult)
    }

    override fun getItemCount() = recipes.size

    fun setData(newData: FoodRecipe) {
        recipes = newData.results
        notifyDataSetChanged()
    }
}
package com.example.modernfoodrecipesapp.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.modernfoodrecipesapp.R
import com.example.modernfoodrecipesapp.databinding.FragmentOverviewBinding
import com.example.modernfoodrecipesapp.model.Result
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipesBundle")

        with(binding) {
            mainImageView.load(myBundle?.image)
            titleTextView.text = myBundle?.title
            likesTextView.text = myBundle?.aggregateLikes.toString()
            timeTextView.text = myBundle?.readyInMinutes.toString()
            val desc = Jsoup.parse(myBundle?.summery).text()
            summaryTextView.text = desc

            if(myBundle?.vegan == true) {
                veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle?.vegetarian == true) {
                vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle?.glutenFree == true) {
                glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle?.veryHealthy == true) {
                healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle?.dairyFree == true) {
                dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if(myBundle?.cheap == true) {
                cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.modernfoodrecipesapp.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.modernfoodrecipesapp.R
import com.example.modernfoodrecipesapp.databinding.RecipesBottomSheetBinding
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.modernfoodrecipesapp.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class RecipesBottomSheet : BottomSheetDialogFragment() {


    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val recipesViewModel: RecipesViewModel by viewModels()

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipID = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeID, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeID, binding.dietTypeChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, selectedChipID ->
            val chip = group.findViewById<Chip>(selectedChipID.first())
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipID = selectedChipID.first()
        }

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, selectedChipID ->
            val chip = group.findViewById<Chip>(selectedChipID.first())
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeID = selectedChipID.first()
        }

        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipID,
                dietTypeChip,
                dietTypeID
            )
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
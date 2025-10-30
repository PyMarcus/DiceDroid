package com.devmarcus.dicegame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.devmarcus.dicegame.databinding.FragmentFirstBinding
import com.devmarcus.dicegame.databinding.FragmentSecondBinding
import kotlinx.coroutines.launch
import kotlin.getValue

class FirstFragment : Fragment() {
    private val viewModel: DiceViewModel by activityViewModels()
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{
            viewModel.uiState.collect {
                it.rolledDiceValue2?.let {
                    binding.ivRolledDice2.setImageResource(it)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
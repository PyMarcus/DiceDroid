package com.devmarcus.dicegame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.devmarcus.dicegame.databinding.FragmentListBinding
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private val viewModel: DiceViewModel by activityViewModels()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{
            viewModel.uiState.collect { binding.rvRolledDices.adapter = RollDiceAdapter(rolledDiceList = it.rolledDiceList)}
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
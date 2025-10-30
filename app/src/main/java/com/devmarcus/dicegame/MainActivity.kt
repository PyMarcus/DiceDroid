package com.devmarcus.dicegame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.devmarcus.dicegame.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DiceViewModel by viewModels()

    private val navController by lazy{
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fcvMain.id) as? NavHostFragment
        navHostFragment?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                // verifica se houve mudanca de status
                viewModel.uiState.collect {
                    it.rolledDiceValue2?.let {
                        binding.ivRolledDice1.setImageResource(it)
                    }
                }
            }
        }



        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.eventListeners()
    }

    private fun eventListeners(){
        // next btn
        binding.btnNextMain.setOnClickListener {
            navController?.currentDestination?.id.let {
                when(it){
                    R.id.firstFragment -> navController?.navigate(R.id.action_firstFragment_to_secondFragment)
                    R.id.secondFragment -> navController?.popBackStack()
                }
            }
        }

        // roll dice btn
        binding.btnPlayMain.setOnClickListener {
            viewModel.rollDice()
        }
    }

}
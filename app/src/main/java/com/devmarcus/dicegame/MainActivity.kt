package com.devmarcus.dicegame

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.devmarcus.dicegame.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DiceViewModel by viewModels()

    private val navController by lazy{
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvMain) as? NavHostFragment
        navHostFragment?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                // verifica se houve mudanca de status
                viewModel.uiState.collect {
                    it.rolledDiceValue1?.let {
                        binding.ivRolledDice1.setImageResource(it)
                    }
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.eventListeners(navController)
    }

    override fun onResume() {
        // quando o app esta em 1 plano
        super.onResume()

        viewModel.uiStateLiveData.observe(this@MainActivity){
            uiState -> uiState.rolledDiceValue1?.let {
                imgRes -> binding.ivRolledDice1.setImageResource(imgRes)
        }
        }
    }

    private fun eventListeners(navController: NavController?){
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
            //
            AlertDialog.Builder(this@MainActivity).
                    setTitle("Rolled dices")
                .setMessage("Do you really want to roll dice?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes"){_,_-> viewModel.rollDice()}
                .setPositiveButtonIcon(AppCompatResources.getDrawable(this@MainActivity, R.drawable.dice_six_faces_one))
                .setNegativeButton("No"){_,_->}
                .setCancelable(false)
                .create()
                .show()
        }
    }

    /**
        Setting the menu configuration and show the options
        @param menu: Menu
        @return boolean
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.create->{
                Toast.makeText(this@MainActivity, "Creating", Toast.LENGTH_SHORT).show()
                true}
            R.id.open->{
                Toast.makeText(this@MainActivity, "Opening", Toast.LENGTH_SHORT).show()

                true}
            else -> false
        }
    }
}
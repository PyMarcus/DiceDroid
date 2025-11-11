package com.devmarcus.dicegame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmarcus.dicegame.databinding.ItemRolledDiceBinding

class RollDiceAdapter(val rolledDiceList: List<RolledDices>): RecyclerView.Adapter<RollDiceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemRolledDiceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding(rolledDiceList[position])
    }

    override fun getItemCount() = rolledDiceList.size

    class ViewHolder(private val bindingItemRolledDice: ItemRolledDiceBinding) : RecyclerView.ViewHolder(bindingItemRolledDice.root){
        fun binding(rolledDices: RolledDices){
            with(bindingItemRolledDice){
                ivHistory1.setImageResource(getDiceImageResource(rolledDices.dice1))
                ivHistory2.setImageResource(getDiceImageResource(rolledDices.dice2))
                ivHistory3.setImageResource(getDiceImageResource(rolledDices.dice3))
            }
        }
    }
}

data class RolledDices(val dice1: Int, val dice2: Int, val dice3: Int)

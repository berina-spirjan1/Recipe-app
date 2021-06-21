package com.codingwithme.tastyRecipes.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.pmf.math.myapplication.tastyRecipes.R
import com.codingwithme.tastyRecipes.entities.MyRecipes
import kotlinx.android.synthetic.main.custom_row.view.*

class MyRecipesAdapter: RecyclerView.Adapter<MyRecipesAdapter.MyViewHolder>() {
    private var myRecipesList = emptyList<MyRecipes>()
    class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun getItemCount(): Int {
        return myRecipesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myRecipesList[position]
        holder.itemView.broj.text=currentItem.id.toString()
        holder.itemView.prikazi_naslov_recepta.text=currentItem.name
        holder.itemView.rowLayout.setOnClickListener{
            val action=ThirdFragmentDirections.actionTreciFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }
    fun setData(myRecipes: List<MyRecipes>){
        this.myRecipesList=myRecipes
        notifyDataSetChanged()
    }
}
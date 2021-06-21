package com.codingwithme.tastyRecipes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ba.unsa.pmf.math.covid_19vaccinationapp.viewmodel.UserViewModel
import ba.unsa.pmf.math.myapplication.tastyRecipes.R
import com.codingwithme.tastyRecipes.entities.MyRecipes
import com.codingwithme.tastyRecipes.viewmodel.MyRecipesViewModel
import kotlinx.android.synthetic.main.fragment_update_my_recipes.*
import kotlinx.android.synthetic.main.fragment_update_my_recipes.view.*

class UpdateMyRecipesFragment : Fragment() {
    private val args by navArgs<UpdateMyRecipesFragmentArgs>()
    private lateinit var mUserViewModel: MyRecipesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_update_my_recipes, container, false)
        mUserViewModel = ViewModelProvider(this).get(MyRecipesViewModel::class.java)
        view.name_of_recipe_edit.setText(args.currentUser.name)
        view.sastojci_edit.setText(args.currentUser.ingredients)
        view.recept_edit.setText(args.currentUser.instructions)
        view.zapisi_recept_edit.setOnClickListener {
            updateItem()
        }

        //Dodajemo meni
        setHasOptionsMenu(true)
        return view
        return view;
    }

    private fun updateItem() {
        val name=name_of_recipe_edit.text.toString()
        val sastojci=sastojci_edit.text.toString()
        val recept=recept_edit.text.toString()
        if(inputCheck(name,sastojci,recept)) {
            //Kreiramo MyRecipe objekat
            val updatedRecipe = MyRecipes(args.currentUser.id,name,sastojci,recept)
            //Uređivanje trenutnog recepta
            mUserViewModel.updateMyRecipes(updatedRecipe)
            Toast.makeText(requireContext(),"Successfully updated recipe!", Toast.LENGTH_SHORT).show()
            //Navigiramo povratak
                findNavController().navigate(R.id.action_updateMyRecipesFragment_to_treciFragment)
        }
        else{
            Toast.makeText(requireContext(),"Please fill out all fields!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputCheck(name: String, sastojci: String, recept: String):Boolean{
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(sastojci) || TextUtils.isEmpty(recept))
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_delete){
            deleteMyRecipe()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteMyRecipe() {
        val builder = AlertDialog.Builder(requireContext())
        //sada provjeravamo nakon što ljekar pritisne delete dugme, da li doista želi obrisati tog usera
        builder.setNegativeButton("No"){_,_->}
        builder.setPositiveButton("Yes"){_,_->
            mUserViewModel.deleteMyRecipe(args.currentUser)
            Toast.makeText(requireContext(),"Successfully removed: ${args.currentUser.name}!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateMyRecipesFragment_to_treciFragment)

        }
        builder.setTitle("Delete ${args.currentUser.name}?")
        builder.setMessage("Are you sure that you want to delete ${args.currentUser.name}?")
        builder.create().show()

    }
}
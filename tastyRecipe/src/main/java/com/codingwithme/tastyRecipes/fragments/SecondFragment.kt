package com.codingwithme.tastyRecipes.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ba.unsa.pmf.math.covid_19vaccinationapp.viewmodel.UserViewModel
import ba.unsa.pmf.math.myapplication.tastyRecipes.R

import com.codingwithme.tastyRecipes.entities.MyRecipes
import com.codingwithme.tastyRecipes.viewmodel.MyRecipesViewModel
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.view.*


class SecondFragment : Fragment() {
    private lateinit var mUserViewModel: MyRecipesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_second, container, false)
        mUserViewModel= ViewModelProvider(this).get(MyRecipesViewModel::class.java)
        view.dodaj_sliku.setOnClickListener {
            val intent= Intent(Intent.ACTION_PICK);
            intent.type="image/*";
            startActivityForResult(intent,456);
        }
        view.zapisi_recept.setOnClickListener { //saljemo na taj klik zapisivanje podataka u bazu
            insertDataToDatabase()
        }
        setHasOptionsMenu(true)
        return view;
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.share_menu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_share){
            shareData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareData() {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="type/palin"
        val textBody="My recipes"
        val shareSubject="Thank you for successfully adding your recipe."
        intent.putExtra(Intent.EXTRA_SUBJECT,textBody)
        intent.putExtra(Intent.EXTRA_TEXT,shareSubject)
        startActivity(Intent.createChooser(intent,"Share your information"))
    }

    private fun insertDataToDatabase() {
        val name=name_of_recipe.text.toString()
        val ingredients=sastojci.text.toString()
        val instructions=recept.text.toString()
        if(inputCheck(name,ingredients,instructions)){
            val myRecipe= MyRecipes(0,name,ingredients,instructions);
            mUserViewModel.addMyRecipes(myRecipe);
            Toast.makeText(requireContext(),"Successfully added recipe!", Toast.LENGTH_SHORT).show()
        }
        else{ //ukoliko neko od polja nije popunjeno ispisujemo prikladnu poruku
            Toast.makeText(requireContext(),"Please fill out all fields!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputCheck(name: String, ingredients: String, instructions: String):Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(ingredients) || TextUtils.isEmpty(instructions));
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==456){
            slika_recepta.setImageURI(data?.data);
        }
    }
}
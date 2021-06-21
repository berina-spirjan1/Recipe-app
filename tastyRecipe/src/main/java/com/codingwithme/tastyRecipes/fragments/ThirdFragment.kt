package com.codingwithme.tastyRecipes.fragments

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ba.unsa.pmf.math.myapplication.tastyRecipes.R
import com.codingwithme.tastyRecipes.entities.MyRecipes
import com.codingwithme.tastyRecipes.viewmodel.MyRecipesViewModel
import kotlinx.android.synthetic.main.fragment_third.view.*
import kotlinx.coroutines.launch


class ThirdFragment : Fragment() {
    private lateinit var mUserViewModel: MyRecipesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_third, container, false)
        val adapter = MyRecipesAdapter()
        val recyclerView=view.recyclerView
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mUserViewModel= ViewModelProvider(this).get(MyRecipesViewModel::class.java)
        mUserViewModel.readAllMyRecipes.observe(viewLifecycleOwner, Observer {recipe->
            adapter.setData(recipe)
        })
        view.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_treciFragment_to_drugiFragment)
        }
        setHasOptionsMenu(true)
        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_delete){
            deleteAllMyRecipes()
        }
        return super.onOptionsItemSelected(item)
    }
    //ovdje implementiramo logiku za brisanje svih recepata, tj. uklanjanje svih podataka iz baze
    private fun deleteAllMyRecipes() {
        val builder = AlertDialog.Builder(requireContext())
        //sada provjeravamo nakon što ljekar pritisne delete dugme, da li doista želi obrisati sve recepte
        builder.setNegativeButton("No"){_,_->}
        builder.setPositiveButton("Yes"){_,_->
            mUserViewModel.deleteAllMyRecipes()
            Toast.makeText(requireContext(),"Successfully removed all recipes!",
                Toast.LENGTH_SHORT).show()
        }

        builder.setTitle("Delete all your recipes?")
        builder.setMessage("Are you sure that you want to delete all recipes?")
        builder.create().show()
    }
}
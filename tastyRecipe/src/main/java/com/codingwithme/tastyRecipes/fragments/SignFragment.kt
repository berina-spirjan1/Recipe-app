package com.codingwithme.tastyRecipes.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import ba.unsa.pmf.math.covid_19vaccinationapp.viewmodel.UserViewModel
import ba.unsa.pmf.math.myapplication.tastyRecipes.R
import com.codingwithme.tastyRecipes.entities.User
import kotlinx.android.synthetic.main.fragment_sign.*
import kotlinx.android.synthetic.main.fragment_sign.view.*

class SignFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_sign, container, false);
        mUserViewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        view.predji_na_login.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signFragment_to_loginFragment)
        }
        view.uloguj_se.setOnClickListener {
            insertDataToDatabase()
        }
        return view;
    }
    private fun insertDataToDatabase() {
        val name=edit_name.text.toString()
        val mail=edit_mail.text.toString()
        val password=edit_password.text.toString()
        val confirm_password=edit_confirm_password.text.toString()
        if(inputCheck(name,mail,password,confirm_password) && password==confirm_password){ //ukoliko su sva polja popunjena
            //Kreiramo potom User Object
            val user= User(0,name,mail,password)
            //Dodajemo podatke u bazu podataka
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(),"Successfully added user!", Toast.LENGTH_SHORT).show()
            //Navigiramo back, ukoliko je profesija doktor vraćamo se na fragment u kojem se ispisuju svi useri
            findNavController().navigate(R.id.action_signFragment_to_loginFragment)
        }
        else if(password==confirm_password){ //ukoliko neko od polja nije popunjeno ispisujemo prikladnu poruku
            Toast.makeText(requireContext(),"Please fill out all fields!", Toast.LENGTH_SHORT).show()
        }
        else if(inputCheck(name,mail,password,confirm_password)){
            Toast.makeText(requireContext(),"Please check your password!", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(),"Please check your password and fill out all fields!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputCheck(name: String, mail: String, password: String, confirm_password: String):Boolean{
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm_password))
    }
}
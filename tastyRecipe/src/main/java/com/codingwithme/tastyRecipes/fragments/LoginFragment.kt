package com.codingwithme.tastyRecipes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import ba.unsa.pmf.math.myapplication.tastyRecipes.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_login, container, false)
        view.predji_dalje_na_sign.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signFragment)
        }
        return view
    }
}
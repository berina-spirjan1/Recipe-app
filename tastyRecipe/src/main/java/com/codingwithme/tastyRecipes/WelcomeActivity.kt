package com.codingwithme.tastyRecipes

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import ba.unsa.pmf.math.myapplication.tastyRecipes.R
import ba.unsa.pmf.math.tastyrecipes.data.UserDao
import com.codingwithme.tastyRecipes.database.UserDatabase
import com.codingwithme.tastyRecipes.database.RecipeDatabase
import com.codingwithme.tastyRecipes.entities.*
import com.codingwithme.tastyRecipes.interfaces.GetDataService
import com.codingwithme.tastyRecipes.retofitclient.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {
    private lateinit var editPassword:EditText
    private lateinit var editMail: EditText
    private var database: UserDatabase? = null
    private var userDAO: UserDao? = null
    private var progressDialog: ProgressDialog? = null
    private var READ_STORAGE_PERM = 123
    private lateinit var button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        button=findViewById(R.id.prijavi_se)
        editMail=findViewById(R.id.mail_login)
        editPassword=findViewById(R.id.password_login)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Check User...")
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setProgress(0)
        database = Room.databaseBuilder(this, UserDatabase::class.java, "user_table.db")
            .allowMainThreadQueries()
            .build()
        userDAO = database!!.userDao()
        prijavi_se.setOnClickListener {
            if (!emptyValidation()) {
                progressDialog!!.show()
                Handler().postDelayed({
                    val user: User = userDAO!!.getUser(
                        editMail.text.toString(),
                        editPassword.text.toString()
                    )
                    if (user != null) {
                    Toast.makeText(
                                this@WelcomeActivity,
                        "Unregistered user, or incorrect",
                        Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val intent = Intent(this@WelcomeActivity, SearchActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    progressDialog!!.dismiss()
                }, 1000)
            } else {
                Toast.makeText(this@WelcomeActivity, "Empty Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun emptyValidation(): Boolean {
        return (TextUtils.isEmpty(editMail.text.toString()) || TextUtils.isEmpty(editPassword.text.toString()))
    }
    fun getCategories() {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<Category> {
            override fun onFailure(call: Call<Category>, t: Throwable) {

                Toast.makeText(this@WelcomeActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {

                for (arr in response.body()!!.categorieitems!!) {
                    getMeal(arr.strcategory)
                }
                insertDataIntoRoomDb(response.body())
            }

        })
    }


    fun getMeal(categoryName: String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : Callback<Meal> {
            override fun onFailure(call: Call<Meal>, t: Throwable) {
                Toast.makeText(this@WelcomeActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {

                insertMealDataIntoRoomDb(categoryName, response.body())
            }

        })
    }

    fun insertDataIntoRoomDb(category: Category?) {

        launch {
            this.let {

                for (arr in category!!.categorieitems!!) {
                    RecipeDatabase.getDatabase(this@WelcomeActivity)
                        .recipeDao().insertCategory(arr)
                }
            }
        }


    }

    fun insertMealDataIntoRoomDb(categoryName: String, meal: Meal?) {

        launch {
            this.let {


                for (arr in meal!!.mealsItem!!) {
                    var mealItemModel = MealsItems(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipeDatabase.getDatabase(this@WelcomeActivity)
                        .recipeDao().insertMeal(mealItemModel)
                    Log.d("mealData", arr.toString())
                }

            }
        }


    }

    fun clearDataBase() {
        launch {
            this.let {
                RecipeDatabase.getDatabase(this@WelcomeActivity).recipeDao().clearDb()
            }
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePermission()) {
            clearDataBase()
            getCategories()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage,",
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    fun predjiDalje(view: View) {
        val intent=Intent(this@WelcomeActivity,SearchActivity::class.java)
        startActivity(intent)
    }
}
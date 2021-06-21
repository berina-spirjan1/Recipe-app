package ba.unsa.pmf.math.covid_19vaccinationapp.repository

import androidx.lifecycle.LiveData
import ba.unsa.pmf.math.tastyrecipes.data.UserDao
import com.codingwithme.tastyRecipes.entities.User

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()
    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }
    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }
    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

}
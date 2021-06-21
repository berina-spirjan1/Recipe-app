package ba.unsa.pmf.math.tastyrecipes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codingwithme.tastyRecipes.entities.User

/*UserDao interfejs sadrži metode koje su nam neophodne za pristup bazi podataka*/
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id")
    fun readAllData():LiveData<List<User>>

    @Query("SELECT * FROM user_table where mail= :mail and password= :password")
    fun getUser(mail: String, password: String): User
}
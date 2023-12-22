package ru.kpfu.itis.bagaviev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import ru.kpfu.itis.bagaviev.data.entities.UserDbEntity
import ru.kpfu.itis.bagaviev.data.tuples.UserInfoTuple

@Dao
interface UserDao {

    @Query("INSERT INTO users (name, phone, email, password, token) " +
            "VALUES (:name, :phone, :email, :password, :token)")
    suspend fun insert(name: String, phone: String, email: String, password: String, token: String)

    @Update
    suspend fun update(userDbEntity: UserDbEntity)

    @Query("UPDATE users SET phone = :phone WHERE user_id = :userId")
    suspend fun updatePhone(userId: Int, phone: String)

    @Query("UPDATE users SET password = :password WHERE user_id = :userId")
    suspend fun updatePassword(userId: Int, password: String)

    @Query("SELECT * FROM users WHERE token = :token")
    suspend fun getByToken(token: String): UserInfoTuple?

    @Query("SELECT users.token FROM users WHERE email = :email and password = :password")
    suspend fun getToken(email: String, password: String): String?

    @Query("DELETE FROM users WHERE token = :token")
    suspend fun deleteUser(token: String)

}
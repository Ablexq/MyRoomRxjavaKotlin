package com.xq.myroomrxjavakotlin.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface UserDao {
    //所有的CURD根据primary key进行匹配
    //------------------------query------------------------
    @Query("SELECT * FROM user")
    fun getAll(): Single<List<User>>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): Single<List<User>>

    @Query(
        "SELECT * FROM user WHERE first_name LIKE :first AND "
                + "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String?, last: String?): Single<User>

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun findByUid(uid: Int): Single<User>

    //-----------------------insert----------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User?): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User?): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User?>?): Single<List<Long>>

    //---------------------update------------------------
    @Update
    fun update(user: User?): Single<Int>

    @Update
    fun updateAll(vararg user: User?): Completable

    @Update
    fun updateAll(user: List<User?>?): Completable

    //-------------------delete-------------------
    @Delete
    fun delete(user: User?): Single<Int>

    @Delete
    fun deleteAll(users: List<User?>?): Single<Int>

    @Delete
    fun deleteAll(vararg users: User?): Single<Int>
}
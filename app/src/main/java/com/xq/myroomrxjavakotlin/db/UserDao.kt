package com.xq.myroomrxjavakotlin.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface UserDao {
    //所有的CURD根据primary key进行匹配
    //------------------------query------------------------
    @Query("SELECT * FROM user")
    fun getAll(): Single<List<UserEntity>>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): Single<List<UserEntity>>

    @Query(
        "SELECT * FROM user WHERE first_name LIKE :first AND "
                + "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String?, last: String?): Single<UserEntity>

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun findByUid(uid: Int): Single<UserEntity>

    //-----------------------insert----------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity?): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: UserEntity?): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity?>?): Single<List<Long>>

    //---------------------update------------------------
    @Update
    fun update(user: UserEntity?): Single<Int>

    @Update
    fun updateAll(vararg user: UserEntity?): Completable

    @Update
    fun updateAll(user: List<UserEntity?>?): Completable

    //-------------------delete-------------------
    @Delete
    fun delete(user: UserEntity?): Single<Int>

    @Delete
    fun deleteAll(users: List<UserEntity?>?): Single<Int>

    @Delete
    fun deleteAll(vararg users: UserEntity?): Single<Int>
}
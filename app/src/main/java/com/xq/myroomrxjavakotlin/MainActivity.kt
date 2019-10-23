package com.xq.myroomrxjavakotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.xq.myroomrxjavakotlin.db.AppDatabase
import com.xq.myroomrxjavakotlin.db.UserEntity
import com.xq.myroomrxjavakotlin.db.UserDao
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var mUserDao: UserDao? = null
    private var mBuffer: StringBuffer? = null
    private val TAG = "thedata"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db: AppDatabase = Room.databaseBuilder<AppDatabase>(
            applicationContext,
            AppDatabase::class.java,
            "roomDemo-database"
        )
            //添加数据库的变动迁移支持(当前状态从version1到version2的变动处理)
            //主要在user里面加入了age字段,大家可以git reset --hard <commit> 到第一个版本
            //然后debug 手动生成一些数据。然后debug 该版本查看数据库升级体现。
//            .addMigrations(AppDatabase.MIGRATION_1_2)
            //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
            //他可能造成主线程lock以及anr
//                .allowMainThreadQueries()
            .build()
        mUserDao = db.userDao()
        mBuffer = StringBuffer()
    }


    fun onClick(view: View) {
        mBuffer!!.delete(0, mBuffer!!.length)
        when (view.getId()) {
            R.id.insert_one -> insertOne()
            R.id.insert_some -> insertSome()
            R.id.update_one -> updateOne()
            R.id.delete_one -> deleteOne()
            R.id.find_one -> findOne()
            R.id.find_all -> findAll()
            R.id.delete_all -> deleteAll()
        }
    }

    private fun updateOne() {
        Thread(Runnable {
            val random = Random().nextInt(9) + 1
            val update: Int = mUserDao!!.update(
                UserEntity(
                    random,
                    "t" + System.currentTimeMillis() / 1000,
                    "kobe"
                )
            ).blockingGet()
            if (update > 0) {
                val msg = "update one success, index is $random"
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            } else {
                val msg =
                    "update one fail ,index is $random the user item doesn't exist "
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            }
            setMsg()
        }).start()
    }

    private fun insertSome() {
        Thread(Runnable {
            val users: ArrayList<UserEntity?> = ArrayList()
            users.add(
                UserEntity(
                    "t" + System.currentTimeMillis() / 1000,
                    "jordan",
                    20
                )
            )
            users.add(
                UserEntity(
                    "t" + System.currentTimeMillis() / 1000,
                    "james",
                    21
                )
            )
            val longs: List<Long> = mUserDao!!.insertAll(users).blockingGet()
            if (longs.isNotEmpty()) {
                for (aLong in longs) {
                    val msg = "insert some success, index is $aLong"
                    mBuffer!!.append(msg + "\n")
                    Log.i(TAG, msg)
                }
            } else {
                val msg = "insert some fail "
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            }
            setMsg()
        }).start()
    }

    private fun insertOne() {
        Thread(Runnable {
            //返回的是插入元素的primary key index

            val aLong: Long = mUserDao!!.insert(
                UserEntity(
                    "t" + System.currentTimeMillis() / 1000,
                    "allen",
                    18
                )
            ).blockingGet()
            if (aLong > 0) {
                val msg = "insert one success, index is $aLong"
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            } else {
                val msg = "insert one fail "
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            }
            setMsg()
        }).start()
    }

    private fun deleteOne() {
        Thread(Runnable {
            val random = Random().nextInt(9) + 1
            val delete: Int = mUserDao!!.delete(UserEntity(random)).blockingGet()
            if (delete > 0) {
                //size 表示删除个数

                val msg = "delete one  success,index is $random"
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            } else {
                val msg =
                    "delete  one fail ,index is $random the user item doesn't exist "
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            }
            setMsg()
        }).start()
    }

    private fun findOne() {
        Thread(Runnable {
            val random = Random().nextInt(9) + 1
            val user: UserEntity = mUserDao!!.findByUid(random).blockingGet()
            val msg =
                "find one success , index is $random  user:  $user"
            mBuffer!!.append(msg + "\n")
            Log.i(TAG, msg)
            setMsg()
        }).start()
    }

    private fun findAll() {
        Thread(Runnable {
            val all: List<UserEntity> = mUserDao!!.getAll().blockingGet()
            if (all.isNotEmpty()) {
                for (user1 in all) {
                    val msg = "find all success ,item  : $user1"
                    mBuffer!!.append(msg + "\n")
                    Log.i(TAG, msg)
                }
            } else {
                val msg = "find all fail , no user item  exist "
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            }
            setMsg()
        }).start()
    }

    private fun deleteAll() {
        Thread(Runnable {
            val all: List<UserEntity?> = mUserDao!!.getAll().blockingGet()
            if (all.isNotEmpty()) {
                val i: Int = mUserDao!!.deleteAll(all).blockingGet()
                val msg = "delete all success , delete  size $i"
                mBuffer!!.append(msg + "\n")
                Log.i(TAG, msg)
            } else {
                val msg = "delete all fail , no user item  exist "
                Log.i(TAG, msg)
                mBuffer!!.append(msg + "\n")
            }
            setMsg()
        }).start()
    }


    private fun setMsg() {
        runOnUiThread {
            val text = mBuffer.toString()
            tv!!.text = text
        }
    }
}

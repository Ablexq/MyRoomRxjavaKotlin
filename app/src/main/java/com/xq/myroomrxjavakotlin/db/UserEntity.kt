package com.xq.myroomrxjavakotlin.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "user", indices = [])
class UserEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    var uid = 0
    @ColumnInfo(name = "first_name")
    var firstName: String? = null
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    @ColumnInfo(name = "age")
    var age = 0

    constructor() {}

    @Ignore
    constructor(uid: Int) {
        this.uid = uid
    }

    @Ignore
    constructor(firstName: String?, lastName: String?) {
        this.firstName = firstName
        this.lastName = lastName
    }

    @Ignore
    constructor(uid: Int, firstName: String?, lastName: String?) {
        this.uid = uid
        this.firstName = firstName
        this.lastName = lastName
    }

    @Ignore
    constructor(firstName: String?, lastName: String?, age: Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }

    @Ignore
    constructor(uid: Int, firstName: String?, lastName: String?, age: Int) {
        this.uid = uid
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }

    override fun toString(): String {
        return "UserEntity{" +
                "uid=" + uid.toString() +
                ", firstName='" + firstName + '\''.toString() +
                ", lastName='" + lastName + '\''.toString() +
                ", age='" + age + '\'' +
                '}'.toInt()
    }
}
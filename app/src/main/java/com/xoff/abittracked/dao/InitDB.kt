package com.xoff.abittracked.dao

class InitDB {
    fun init(){
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val userDao = db.userDao()
        val users: List<User> = userDao.getAll()
    }
}
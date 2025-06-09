package com.example.finalexam.data.dao.followDao.impl

import com.example.finalexam.data.dao.followDao.FollowDao
import com.example.finalexam.entity.User

class FollowDaoImpl : FollowDao {
    override suspend fun getFollowers(userId: String): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowees(userId: String): List<User> {
        TODO("Not yet implemented")
    }

}
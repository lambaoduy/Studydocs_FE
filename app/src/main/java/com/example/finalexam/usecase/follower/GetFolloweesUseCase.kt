package com.example.finalexam.usecase.follower

import com.example.finalexam.data.dao.followDao.FollowDao
import com.example.finalexam.data.dao.followDao.impl.FollowDaoImpl
import com.example.finalexam.entity.User

class GetFolloweesUseCase {
    private val followDao: FollowDao = FollowDaoImpl()
    suspend fun invoke(userId: String): List<User> = followDao.getFollowees(userId)
}
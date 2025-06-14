package com.example.finalexam.usecase.follower

import com.example.finalexam.data.dao.followDao.FollowDao
import com.example.finalexam.data.dao.followDao.impl.FollowDaoImpl
import com.example.finalexam.entity.User

class GetFollowersUseCase {
    private val followerDao: FollowDao = FollowDaoImpl()
    suspend fun invoke(userId: String): List<User> = followerDao.getFollowers(userId)

}
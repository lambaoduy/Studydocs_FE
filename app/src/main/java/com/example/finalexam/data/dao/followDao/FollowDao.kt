package com.example.finalexam.data.dao.followDao

import com.example.finalexam.entity.User

interface FollowDao {
    suspend fun getFollowers(userId: String): List<User>
    suspend fun getFollowees(userId: String): List<User>
}
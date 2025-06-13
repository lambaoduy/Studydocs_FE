package com.example.finalexam.entity

data class Following(
    val followingId: String,
    val targetId: String,
    val name: String,
    val avatarUrl: String,
    val notifyEnables: Boolean
) {
}
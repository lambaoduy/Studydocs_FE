package com.example.finalexam.data.enums

enum class FollowType {
    USER("users");

    val value: String

    constructor(value: String) {
        this.value = value
    }
}
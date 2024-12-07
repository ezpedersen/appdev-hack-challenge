package com.example.frontend.data

import java.util.Date

data class FriendRequest (
    val sender : User,
    val reciever : User,
    val createdAt : Date,
    val repondedTo : Boolean
)
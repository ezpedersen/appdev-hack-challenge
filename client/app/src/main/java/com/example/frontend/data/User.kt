package com.example.frontend.data

data class User (
    val uuid : String,
    val name : String,
    val imageUrl : String,
    val netId : String,
    val bio : String,
    val asks : List<Listing>,
    val gives : List<Listing>,
    val friends : List<User>,
    val friendRequests : List<FriendRequest>
)

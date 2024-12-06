package com.example.frontend.data
import java.util.Date


data class User (
    val name : String,
    val netId : String,
    val bio : String,
    val wantedItems : List<Listing>,
    val offeredItems : List<Listing>,
    val friendList : List<User>
)

data class Listing (
    val name : String,
    val date : Date,
    val description : String,
    val type : String,
    val owner : User,
    val acceptedBy : User
)

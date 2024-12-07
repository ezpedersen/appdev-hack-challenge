package com.example.frontend.data

import java.util.Date

data class Listing (
    val name : String,
    val createdAt : Date,
    val imageUrl : String,
    val description : String,
    val type : String,
    val owner : User,
    val acceptedBy : User
)
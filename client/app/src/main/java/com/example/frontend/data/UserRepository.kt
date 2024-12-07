package com.example.frontend.data

import retrofit2.Call
import javax.inject.Inject
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi : ApiService,
)  {
    suspend fun getUserByNetId(netId : String): Response<User> {
        return userApi.getUserByNetid(netId)
    }

    suspend fun getUserByUId(uuid: String): Response<User> {
        return userApi.getUserByUuid(uuid)
    }

    suspend fun createUser(user: User) {
        userApi.createUser(user)
    }

    suspend fun deleteUser() {
        userApi.deleteUser()
    }

    suspend fun updateUserBio(userBio: String) {
        userApi.updateUserBio(userBio)
    }

    suspend fun getFriendRequests(): Call<List<FriendRequest>> {
        return userApi.getFriendRequests()
    }

    suspend fun sendFriendRequest(netid: String) {
        userApi.sendFriendRequest(netid)
    }

    suspend fun respondToFriendRequest(id: String, accept: String) {
        userApi.respondToFriendRequest(id, accept)
    }

    suspend fun deleteFriend(netid: String) {
        userApi.deleteFriend(netid)
    }

    suspend fun getAskListings(): Response<List<Listing>> {
        return userApi.getAskListings()
    }

    suspend fun getGiveListing(): Response<List<Listing>> {
        return userApi.getGiveListings()
    }

    suspend fun getListingById(id: String): Response<Listing> {
        return userApi.getListingById(id)
    }

    suspend fun createListing(listing: Listing) {
        userApi.createListing(listing)
    }

    suspend fun updateListing(id: String, listing: Listing) {
        userApi.updateListing(id, listing)
    }

    suspend fun deleteListing(id: String) {
        userApi.deleteListing(id)
    }
}
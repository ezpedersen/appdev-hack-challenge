    package com.example.frontend.data

    import retrofit2.Response
    import retrofit2.http.GET
    import retrofit2.http.Headers
    import retrofit2.http.Query
    import com.example.frontend.data.User
    import retrofit2.Call
    import retrofit2.http.Body
    import retrofit2.http.DELETE
    import retrofit2.http.POST
    import retrofit2.http.PUT
    import retrofit2.http.Path

    interface ApiService {
        @Headers("Accept: application/json")
        @GET("/user/{netid}")
        suspend fun getUserByNetid(@Path("netid") netid: String): Response<User>

        @Headers("Accept: application/json")
        @GET("/user/uuid/{uuid}")
        suspend fun getUserByUuid(@Path("uuid") uuid: String): Response<User>

        @Headers("Accept: application/json")
        @POST("/user")
        suspend fun createUser(@Body user: User): Call<User>

        @Headers("Accept: application/json")
        @DELETE("/user")
        suspend fun deleteUser(): Call<Void>

        @Headers("Accept: application/json")
        @PUT("/user")
        suspend fun updateUserBio(@Body userBio: String): Call<String>

        // Friend Routes
        @Headers("Accept: application/json")
        @GET("/friend")
        suspend fun getFriendRequests(): Call<List<FriendRequest>>

        @Headers("Accept: application/json")
        @POST("/friend")
        suspend fun sendFriendRequest(@Body netId : String): Call<Void>

        @Headers("Accept: application/json")
        @PUT("/friend/{id}")
        suspend fun respondToFriendRequest(@Path("id") id: String, @Body accept : String): Call<Void>

        @Headers("Accept: application/json")
        @DELETE("/friend")
        suspend fun deleteFriend(@Body netid: String): Call<Void>

        // Listing Routes
        @Headers("Accept: application/json")
        @GET("/listing/asks")
        suspend fun getAskListings(): Response<List<Listing>>

        @Headers("Accept: application/json")
        @GET("/listing/gives")
        suspend fun getGiveListings(): Response<List<Listing>>

        @Headers("Accept: application/json")
        @GET("/listing/{id}")
        suspend fun getListingById(@Path("id") id: String): Response<Listing>

        @Headers("Accept: application/json")
        @POST("/listing")
        suspend fun createListing(@Body listing: Listing): Call<Listing>

        @Headers("Accept: application/json")
        @PUT("/listing/{id}")
        suspend fun updateListing(@Path("id") id: String, @Body listing: Listing): Call<Listing>

        @Headers("Accept: application/json")
        @DELETE("/listing/{id}")
        suspend fun deleteListing(@Path("id") id: String): Call<Void>
    }
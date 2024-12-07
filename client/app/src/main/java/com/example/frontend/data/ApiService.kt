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
        fun getUserByNetid(@Path("netid") netid: String): Response<User>

        @Headers("Accept: application/json")
        @GET("/user/uuid/{uuid}")
        fun getUserByUuid(@Path("uuid") uuid: String): Response<User>

        @Headers("Accept: application/json")
        @POST("/user")
        fun createUser(@Body user: User): Call<User>

        @Headers("Accept: application/json")
        @DELETE("/user")
        fun deleteUser(): Call<Void>

        @Headers("Accept: application/json")
        @PUT("/user")
        fun updateUserBio(@Body userBio: String): Call<String>

        // Friend Routes
        @Headers("Accept: application/json")
        @GET("/friend")
        fun getFriendRequests(): Call<List<FriendRequest>>

        @Headers("Accept: application/json")
        @POST("/friend")
        fun sendFriendRequest(@Body friendRequest: FriendRequest): Call<Void>

        @Headers("Accept: application/json")
        @PUT("/friend/{id}")
        fun respondToFriendRequest(@Path("id") id: String, @Body response: FriendResponse): Call<Void>

        @Headers("Accept: application/json")
        @DELETE("/friend")
        fun deleteFriend(@Body netid: String): Call<Void>

        // Listing Routes
        @Headers("Accept: application/json")
        @GET("/listing/asks")
        fun getAskListings(): Call<List<Listing>>

        @Headers("Accept: application/json")
        @GET("/listing/gives")
        fun getGiveListings(): Call<List<Listing>>

        @Headers("Accept: application/json")
        @GET("/listing/{id}")
        fun getListingById(@Path("id") id: String): Call<Listing>

        @Headers("Accept: application/json")
        @POST("/listing")
        fun createListing(@Body listing: ListingRequest): Call<ListingResponse>

        @Headers("Accept: application/json")
        @PUT("/listing/{id}")
        fun updateListing(@Path("id") id: String, @Body listing: ListingUpdateRequest): Call<ListingResponse>

        @Headers("Accept: application/json")
        @DELETE("/listing/{id}")
        fun deleteListing(@Path("id") id: String): Call<Void>
        suspend fun getUserById(@Query("netId") netId : String): Response<User>
    }
    package com.example.frontend.data

    import com.google.android.gms.common.api.Api
    import retrofit2.Response
    import retrofit2.http.GET
    import retrofit2.http.Header
    import retrofit2.http.Headers
    import retrofit2.http.Query
    import com.example.frontend.data.User

    interface UserApi{
        @GET("/users/:netid")
        @Headers("Accept: application/json")
        suspend fun getUserById(@Query("netId") netId : String): Response<User>
    }
package api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteService {
    fun remoteCall(baseUrl:String) : RemoteInterface{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RemoteInterface::class.java)
    }
}
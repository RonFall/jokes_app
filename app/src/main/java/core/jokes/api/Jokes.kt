package core.jokes.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class Jokes(var title: String, var short_post: String, var author: String, var date: String)
data class JokesResponse(var error: Boolean, var data: ArrayList<Jokes>, @SerializedName("error_data") var errorData: Error?)

interface JokesService {
    @GET("random/{count}")
    fun getJokes(@Path("count") id: String): Call<JsonBase>
}

data class JsonBase (
    @SerializedName("type") val type : String,
    @SerializedName("value") val value : ArrayList<Value>
)

data class Value (
    @SerializedName("id") val id : Int,
    @SerializedName("joke") val joke : String,
    @SerializedName("categories") val categories : List<String>
)
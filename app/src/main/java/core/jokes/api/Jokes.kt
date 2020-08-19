package core.jokes.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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
package core.jokes.api

import com.google.gson.Gson
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.HttpCookie
import java.net.URI

data class Error(var code: Int, var text: String)

object API {
    lateinit var manager: CookieManager

    lateinit var jokesService: JokesService

    fun initApi() {
        manager = CookieManager()
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

        println("CookieManager initialized!")

        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(manager))
            .build()

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.icndb.com/jokes/")
            .client(client)
            .build().also {
                jokesService = it.create(JokesService::class.java)
            }
    }

    fun <T> getBodyFromResponse(res: Response<T>, cls: Class<T>): T? {
        return if (res.isSuccessful) res.body()
        else Gson().fromJson(res.errorBody()!!.string(), cls)
    }

    fun getCookie(uri: URI, name: String): HttpCookie? {
        val uriStore = manager.cookieStore.get(uri)
        uriStore.forEach { cookie ->
            if (cookie.name == name) {
                return cookie
            }
        }
        return null
    }

    fun <T> responseAllData(responseType: Class<T>, dataFunction: Call<T>, onResponse: (body: T) -> Any) {
        dataFunction.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = getBodyFromResponse(response, responseType)
                onResponse(body!!)
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
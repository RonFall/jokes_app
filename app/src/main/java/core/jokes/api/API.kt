package core.jokes.api

import com.google.gson.Gson
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy

object API {
    lateinit var manager: CookieManager

    lateinit var jokesService: JokesService

    fun initApi() {
        manager = CookieManager()
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

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
}
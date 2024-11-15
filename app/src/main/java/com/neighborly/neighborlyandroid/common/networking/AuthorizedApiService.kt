package com.neighborly.neighborlyandroid.common.networking


import TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.common.models.User
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import com.neighborly.neighborlyandroid.market.models.MarketSearchResponse
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiRequest
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiResponse
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

class AuthInterceptor(private val token: String?) : Interceptor {
    // Appends the auth token to a request if the token exists in DataStore

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response{
        // if token="" that means there is no token in the dataStore
        return if (token ==""){
            chain.proceed(chain.request().newBuilder().build())

        }else{
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()
            chain.proceed(request)
        }

    }
}
class AuthorizedApiServiceImpl(private val tokenDataStore: TokenDataStore) : AuthorizedApiService {

    private var token : String
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/"
    }
    init {
        // get the token from the datastore
        runBlocking {
            token = tokenDataStore.getToken() // Directly assign the result
            Log.i("logs","Auth Token: "+token)
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val authOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(token))
        .addInterceptor(loggingInterceptor)
        .build()
    private val authorizedRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(authOkHttpClient)
        .build()
    private val authorizedApiService = authorizedRetrofit.create(AuthorizedApiService::class.java)

    @GET("/main/")
    override suspend fun getUserInfo(): Response<User> {
        return authorizedApiService.getUserInfo()
    }
    @GET("/market/")
    override suspend fun requestMarketItems(@Query("searchQuery") request: MarketSearchRequest): Response<MarketSearchResponse> {
        val response = authorizedApiService.requestMarketItems(request)

        if (response.isSuccessful) {
            Log.i("Logs","Market items requested succesfully")
        }
        return response
    }

}
interface AuthorizedApiService{

    @GET("/main/")
    suspend fun getUserInfo(): Response<User>
    @GET("/market/")
    suspend fun requestMarketItems(@Query("searchQuery") request:MarketSearchRequest):Response<MarketSearchResponse>
}
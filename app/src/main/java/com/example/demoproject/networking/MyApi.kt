package com.example.demoproject.networking


import com.example.demoproject.model.network.ContentResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit


interface MyApi {


    @GET("test.json")
    suspend fun getContentData(): Response<ContentResponse>



    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {




            val interceptor = okhttp3.logging.HttpLoggingInterceptor()
            interceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY

            val tlsTocketFactory = TLSSocketFactory()

            val builder = OkHttpClient().newBuilder()
                .callTimeout(2, TimeUnit.MINUTES)
            builder.readTimeout(100, TimeUnit.SECONDS)
            builder.connectTimeout(100, TimeUnit.SECONDS)
            builder.sslSocketFactory(tlsTocketFactory, tlsTocketFactory.trustManager)
            builder.hostnameVerifier { _, session -> true }

            builder.addInterceptor(networkConnectionInterceptor)
            builder.addInterceptor(interceptor)


            builder.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request = chain.request().newBuilder()
                       /* .addHeader(
                            "Authorization",
                            "${App.preference?.getString(PrefUtil.AUTH_TOKEN)}"
                        )*/
                        /*   .addHeader("Host", "<calculated when request is sent>")*/
                        .build()
                    return chain.proceed(request)
                }
            })
            val client = builder.build()


            return Retrofit.Builder()
                .client(client)
                .baseUrl(WebServiceUrl.BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build()
                .create(MyApi::class.java)
        }
    }



}

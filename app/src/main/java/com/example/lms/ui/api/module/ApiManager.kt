package com.example.lms.ui.api.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object{
        private var retrofit : Retrofit?= null
        private fun getInstance() : Retrofit {
            if(retrofit == null)
            {
                retrofit = Retrofit.Builder()
                    //.baseUrl("https://nabilramadan.bsite.net")
                    .baseUrl("https://lms.runasp.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        fun getApi():UserServices{
            return getInstance().create(UserServices::class.java)
        }
    }
}
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.security.cert.X509Certificate
//import javax.net.ssl.SSLContext
//import javax.net.ssl.TrustManager
//import javax.net.ssl.X509TrustManager
//
//class ApiManager {
//    companion object {
//        private var retrofit: Retrofit? = null
//
//        init {
//            setupRetrofit()
//        }
//
//        private fun setupRetrofit() {
//            // Create a custom TrustManager that accepts all certificates
//            val trustAllCertificates: Array<TrustManager> = arrayOf(
//                object : X509TrustManager {
//                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
//                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
//                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
//                }
//            )
//
//            // Set up SSL context to use the custom TrustManager
//            val sslContext = SSLContext.getInstance("SSL")
//            sslContext.init(null, trustAllCertificates, java.security.SecureRandom())
//
//            // Create an OkHttpClient with the custom SSL context
//            val okHttpClient = OkHttpClient.Builder()
//                .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
//                .hostnameVerifier { _, _ -> true } // Accept all hostnames
//                .addInterceptor(HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY // Add logging interceptor
//                })
//                .build()
//
//            // Create Retrofit instance using the custom OkHttpClient
//            retrofit = Retrofit.Builder()
//                .baseUrl("https://lms.runasp.net")
//                //.baseUrl("https://nabilramadan.bsite.net")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//
//        fun getApi(): UserServices {
//            return retrofit!!.create(UserServices::class.java)
//        }
//    }
//}

package com.example.demoproject.utils

import android.content.Context
import android.os.StrictMode
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.demoproject.networking.MyApi
import com.example.demoproject.networking.NetworkConnectionInterceptor
import com.example.demoproject.repo.ContentRepository
import com.example.demoproject.ui.main.list.ListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class App : MultiDexApplication(), KodeinAware {


    companion object {

        var application: App? = null


        val TAG = "MyApp"


        fun getInstance(): App? {
            return application
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

    override fun onCreate() {
        super.onCreate()

        application = this

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())



    }


    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))


        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }

        bind() from singleton { ContentRepository(instance()) }

        bind() from provider { ListViewModelFactory(instance()) }



    }


}
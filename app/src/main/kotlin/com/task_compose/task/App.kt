package com.task_compose.task

import android.app.Application
import com.task_compose.dispatchers.provider.di.DispatchersModule
import com.task_compose.home.di.HomeModule
import com.task_compose.network.impl.di.DataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(modules)
        }
    }

    private val modules = listOf(
        AppModule().module,
        DataModule().module,
        DispatchersModule().module,
        HomeModule().module
    )
}
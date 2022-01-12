package io.coreflodev.dog

import android.app.Application
import android.content.Context
import io.coreflodev.dog.common.di.AppComponent
import io.coreflodev.dog.common.di.AppModule
import io.coreflodev.dog.common.di.DaggerAppComponent

class DogApp : Application() {

    private val applicationComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(baseContext))
            .build()
    }

    companion object {

        fun appComponent(context: Context) = (context.applicationContext as DogApp).applicationComponent
    }
}

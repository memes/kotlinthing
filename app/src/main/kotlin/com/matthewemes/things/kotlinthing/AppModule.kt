package com.matthewemes.things.kotlinthing

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule(val app: KotlinThing){
    @Provides
    @Singleton
    fun provideApp() = app
}
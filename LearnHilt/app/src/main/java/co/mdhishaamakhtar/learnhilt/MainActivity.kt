package co.mdhishaamakhtar.learnhilt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Qualifier

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing1())
        println(someClass.doAThing2())
    }
}

class SomeClass
@Inject
constructor(
        @Impl1 private val someImpl1: SomeInterface,
        @Impl2 private val someImpl2: SomeInterface,
        private val gson: Gson
) {
    fun doAThing1(): String {
        return "Hey I did a: ${someImpl1.getAThing()}"
    }

    fun doAThing2(): String {
        return "Hey I did a: ${someImpl2.getAThing()}"
    }
}

class SomeInterfaceImpl1
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing 1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing 2"
    }
}

interface SomeInterface {
    fun getAThing(): String
}

/**
 * As a rule, use provides for 3rd part
 * Use binds if we implement our own interface
 * */

//@InstallIn(ApplicationComponent::class)
//@Module
//abstract class MyModule {
//    @Binds
//    abstract fun bindSomeDependency(
//            someImpl: SomeInterfaceImpl
//    ): SomeInterface
//}

@InstallIn(ApplicationComponent::class)
@Module
class MyModule {
    @Provides
    @Impl1
    fun provideSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl1()
    }

    @Provides
    @Impl2
    fun provideSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2

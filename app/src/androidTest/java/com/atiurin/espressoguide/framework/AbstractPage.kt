package com.atiurin.espressoguide.framework

import com.google.gson.reflect.TypeToken

abstract class AbstractPage<T>(action: T.() -> Unit) : Page{
    init {
        this.run<AbstractPage<T>> { action }
    }

    inline fun <reified K : Page> run(block: K.() -> Unit){
        K::class.java.newInstance().block()
    }
}

package com.atiurin.espressoguide.framework

abstract class BasePage<out T : BasePage<T>> : Page{
    inline fun <reified T : BasePage<T>> doOnPage(noinline function: T.() -> Unit): T {
        return T::class.java
            .newInstance()
            .apply { this(function) }
    }

    inline operator fun <R> invoke(block: T.() -> R): R {
        return block.invoke(this as T)
    }
}

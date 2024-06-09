package com.quinti.android_step_template.kmp.data.api.inject

interface Provider<T> {
    fun get(): T
}

interface SingletonProvider<T> : Provider<T>

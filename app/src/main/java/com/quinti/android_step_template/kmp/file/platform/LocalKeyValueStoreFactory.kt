package com.quinti.android_step_template.kmp.file.platform

/**
 * LocalKeyValueStore Factory
 */
interface LocalKeyValueStoreFactory {
    companion object {
        /**
         * LocalKeyValueStoreFactory の取得
         */
        lateinit var instance: LocalKeyValueStoreFactory
    }

    /**
     * LocalKeyValueStore を生成する
     */
    fun create(storeName: String): LocalKeyValueStore
}
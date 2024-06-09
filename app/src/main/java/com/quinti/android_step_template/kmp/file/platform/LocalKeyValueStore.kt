package com.quinti.android_step_template.kmp.file.platform

/**
 * 端末内の簡易 KVS
 *
 * * 以下の実装を想定する
 *     * Android は SharedPreferences
 *     * iOS は UserDefaults
 *
 * いずれの機能もオンメモリキャッシュされる想定であるため、値の読み書きは同期インタフェースとしている
 */
interface LocalKeyValueStore {
    /**
     * 指定した値が存在するか
     */
    fun contains(key: String): Boolean

    /**
     * String 取得
     */
    fun string(key: String, defaultValue: String): String

    /**
     * String 取得
     */
    fun string(key: String): String?

    /**
     * Int 取得
     */
    fun integer(key: String, defaultValue: Int): Int

    /**
     * Int 取得
     */
    fun integer(key: String): Int?

    /**
     * Long 取得
     */
    fun long(key: String, defaultValue: Long): Long

    /**
     * Long 取得
     */
    fun long(key: String): Long?

    /**
     * Float 取得
     */
    fun float(key: String, defaultValue: Float): Float

    /**
     * Float 取得
     */
    fun float(key: String): Float?

    /**
     * Boolean 取得
     */
    fun boolean(key: String, defaultValue: Boolean): Boolean

    /**
     * Boolean 取得
     */
    fun boolean(key: String): Boolean?

    /**
     * String 設定
     */
    fun set(key: String, value: String)

    /**
     * Int 設定
     */
    fun set(key: String, value: Int)

    /**
     * Long 設定
     */
    fun set(key: String, value: Long)

    /**
     * Float 設定
     */
    fun set(key: String, value: Float)

    /**
     * Boolean 設定
     */
    fun set(key: String, value: Boolean)

    /**
     * 値を削除する
     */
    fun remove(key: String)
}
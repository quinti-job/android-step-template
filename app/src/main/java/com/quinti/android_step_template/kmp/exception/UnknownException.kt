package com.quinti.android_step_template.kmp.exception

/**
 * 未知の例外をwrapするクラス
 */
class UnknownException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

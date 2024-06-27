package com.quinti.android_step_template

import androidx.fragment.app.Fragment
import okhttp3.Route

interface TopFragmentFactory {
    fun create(route: Route): Fragment
}
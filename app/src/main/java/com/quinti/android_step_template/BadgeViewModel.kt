package com.quinti.android_step_template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.quinti.android_step_template.kmp.domain.usecase.WatchNeedShowRewardTabIndicatorUseCase
import javax.inject.Inject

class BadgeViewModel @Inject constructor(
    watchNeedShowRewardTabIndicator: WatchNeedShowRewardTabIndicatorUseCase,
) : ViewModel() {

    val badgeCount = MutableLiveData<Int>()
    val isRewardTabIndicatorVisible: LiveData<Boolean> =
        watchNeedShowRewardTabIndicator().asLiveData(viewModelScope.coroutineContext)

    fun updateBadge(count: Int) {
        badgeCount.value = count
    }
}
package com.quinti.android_step_template.di

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.produceViewModels(
    noinline factory: (SavedStateHandle) -> VM,
): Lazy<VM> = viewModels { ActivitySavedStateViewModelFactory(this, factory) }

@MainThread
inline fun <reified VM : ViewModel> Fragment.produceViewModels(
    noinline factory: (SavedStateHandle) -> VM,
): Lazy<VM> = createViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { viewModelStore },
    factoryProducer = { FragmentSavedStateViewModelFactory(this, factory) },
)

@MainThread
inline fun <reified VM : ViewModel> Fragment.produceActivityViewModels(
    noinline factory: (SavedStateHandle) -> VM,
): Lazy<VM> = createViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { requireActivity().viewModelStore },
    factoryProducer = { ActivitySavedStateViewModelFactory(requireActivity(), factory) },
)

@MainThread
inline fun <reified VM : ViewModel> Fragment.produceParentViewModels(
    noinline factory: (SavedStateHandle) -> VM,
): Lazy<VM> = createViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { requireParentFragment().viewModelStore },
    factoryProducer = { FragmentSavedStateViewModelFactory(requireParentFragment(), factory) },
)

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModels(): Lazy<VM> = createViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { requireParentFragment().viewModelStore },
)

class ActivitySavedStateViewModelFactory<VM : ViewModel>(
    activity: ComponentActivity,
    private val factory: (SavedStateHandle) -> VM,
) : AbstractSavedStateViewModelFactory(activity, activity.intent?.extras) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T = factory(handle) as T
}

class FragmentSavedStateViewModelFactory<VM : ViewModel>(
    fragment: Fragment,
    private val factory: (SavedStateHandle) -> VM,
) : AbstractSavedStateViewModelFactory(fragment, fragment.arguments) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T = factory(handle) as T
}

class ViewModelFactory<VM : ViewModel> @Inject constructor(
    private val viewModel: Provider<VM>,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel.get() as T
}
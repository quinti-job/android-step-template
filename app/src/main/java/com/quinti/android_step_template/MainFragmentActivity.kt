package com.quinti.android_step_template

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint


import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import co.kyash.MainViewModel.Event
import co.kyash.databinding.MainActivityBinding
import co.kyash.di.produceViewModels
import co.kyash.extension.getNotificationSettings
import co.kyash.mobile.core.featureflag.ReleaseFlag
import co.kyash.model.entity.WalletId
import co.kyash.ui.account.AccountFragmentFactory
import co.kyash.ui.account.NotificationSettingFragmentFactory
import co.kyash.ui.badge.BadgeViewModel
import co.kyash.ui.base.BaseWithoutDaggerSupportActivity
import co.kyash.ui.base.delegate.ExtraNullable
import co.kyash.ui.deeplink.Deeplink
import co.kyash.ui.extension.requestInAppReview
import co.kyash.ui.main.CouponFragmentFactory
import co.kyash.ui.main.MainPageFragment
import co.kyash.ui.main.Route
import co.kyash.ui.navigator.ActivityNavigator
import co.kyash.ui.navigator.Router
import co.kyash.ui.passcode.SecuritySettingFragment
import co.kyash.ui.redeem.RedeemFragmentFactory
import co.kyash.ui.reward.compose.CoachMark
import co.kyash.ui.util.NotificationUtility
import co.kyash.ui.wallet.WalletFragmentFactory
import co.kyash.ui.wallet.WalletRoute
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.quinti.android_step_template.di.produceViewModels
import com.quinti.android_step_template.ui.MainViewModel
import com.quinti.android_step_template.ui.navigator.Router
import com.quinti.android_step_template.util.NotificationUtility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.launch
import me.leolin.shortcutbadger.ShortcutBadger
import okhttp3.Route
import timber.log.Timber

@AndroidEntryPoint
class MainFragmentActivity : FragmentActivity() {
    @Inject
    override lateinit var navigator: ActivityNavigator

    @Inject
    lateinit var router: Router

    @Inject
    override lateinit var notificationUtility: NotificationUtility

    @Inject
    lateinit var viewModelProvider: Provider<MainViewModel>
    private val viewModel: MainViewModel by produceViewModels {
        viewModelProvider.get()
    }

    @Inject
    lateinit var badgeViewModelProvider: Provider<BadgeViewModel>
    private val badgeViewModel: BadgeViewModel by produceViewModels {
        badgeViewModelProvider.get()
    }

    @Inject
    lateinit var topFragmentFactory: TopFragmentFactory

    private lateinit var binding: MainActivityBinding

    val route: Route? by ExtraNullable()
    val selectedWalletId: WalletId? by ExtraNullable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(
                com.google.android.material.R.id.container
            ) as NavHostFragment
        val navController = navHostFragment.findNavController()
        setupNavGraph(navController = navController)
    }

    private fun setupNavGraph(
        navController: NavController
    ) {
        val graph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        navController.setGraph(graph, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // ~ Android 9 では Navigation 半透明を明示的に指定する
            window.navigationBarColor = getColor(co.kyash.core.R.color.edge_to_edge_nav_bar_dark)
        }

        clearNotificationIfNeeded()

        viewModel.onEvent.observe(this, ::handleEvent)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.isWalletTab = (destination.id == co.kyash.wallet.ui.R.id.wallet)
            val isTabTop = destination.id in listOf(
                co.kyash.wallet.ui.R.id.wallet,
                co.kyash.reward.R.id.coupon,
                co.kyash.household.R.id.household,
                co.kyash.sendrequest.R.id.sendRequest,
                co.kyash.account.ui.R.id.account,
            )
            viewModel.setIsTabTopScreen(isTabTop)
            if (destination.id == co.kyash.reward.R.id.coupon) {
                viewModel.hideRewardTabCoachMark()
            }
        }

        binding.bottomBar.apply {
            if (savedInstanceState == null) {
                // DeepLinkによって別タブに遷移する可能性があるのでsetupNavGraphより前に行う必要がある
                selectedItemId = route?.tabId ?: co.kyash.core.R.id.wallet_nav_graph
            }
            setupWithNavController(navController)
            setOnItemReselectedListener {
                val fragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                (fragment as? MainPageFragment)?.onMainPageReselected()
            }
        }
        setupNavGraph(navController = navController)
        if (savedInstanceState == null) {
            handleDeeplink()
        }

        badgeViewModel.badgeCount.observe(this) {
            ShortcutBadger.applyCount(this, it)
            val bar = binding.bottomBar
            if (it <= 0) {
                bar.removeBadge(co.kyash.core.R.id.wallet_nav_graph)
            } else {
                showBadge(co.kyash.core.R.id.wallet_nav_graph, it)
            }
        }

        badgeViewModel.isRewardTabIndicatorVisible.observe(this) {
            binding.bottomBar.getOrCreateBadge(co.kyash.reward.R.id.coupon_nav_graph).apply {
                isVisible = it
            }
        }

        val coachMarkComposeView = findViewById<ComposeView>(R.id.compose_view)
        coachMarkComposeView.setContent {
            CoachMark(
                onClose = {
                    viewModel.hideRewardTabCoachMark()
                },
            )
        }

        viewModel.init(getNotificationSettings())
    }

    override fun onResume() {
        super.onResume()

        viewModel.onRefreshNotificationSetting()
    }

    private fun setupNavGraph(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.main_nav_graph)

        // Payroll開始までPayroll関連のNavGraphを全て削除
        if (!releaseFlag.isPayrollEnabled) {
            val payrollAccountNavGraph =
                graph.findNode(co.kyash.payroll.R.id.payroll_account_nav_graph) as NavGraph
            val payrollAccountRegistrationNavGraph =
                graph.findNode(co.kyash.payroll.R.id.payroll_account_registration_nav_graph) as NavGraph
            val payrollEvacuationAccountRegistrationNavGraph =
                graph.findNode(
                    co.kyash.payroll.R.id.payroll_evacuation_account_registration_nav_graph,
                ) as NavGraph
            val payrollEvacuationAccountSelectionNavGraph =
                graph.findNode(co.kyash.payroll.R.id.payroll_evacuation_account_selection_nav_graph) as NavGraph

            graph.remove(payrollAccountNavGraph)
            graph.remove(payrollAccountRegistrationNavGraph)
            graph.remove(payrollEvacuationAccountRegistrationNavGraph)
            graph.remove(payrollEvacuationAccountSelectionNavGraph)
        }

        // WalletFragment 引数の設定
        val args = bundleOf(
            "route" to ((route as? Route.OpenWallet)?.inWallet ?: WalletRoute.None),
            "walletId" to selectedWalletId,
        )
        // setGraph() により MainActivity.intent.data の DeepLink が自動的に解釈される
        navController.setGraph(graph, args)
    }

    /**
     * AndroidX Navigation 管理外の DeepLink を解釈する
     */
    private fun handleDeeplink() {
        if (intent.getBooleanExtra(Deeplink.IS_DEEP_LINK, false)) {
            val uri = intent.getStringExtra(Deeplink.URI)!!
            handleDeepLinkUri(uri)
        } else {
            // not deep link
            viewModel.onCreateWithoutDeepLink()

            // Check NOTIFICATION_PREFERENCES
            if (intent.categories != null && intent.categories.contains(
                    Notification.INTENT_CATEGORY_NOTIFICATION_PREFERENCES,
                )
            ) {
                binding.bottomBar.selectedItemId = co.kyash.account.ui.R.id.account_nav_graph
                openNotificationSetting()
            }
        }
    }

    private fun showBadge(@IdRes id: Int, number: Int) {
        val drawable = binding.bottomBar.getOrCreateBadge(id)
        drawable.number = number
        drawable.backgroundColor = ContextCompat.getColor(this, co.kyash.core.R.color.orange)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager
            .primaryNavigationFragment
            ?.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleDeepLinkUri(uri: String) {
        when (uri) {
            "kyash://secured/spend" -> {
                binding.bottomBar.selectedItemId = co.kyash.core.R.id.wallet_nav_graph
            }

            "kyash://secured/account" -> {
                binding.bottomBar.selectedItemId = co.kyash.account.ui.R.id.account_nav_graph
            }

            "kyash://secured/account/security" -> {
                binding.bottomBar.selectedItemId = co.kyash.account.ui.R.id.account_nav_graph
                openSecurity()
            }

            "kyash://secured/account/redeem" -> {
                binding.bottomBar.selectedItemId = co.kyash.account.ui.R.id.account_nav_graph
                openRedeem()
            }

            else -> {
                // kyash://secured/profile/123
                if (uri.startsWith("kyash://secured/profile/")) {
                    val publicId = intent.extras?.getString("publicId")
                    if (publicId != null) {
                        try {
                            viewModel.showUserDialog(publicId.toLong())
                        } catch (e: NumberFormatException) {
                            Timber.e(e)
                        }
                    }
                }
                // kyash://qr/u/123
                if (uri.startsWith("kyash://qr/u/")) {
                    val publicId = intent.getStringExtra("publicId")!!
                    val action = intent.getStringExtra("action")
                    if (action.isNullOrEmpty()) {
                        try {
                            viewModel.showUserDialog(publicId.toLong())
                        } catch (e: NumberFormatException) {
                            Timber.e(e)
                        }
                    } else {
                        val amount = intent.getStringExtra("amount")
                        val message = intent.getStringExtra("message")
                        try {
                            viewModel.goToSendOrRequest(
                                publicId.toLong(),
                                action,
                                amount?.toLong(),
                                message,
                            )
                        } catch (e: NumberFormatException) {
                            Timber.e(e)
                        }
                    }
                }
            }
        }
    }

    private fun openSecurity() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, SecuritySettingFragment.newInstance())
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun openRedeem() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, redeemFragmentFactory.create())
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun openNotificationSetting() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, notificationSettingFragmentFactory.create())
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun handleEvent(event: Event) {
        when (event) {
            Event.RequestAppReview -> lifecycleScope.launch {
                requestInAppReview()
            }

            is Event.FailedAuth -> navigator.handleOnlyAuthError(event.throwable)
            is Event.NavigateToImportantNoticeForKyashValueTerms ->
                navigator.navigateToImportantNoticeForKyashValueTerms(
                    event.status,
                )

            Event.NavigateToImportantNoticeForIdentity -> navigator.navigateToImportantNoticeForIdentity()
            is Event.ShowUserDialog -> navigator.showUserDialog(event.user)
            is Event.NavigateToSend -> navigator.navigateToSend(
                event.user,
                event.amount,
                event.message,
            )

            is Event.NavigateToRequest -> navigator.navigateToRequest(
                event.user,
                event.amount,
                event.message,
                null,
            )

            Event.ShowInformationRequiredForTransactionsIsNotFilledDialog -> {
                router.showAlertDialog(
                    title = co.kyash.core.R.string.error,
                    message = co.kyash.ui.core.R.string.core_information_required_for_transactions_is_not_filled,
                    ok = co.kyash.core.R.string.ok,
                )
            }
        }
    }

    companion object {
        fun createIntent(
            context: Context,
            route: TabRoute = TabRoute.OpenWallet(),
            selectedWalletId: WalletId? = null,
        ): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent.also {
                it.putExtra(MainActivity::route.name, route)
                it.putExtra(MainActivity::selectedWalletId.name, selectedWalletId)
            }
        }
    }
}


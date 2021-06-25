package com.jmm.brsap.scrap24x7.ui.customer.dashboard

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.AccountSettingChildAdapter
import com.jmm.brsap.scrap24x7.adapters.AccountSettingParentAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentAccountBinding
import com.jmm.brsap.scrap24x7.model.ModelMenuItem
import com.jmm.brsap.scrap24x7.model.ModelParentMenu
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.MainActivity
import com.jmm.brsap.scrap24x7.ui.customer.account.AccountActivity
import com.jmm.brsap.scrap24x7.util.MenuEnum
import com.jmm.brsap.scrap24x7.viewmodel.customer.CustomerAccountViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Account : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate), AccountSettingChildAdapter.AccountSettingChildInterface {

    //UI

    //ViewModels
    private val viewModel by viewModels<CustomerAccountViewModel>()

    // Adapters
    private lateinit var accountSettingParentAdapter: AccountSettingParentAdapter

    // Variable
    private var userName = StringBuilder()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        accountSettingParentAdapter = AccountSettingParentAdapter(getAccountMenuList(), this)
        binding.rvAccountMenuList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = accountSettingParentAdapter
        }
    }

    private fun getAccountMenuList(): List<ModelParentMenu> {
        val menuList = mutableListOf<ModelParentMenu>()

        val settingMenuItemList = mutableListOf<ModelMenuItem>()
        settingMenuItemList.add(
                ModelMenuItem(
                        MenuEnum.PROFILE,
                        "Profile",
                        "change and update your profile data",
                        R.drawable.ic_baseline_person_24
                )
        )
//        settingMenuItemList.add(ModelMenuItem(MenuEnum.NOTIFICATION,"Notifications","Change notification settings",R.drawable.ic_round_notifications_24))
//        settingMenuItemList.add(
//            ModelMenuItem(
//                MenuEnum.LANGUAGE,
//                "Languages",
//                "Choose app language",
//                R.drawable.ic_round_notifications_24
//            )
//        )
        settingMenuItemList.add(
                ModelMenuItem(
                        MenuEnum.ADDRESS_SETTINGS,
                        "Address",
                        "Manage your address details",
                        R.drawable.ic_round_location_on_24
                )
        )
        settingMenuItemList.add(
                ModelMenuItem(
                        MenuEnum.PAYMENT_METHOD,
                        "Payment",
                        "Payment method settings",
                        R.drawable.ic_round_payment_24
                )
        )

        val settingMenu = ModelParentMenu("Settings", settingMenuItemList)

        val helpMenuItemList = mutableListOf<ModelMenuItem>()
//        helpMenuItemList.add(ModelMenuItem(MenuEnum.FAQ, "FAQ", "", R.drawable.ic_round_help_24))
        helpMenuItemList.add(ModelMenuItem(MenuEnum.HOW_IT_WORKS, "How it works?", "", R.drawable.ic_round_help_24))
        helpMenuItemList.add(
                ModelMenuItem(
                        MenuEnum.HELP_CENTRE,
                        "Call Us",
                        "",
                        R.drawable.ic_round_phone_24
                )
        )
        helpMenuItemList.add(
                ModelMenuItem(
                        MenuEnum.ABOUT,
                        "About Us",
                        "",
                        R.drawable.ic_round_info_24
                )
        )
        helpMenuItemList.add(
                ModelMenuItem(
                        MenuEnum.SHARE,
                        "Share",
                        "",
                        R.drawable.ic_baseline_share_24
                )
        )
        helpMenuItemList.add(
                ModelMenuItem(
                        MenuEnum.LOGOUT,
                        "Log out",
                        "",
                        R.drawable.ic_round_logout_24
                )
        )

        val helpMenu = ModelParentMenu("Help", helpMenuItemList)

        menuList.add(settingMenu)
        menuList.add(helpMenu)
        return menuList
    }

    override fun onMenuClick(menu: ModelMenuItem) {
        when (menu.id) {

            MenuEnum.LOGOUT -> {
                val dialogClickListener =
                        DialogInterface.OnClickListener { dialog, which ->
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    viewModel.clearUserInfo()
                                    val intent = Intent(requireActivity(), MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    requireActivity().finish()
                                    dialog.dismiss()

                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                }
                            }
                        }
                showAlertDialog("Do you really want to log out?", dialogClickListener)


            }
            MenuEnum.HELP_CENTRE -> {
                val i = Intent(Intent.ACTION_DIAL)
                val p = "tel:" + getString(R.string.phone_number)
                i.data = Uri.parse(p)
                startActivity(i)
            }
            else -> {
                val intent = Intent(requireActivity(), AccountActivity::class.java)
                intent.putExtra("SOURCE", menu.id)
                startActivity(intent)
            }
        }


    }

    override fun subscribeObservers() {


        viewModel.firstName.observe(viewLifecycleOwner, {
            userName.clear()
            userName.append(it)
            userName.append(" ")
        })
        viewModel.lastName.observe(viewLifecycleOwner, {
            userName.append(it)
            binding.tvUserName.text = userName.toString()
        })


    }

}
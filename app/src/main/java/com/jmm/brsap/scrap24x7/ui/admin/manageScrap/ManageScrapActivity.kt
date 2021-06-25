package com.jmm.brsap.scrap24x7.ui.admin.manageScrap

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.SingleTitleRowAdapter
import com.jmm.brsap.scrap24x7.databinding.ActivityManageScrapBinding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.ui.admin.ADD_VEHICLE
import com.jmm.brsap.scrap24x7.ui.admin.UPDATE_VEHICLE
import com.jmm.brsap.scrap24x7.ui.admin.manageUser.UserDetailSheet
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageScrapActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManageScrapActivity : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener,
    SingleTitleRowAdapter.SingleTitleRowInterface {

    private lateinit var binding: ActivityManageScrapBinding
    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY
    private val viewModel by viewModels<ManageScrapActivityViewModel>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var singleTitleRowAdapter: SingleTitleRowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageScrapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                if (result.resultCode== RESULT_OK){
                    showToast(data!!.getStringExtra("Message")!!)
                }
                else{
                    showToast("Cancelled !!")
                }

            }
        binding.toolbarManageScrap.setApplicationToolbarListener(this)
        source = intent.getSerializableExtra("SOURCE") as AdminEnum
        setupRvData()
        subscribeObservers()
        when (source) {
            AdminEnum.MANAGE_SCRAP_CATEGORY -> {
                setToolbarTitle("Scrap Category")
                viewModel.getCategories()
            }
            AdminEnum.MANAGE_SCRAP_ITEMS -> {
                setToolbarTitle("Scrap Items")
                viewModel.getCategoryItems()
            }
            AdminEnum.MANAGE_UNIT -> {
                setToolbarTitle("Units")
                viewModel.getUnits()
            }
            else -> {
                //nothing
            }
        }

        binding.fabAdd.setOnClickListener {
            when (source) {
                AdminEnum.MANAGE_SCRAP_CATEGORY -> {
                    val intent = Intent(this, AddScrapCategory::class.java)
                    intent.putExtra("ACTION", AdminEnum.ADD)
                    resultLauncher.launch(intent)
                }
                AdminEnum.MANAGE_SCRAP_ITEMS -> {
                    // open bottom sheet
                    val bottomSheet = AddNewScrapItem()
                    val bundle = Bundle()
                    bundle.putSerializable("ACTION",AdminEnum.ADD)
                    bottomSheet.arguments = bundle
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                }
                AdminEnum.MANAGE_UNIT -> {
                    // open bottom sheet
                    val bottomSheet = AddNewUnit()
                    val bundle = Bundle()
                    bundle.putSerializable("ACTION",AdminEnum.ADD)
                    bottomSheet.arguments = bundle
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                }
            }
        }
    }

    private fun subscribeObservers(){
        viewModel.categories.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        singleTitleRowAdapter.setItemList(it)
                    }

                    displayLoading(false)
                }
                Status.LOADING -> {
                    displayLoading(true)
                }
                Status.ERROR -> {
                    displayLoading(false)
                    _result.message?.let {
                        displayError(it)
                    }
                }
            }
        })


        viewModel.categoryItems.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        singleTitleRowAdapter.setItemList(it)
                    }

                    displayLoading(false)
                }
                Status.LOADING -> {
                    displayLoading(true)
                }
                Status.ERROR -> {
                    displayLoading(false)
                    _result.message?.let {
                        displayError(it)
                    }
                }
            }
        })

        viewModel.units.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        singleTitleRowAdapter.setItemList(it)
                    }

                    displayLoading(false)
                }
                Status.LOADING -> {
                    displayLoading(true)
                }
                Status.ERROR -> {
                    displayLoading(false)
                    _result.message?.let {
                        displayError(it)
                    }
                }
            }
        })

    }
    private fun setupRvData() {
        singleTitleRowAdapter = SingleTitleRowAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = singleTitleRowAdapter
        }
    }

    private fun setToolbarTitle(title: String) {

        binding.toolbarManageScrap.setToolbarTitle(title)
    }


    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

    override fun onItemClick(item: Any) {
        when(item){
            is Category ->{
                val bottomSheet = ScrapDetailSheet()
                val bundle = Bundle()
                bundle.putInt("ID",item.id!!)
                bundle.putSerializable("TYPE",AdminEnum.CATEGORY)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
            is CategoryItem ->{
                val bottomSheet = ScrapDetailSheet()
                val bundle = Bundle()
                bundle.putInt("ID",item.id!!)
                bundle.putSerializable("TYPE",AdminEnum.CATEGORY_ITEM)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }

            is UnitModel ->{
                val bottomSheet = ScrapDetailSheet()
                val bundle = Bundle()
                bundle.putInt("ID",item.id!!)
                bundle.putSerializable("TYPE",AdminEnum.UNIT)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
        }
    }

    override fun onEditClick(item: Any) {
        when(item){
            is Category ->{
                val intent = Intent(this, AddScrapCategory::class.java)
                intent.putExtra("ACTION", AdminEnum.ADD)
                startActivity(intent)
            }
            is CategoryItem ->{
                val bottomSheet = AddNewScrapItem()
                val bundle = Bundle()
                bundle.putSerializable("ACTION",AdminEnum.EDIT)
                bundle.putInt("CategoryItemID",item.id!!)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }

            is UnitModel ->{
                val bottomSheet = AddNewUnit()
                val bundle = Bundle()
                bundle.putSerializable("ACTION",AdminEnum.EDIT)
                bundle.putInt("UnitModelID",item.id!!)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        }
    }

    override fun onDeleteClick(item: Any) {
        when(item){

            is Category->{
                
            }
            is CategoryItem->{
            }

            is UnitModel->{
            }
        }
    }
}
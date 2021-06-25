package com.jmm.brsap.scrap24x7.ui.customer.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jmm.brsap.scrap24x7.adapters.LanguageCategoryItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentChooseLanguageBinding
import com.jmm.brsap.scrap24x7.model.ModelLanguageCategoryItem


class ChooseLanguage : Fragment() {
    private  var _binding:FragmentChooseLanguageBinding? = null
    private val binding get() = _binding!!

    private lateinit var languageCategoryItemAdapter: LanguageCategoryItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentChooseLanguageBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageCategoryItemAdapter = LanguageCategoryItemAdapter(prepareLanguageCategoryItemList())
        binding.rvChooseLanguage.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,2)
            adapter = languageCategoryItemAdapter
        }

    }

    private fun prepareLanguageCategoryItemList():MutableList<ModelLanguageCategoryItem>{

        val categoryItemList = ArrayList<ModelLanguageCategoryItem>()
        categoryItemList.add(ModelLanguageCategoryItem("English","",true))
        categoryItemList.add(ModelLanguageCategoryItem("हिन्दी","Hindi"))
        categoryItemList.add(ModelLanguageCategoryItem("मराठी","Marathi"))
        categoryItemList.add(ModelLanguageCategoryItem("বাংলা","Bangla"))
        categoryItemList.add(ModelLanguageCategoryItem("ગુજરાતી","Gujarati"))
        categoryItemList.add(ModelLanguageCategoryItem("മലയാളം","Malayalam"))
        categoryItemList.add(ModelLanguageCategoryItem("தமிழ்","Tamil"))
        categoryItemList.add(ModelLanguageCategoryItem("ಕನ್ನಡ","Kannada"))

        return categoryItemList
    }


}
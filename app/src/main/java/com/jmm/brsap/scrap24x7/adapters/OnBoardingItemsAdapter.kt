package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplateContainerOnboardingBinding
import com.jmm.brsap.scrap24x7.model.OnBoardingItem


class OnBoardingItemsAdapter(private val onBoardingItems:List<OnBoardingItem>):RecyclerView.Adapter<OnBoardingItemsAdapter.OnBoardingItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            TemplateContainerOnboardingBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.createItem(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnBoardingItemViewHolder(private val binding:TemplateContainerOnboardingBinding):RecyclerView.ViewHolder(binding.root){



        fun createItem(onBoardingItem: OnBoardingItem){
            binding.apply {
                ivOnboarding.setImageResource(onBoardingItem.onBoardingImage)
                tvTitleOnboarding.text = onBoardingItem.onBoardingTitle
                tvDescriptionOnboarding.text = onBoardingItem.onBoardingDescription

            }
        }
    }
}
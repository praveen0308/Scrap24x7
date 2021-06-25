package com.jmm.brsap.scrap24x7.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.SwipeMenuItemBinding
import com.jmm.brsap.scrap24x7.databinding.ToolbarApplicationDisplayBinding
import com.jmm.brsap.scrap24x7.ui.admin.AddNewVehicle

class SwipeMenuItem @kotlin.jvm.JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private var binding = SwipeMenuItemBinding.inflate(LayoutInflater.from(context))


    init {
        addView(binding.root)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SwipeMenuItem)

        binding.root.setBackgroundColor(attributes.getColor(R.styleable.SwipeMenuItem_SwipeMenuItemBackground, Color.WHITE))
        if (attributes.getDrawable(R.styleable.SwipeMenuItem_SwipeMenuItemIcon) != null) {
            binding.icon.setImageDrawable(attributes.getDrawable(R.styleable.SwipeMenuItem_SwipeMenuItemIcon))
        }

        binding.tvTitle.text = attributes.getString(R.styleable.SwipeMenuItem_SwipeMenuItemText)

        attributes.recycle()

    }

    fun setTitle(text:String){
        binding.tvTitle.text = text
    }

    fun setIcon(imageUrl:Int){
        binding.icon.setImageResource(imageUrl)
    }

    fun setMenuBackgroundColor(color:Int){
        binding.root.setBackgroundColor(color)
    }

    fun setMenuTint(color:Int){
        binding.apply {
            binding.tvTitle.setTextColor(color)
            binding.icon.setColorFilter(color)
        }
    }
}
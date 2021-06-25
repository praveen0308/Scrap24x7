package com.jmm.brsap.scrap24x7.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ToolbarApplicationDisplayBinding
import com.jmm.brsap.scrap24x7.ui.admin.AddNewVehicle

class ApplicationToolbar @kotlin.jvm.JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private var binding = ToolbarApplicationDisplayBinding.inflate(LayoutInflater.from(context))
//    // UI
//    private var title: TextView
//    private var tvMenuBadge: TextView
//    private var navIcon: ImageView
//    private var menuIcon: ImageView
//    private var viewMenu: View


    // Variables
    private var badgeCount = 0

    private lateinit var mListener: ApplicationToolbarListener

    init {
        addView(binding.root)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ApplicationToolbar)

        if (attributes.getDrawable(R.styleable.ApplicationToolbar_ATNavIcon) != null) {
            binding.ivNavigationIcon.setImageDrawable(attributes.getDrawable(R.styleable.ApplicationToolbar_ATNavIcon))
        }

        if (attributes.getDrawable(R.styleable.ApplicationToolbar_ATMenuIcon) != null) {
            binding.layoutMenu.ivToolbarMenuIcon.setImageDrawable(attributes.getDrawable(R.styleable.ApplicationToolbar_ATMenuIcon))
        }

        when(attributes.getInt(R.styleable.ApplicationToolbar_ATTitleGravity,-1)){
            -1 -> binding.tvToolbarTitle.gravity = Gravity.START or Gravity.CENTER_VERTICAL
            1 -> binding.tvToolbarTitle.gravity = Gravity.END or Gravity.CENTER_VERTICAL
            0 -> binding.tvToolbarTitle.gravity = Gravity.CENTER
        }



        binding.tvToolbarTitle.text = attributes.getString(R.styleable.ApplicationToolbar_ATTitle)

        val titleSize = attributes.getDimensionPixelSize(R.styleable.ApplicationToolbar_ATTitleTextSize, 0);


        if (titleSize > 0) {
            setTextSizeOfView(binding.tvToolbarTitle, titleSize.toFloat())
        }

//        setVisibilityOfView(viewMenu, attributes.getBoolean(R.styleable.ApplicationToolbar_ATMenuIconVisibility, false))

        binding.ivNavigationIcon.visibility = if (attributes.getBoolean(R.styleable.ApplicationToolbar_ATNavIconVisibility, true))
            View.VISIBLE
        else View.INVISIBLE

        binding.layoutMenu.root.visibility = if (attributes.getBoolean(R.styleable.ApplicationToolbar_ATMenuIconVisibility, false))
            View.VISIBLE
        else View.INVISIBLE
        setVisibilityOfView(binding.tvToolbarTitle, attributes.getBoolean(R.styleable.ApplicationToolbar_ATTitleVisibility, true))


        binding.ivNavigationIcon.setOnClickListener {
            mListener.onToolbarNavClick()
        }

        binding.layoutMenu.ivToolbarMenuIcon.setOnClickListener {
            mListener.onMenuClick()
        }


        attributes.recycle()

    }

    private fun setVisibilityOfView(v: View, visibility: Boolean) {
        v.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun changeVisibilityOfBadge(){
        binding.layoutMenu.tvCartBadge.visibility = if (badgeCount==0) View.GONE else View.VISIBLE
    }
    private fun setTextSizeOfView(v: TextView, size: Float) {
        v.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    fun setApplicationToolbarListener(applicationToolbarListener: ApplicationToolbarListener) {
        mListener = applicationToolbarListener

    }

    fun setToolbarTitle(text: String) {
        binding.tvToolbarTitle.text = text
    }

    fun setToolbarTitle(text: Spanned) {
        binding.tvToolbarTitle.text = text
    }

    fun setToolbarTitleColor(color: Int) {
        binding.tvToolbarTitle.setTextColor(color)
    }


    fun setToolbarNavIcon(image: Int) {
        binding.ivNavigationIcon.setImageResource(image)
    }

    fun setNavIconColor(color: Int) {
        binding.ivNavigationIcon.setColorFilter(color)
    }

    fun setToolbarMenuIcon(drawable: Drawable) {
        binding.layoutMenu.ivToolbarMenuIcon.setImageDrawable(drawable)
    }

    fun setMenuIconColor(color: Int) {
        binding.layoutMenu.ivToolbarMenuIcon.setColorFilter(color)
    }


    fun setMenuBadgeCount(count:Int){
        badgeCount = count
        binding.layoutMenu.tvCartBadge.text = count.toString()
        changeVisibilityOfBadge()
    }


    interface ApplicationToolbarListener {
        fun onToolbarNavClick()
        fun onMenuClick()
    }
}
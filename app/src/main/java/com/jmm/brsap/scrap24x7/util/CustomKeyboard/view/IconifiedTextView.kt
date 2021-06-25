package com.example.pocketmoney.utils.CustomKeyboard.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.jmm.brsap.scrap24x7.R

/**
 * Created by Evgeny Eliseyev on 11/02/2018.
 */

@SuppressLint("AppCompatCustomView")
internal class IconifiedTextView : TextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        setIconifiedTypeface()
    }

    private fun setIconifiedTypeface() {
//        typeface = Typeface.createFromAsset(context.assets, "font/nunito_regular.ttf")
        typeface = ResourcesCompat.getFont(context, R.font.nunito_regular);
    }
}
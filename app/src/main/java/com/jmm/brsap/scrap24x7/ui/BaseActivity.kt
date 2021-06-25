package com.jmm.brsap.scrap24x7.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.util.ProgressBarHandler
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.ParameterizedType

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {


    private lateinit var progressBarHandler: ProgressBarHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBarHandler = ProgressBarHandler(this)

    }


    protected fun displayLoading(state: Boolean) {
        if (state) progressBarHandler.show() else progressBarHandler.hide()
    }


    protected fun displayRefreshing(loading: Boolean) {

    }

    protected fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    protected fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showAlertDialog(message: String?,dialogClickListener: DialogInterface.OnClickListener){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    protected fun showFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}
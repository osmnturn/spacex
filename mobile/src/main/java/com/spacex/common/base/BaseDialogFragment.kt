package com.spacex.common.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.spacex.R
import com.spacex.shared.annotation.ContentLayout


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
abstract class BaseDialogFragment<DB : ViewDataBinding> : DialogFragment() {


    protected lateinit var binding: DB

    private var setLayout: ContentLayout

    init {
        if (javaClass.isAnnotationPresent(ContentLayout::class.java)) {
            setLayout = javaClass.getAnnotation(ContentLayout::class.java)
            if (setLayout.value == 0) {
                throw RuntimeException("@SetLayout has invalid value " + javaClass.name)
            }
        } else {
            throw RuntimeException("@SetLayout not found " + javaClass.name)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout.value, container, false)
        return binding.root
    }

    override fun show(fragmentManager: FragmentManager, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }
}

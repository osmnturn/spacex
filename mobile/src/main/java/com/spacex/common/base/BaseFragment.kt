package com.spacex.common.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.spacex.shared.annotation.ContentLayout
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    protected lateinit var binding: DB

    private var setLayout: ContentLayout? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val baseActivity: Activity?
        get() = activity

    init {
        if (javaClass.isAnnotationPresent(ContentLayout::class.java)) {
            setLayout = javaClass.getAnnotation(ContentLayout::class.java)
            if (setLayout!!.value == 0) {
                throw RuntimeException("@SetLayout has invalid value " + javaClass.name)
            }
        } else {
            throw RuntimeException("@SetLayout not found " + javaClass.name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    @Deprecated("")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout!!.value, container, false)
        return binding.root
    }

}

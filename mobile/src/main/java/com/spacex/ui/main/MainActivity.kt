package com.spacex.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.spacex.R
import com.spacex.common.base.BaseActivity
import com.spacex.databinding.MainActivityBinding
import com.spacex.shared.extensions.replaceFragment
import com.spacex.util.viewModelProvider
import com.spacex.ui.main.fragment.language.LanguageListFragment
import com.spacex.ui.main.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    internal var viewModel: MainViewModel? = null
    lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewModel = viewModel

        if (savedInstanceState == null) {
            replaceFragment(LanguageListFragment.newInstance(), R.id.container)
        }
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}

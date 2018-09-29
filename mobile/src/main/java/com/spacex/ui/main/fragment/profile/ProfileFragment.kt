package com.spacex.ui.main.fragment.profile

import android.os.Bundle
import com.spacex.R
import com.spacex.shared.annotation.ContentLayout
import com.spacex.common.base.BaseFragment
import com.spacex.databinding.MainProfileFragmentBinding
import com.spacex.shared.extensions.viewModelProvider

@ContentLayout(R.layout.main_profile_fragment)
class ProfileFragment : BaseFragment<MainProfileFragmentBinding>() {


    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        lifecycle.addObserver(viewModel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel=viewModel
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }


}
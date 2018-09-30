package com.spacex.ui.main.fragment.language

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.spacex.R
import com.spacex.common.base.BaseFragment
import com.spacex.databinding.MainLanguageListFragmentBinding
import com.spacex.model.Language
import com.spacex.shared.annotation.ContentLayout
import com.spacex.util.clearDecorations
import com.spacex.util.viewModelProvider
import kotlinx.android.synthetic.main.main_language_list_fragment.view.*

@ContentLayout(R.layout.main_language_list_fragment)
class LanguageListFragment : BaseFragment<MainLanguageListFragmentBinding>() {

    lateinit var viewModel: LanguageListViewModel

    private lateinit var adapter: LanguageAdapter


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LanguageAdapter()
        viewModel = viewModelProvider(viewModelFactory)
        binding.viewModel = viewModel
        viewModel.getlanguageList()
        binding.contentView.apply {
            adapter = this@LanguageListFragment.adapter
            (layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }

        viewModel.mutableLiveData.observe(this, Observer {
            initializeList(it)
        })

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }


    private fun initializeList(languageList: List<Language>) {
        adapter.submitList(languageList)
        binding.contentView.run {
            // we want this to run after diffing
            doOnNextLayout {
                clearDecorations()

            }
        }

    }


    companion object {
        fun newInstance(): Fragment {
            return LanguageListFragment()
        }
    }

}
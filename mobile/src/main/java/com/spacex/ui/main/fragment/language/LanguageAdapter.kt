package com.spacex.ui.main.fragment.language

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spacex.databinding.ItemLanguageBinding
import com.spacex.model.Language
import java.util.*
import kotlin.collections.ArrayList

private const val formatPattern = "d MMMM yyyy"

class LanguageAdapter : ListAdapter<Language, LanguageViewHolder>(LanguageDiff) {
    lateinit var list: List<Language>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
                ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.txtDate.text = DateFormat.format(formatPattern, getItem(position).launchDate)
    }

    fun shortDateNew() {
        Collections.sort(list) { rate2, rate1 ->
            rate1.launchDate.compareTo(rate2.launchDate)
        }
        notifyDataSetChanged()
    }

    fun shortDateOld() {
        Collections.sort(list) { rate1, rate2 ->
            rate1.launchDate.compareTo(rate2.launchDate)
        }
        notifyDataSetChanged()
    }

    override fun submitList(list: List<Language>?) {
        this.list = list ?: ArrayList<Language>();
        super.submitList(list)
    }

}

class LanguageViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root)

object LanguageDiff : DiffUtil.ItemCallback<Language>() {
    override fun areItemsTheSame(
            oldItem: Language,
            newItem: Language
    ): Boolean {
        // We don't have to compare the #userEvent because the id of #session and #userEvent
        // should match
        return oldItem.flightNumber == newItem.flightNumber
    }

    override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
        return oldItem == newItem
    }
}
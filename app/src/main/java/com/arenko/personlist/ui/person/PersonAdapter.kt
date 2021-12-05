package com.arenko.personlist.ui.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arenko.personlist.R
import com.arenko.personlist.databinding.ItemPersonBinding
import com.arenko.personlist.datasource.Person

class PersonAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val artList = ArrayList<Person>()

    fun setList(arts: List<Person>) {
        artList.clear()
        artList.addAll(arts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPersonBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_person, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as PersonViewHolder
        viewHolder.bind(artList[position])
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    inner class PersonViewHolder(val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.tvPerson.text = person.fullName + " (" + person.id + ")"
        }
    }
}

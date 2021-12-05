package com.arenko.personlist.ui.person

import android.nfc.tech.MifareUltralight
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arenko.personlist.R
import com.arenko.personlist.base.BaseFragment
import com.arenko.personlist.databinding.MainFragmentBinding
import com.arenko.personlist.datasource.Person
import com.arenko.personlist.extentions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : BaseFragment<PersonViewModel, MainFragmentBinding>(
    R.layout.main_fragment,
    PersonViewModel::class.java
) {

    lateinit var personAdapter: PersonAdapter
    private lateinit var personList: ArrayList<Person>
    lateinit var llm: LinearLayoutManager
    private var isLoading = false
    private var isLastPage = false
    private var next: String? = null
    private var errorCount = 0

    override fun init() {
        initAdapter()
        showResult()
    }

    private fun initAdapter() {
        personList = arrayListOf()
        personAdapter = PersonAdapter()
        llm = LinearLayoutManager(context)
        binding.rvPerson.apply {
            layoutManager = llm
            adapter = personAdapter
        }
        binding.rvPerson.addOnScrollListener(recyclerViewOnScrollListener)
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshItems()
        }
    }

    private fun showResult() {
        viewModel.getPersonList(next).observe(this, {
            isLoading = false
            if (it?.fetchResponse != null) {
                errorCount = 0
                if (it.fetchResponse.people.isNotEmpty()) {
                    noResultState(false)
                    next = it.fetchResponse.next
                    personList.addAll(filterDuplicateItems(it.fetchResponse.people))
                    personAdapter.setList(personList)
                    personAdapter.notifyDataSetChanged()
                } else if (personList.isEmpty()) {
                    noResultState(true)
                    initRenewListener()
                }
            } else if (it?.fetchError != null) {
                errorCount++
                requireContext().toast(it.fetchError.errorDescription + " " + errorCount)
                if (errorCount < 3) {
                    showResult()
                }
            }
        })
    }

    private fun refreshItems() {
        initAdapter()
        showResult()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = llm.getChildCount()
                val totalItemCount: Int = llm.getItemCount()
                val firstVisibleItemPosition: Int = llm.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= MifareUltralight.PAGE_SIZE) {
                        isLoading = true
                        showResult()
                    }
                }
            }
        }

    private fun filterDuplicateItems(apiList: List<Person>): List<Person> {
        val filteredApiList: ArrayList<Person> = arrayListOf()
        for (person in apiList) {
            if (!personList.contains(person)) {
                filteredApiList.add(person)
            }
        }
        return filteredApiList
    }

    private fun initRenewListener() {
        binding.layoutNoResult.btnRenew.setOnClickListener {
            noResultState(false)
            refreshItems()
        }
    }

    private fun noResultState(noResult: Boolean) {
        if (noResult) {
            binding.swipeRefreshLayout.visibility = View.GONE
            binding.layoutNoResult.root.visibility = View.VISIBLE
        } else {
            binding.swipeRefreshLayout.visibility = View.VISIBLE
            binding.layoutNoResult.root.visibility = View.GONE
        }
    }
}
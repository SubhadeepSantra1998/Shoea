package com.subha.optymize.presentation.product_listing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.subha.optymize.R
import com.subha.optymize.data.remote.paging.LoaderAdapter
import com.subha.optymize.databinding.FragmentProductListBinding
import com.subha.optymize.viewModel.ShoeaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.fragment_product_list),
    ShoeaPagingAdapter.clickListener {

    private var _binding: FragmentProductListBinding? = null
    val binding: FragmentProductListBinding?
        get() = _binding


    private val viewModel: ShoeaViewModel by viewModels()
    lateinit var productListAdapter: ShoeaPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)

        setUI()

        apiCall()
    }


    private fun setUI() {
        binding!!.pullToRefresh.setOnRefreshListener {
            refreashData()
            binding!!.pullToRefresh.isRefreshing = false
        }
        setupRecyclerView()
    }

    private fun refreashData() {
        setupRecyclerView()
        apiCall()
    }

    private fun setupRecyclerView() {
        productListAdapter = ShoeaPagingAdapter(requireContext(), this)
        binding!!.rvProductList.apply {
            adapter = productListAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun apiCall() {
        lifecycleScope.launch {
            viewModel.datalist.collect {
                productListAdapter.submitData(it)
            }
        }
    }

    override fun item(id: String) {

        val direction =
            ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(id)
        findNavController().navigate(direction)
    }


    override fun setUserVisibleHint(visible: Boolean) {
        super.setUserVisibleHint(visible)
        if (visible && isResumed) {
            setUI()
            apiCall()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
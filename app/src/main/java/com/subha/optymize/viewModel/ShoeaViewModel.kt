package com.subha.optymize.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.subha.optymize.core.helper.Resource
import com.subha.optymize.data.model.Product
import com.subha.optymize.repository.ShoeaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoeaViewModel @Inject constructor(private val repository: ShoeaRepository) : ViewModel() {


    val datalist = repository.getproductList().cachedIn(viewModelScope).asFlow()


    val productListLiveData: LiveData<Resource<Product>>
        get() = repository.getProductListLiveData

    fun productList(url: String) {
        viewModelScope.launch {
            repository.getProductList(url)
        }
    }


}
package com.subha.optymize.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.subha.optymize.core.helper.Resource
import com.subha.optymize.data.model.Product
import com.subha.optymize.data.remote.api.ShoeaApi
import com.subha.optymize.data.remote.paging.ShoeaPagingSource
import org.json.JSONObject
import javax.inject.Inject

class ShoeaRepository @Inject constructor(private val api: ShoeaApi) {

    private val _getProductListLiveData = MutableLiveData<Resource<Product>>()
    val getProductListLiveData: LiveData<Resource<Product>>
        get() = _getProductListLiveData


    suspend fun getProductList(url: String) {
        _getProductListLiveData.postValue(Resource.Loading())
        val response = api.getproductlist(url)
        if (response.isSuccessful && response.body() != null) {
            _getProductListLiveData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _getProductListLiveData.postValue(Resource.Error(errorObj.getString("message")))
        } else {
            _getProductListLiveData.postValue(Resource.Error("Something Went Wrong"))
        }
    }


    fun getproductList() = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ShoeaPagingSource(api)
        }
    ).liveData


}
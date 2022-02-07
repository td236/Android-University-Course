/*
package com.example.shop
 */
/*
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.model.Product
import com.example.shop.model.ProductAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val api: ProductAPI
) : ViewModel() {

    private val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    init {
        getProductList()
    }

    fun getProductList() {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    product = api.getProduct(),
                    isLoading = false
                )

            } catch (exc: Exception) {
                Log.e("ProductViewModel", "getProductList: ", exc)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }
    data class ProductState(
        val product: Product? = null,
        val isLoading: Boolean = false
    )
}
*/
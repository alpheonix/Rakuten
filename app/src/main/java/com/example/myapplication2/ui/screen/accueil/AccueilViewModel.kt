package com.example.myapplication2.ui.screen.accueil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication2.models.Images
import com.example.myapplication2.models.ImagesUrls
import com.example.myapplication2.models.ProductDetail
import com.example.myapplication2.models.Rating
import com.example.myapplication2.models.Seller
import com.example.myapplication2.models.URL
import com.example.myapplication2.api.Repository
import com.example.rakutentest.Model.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccueilViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _data = MutableStateFlow(Search())
    val data: StateFlow<Search> = _data

    private val _isPressed = MutableStateFlow(false)
    val isPressed: StateFlow<Boolean> = _isPressed

    // comme le json fournis a l'adresse https://4206121f-64a1-4256-a73d-2ac541b3efe4.mock.pstmn.io/products/details?id=6035914280
    //n'est pas serialisable

    private val _detail = MutableStateFlow(
        ProductDetail(
            productId = 6035914280,
            salePrice = 689.99,
            seller = Seller(11773507564, "Pixel-Tech"),
            quality = "NEW",
            type = "NEW",
            sellerComment = "Carte SIM unique, version US, compatible avec les opérateurs français. Avant l'expédition, nous vérifierons si le téléphone est en bon état et fournirons un ensemble complet d'accessoires compatibles français (y compris les prises de charge européennes) + film de protection et coque de téléphone. Remarque: Expédition depuis la Chine, une garantie de 9 à 19 jours ouvrables / un an est fournie",
            headline = "Samsung Galaxy S21 5G 128 Go Double SIM Violet",
            description = "Fabricant : Samsung Référence fabricant : SM-G991BZVDEUB - SM-G991BZVDEUH Poids : 169 Interface sans fil : NFC Produit soumis à la Rémunération Pour Copie Privée. En savoir plus Voir le montant de la Rémunération Pour Copie Privée",
            categories = listOf("Tel-PDA", "Telephones-mobiles"),
            globalRating = Rating(4.6F, 94),
            images = listOf(
                Images(
                    imagesUrls = ImagesUrls(
                        entry = listOf(
                            URL(
                                size = "ORIGINAL",
                                url = "https://fr.shopping.rakuten.com/photo/1673299896.jpg"
                            ),
                            URL(
                                size = "SMALL",
                                url = "https://fr.shopping.rakuten.com/photo/1673299896.jpg"
                            ),
                            URL(
                                size = "MEDIUM",
                                url = "https://fr.shopping.rakuten.com/photo/1673299896.jpg"
                            ),
                            URL(
                                size = "LARGE",
                                url = "https://fr.shopping.rakuten.com/photo/1673299896.jpg"
                            )
                        )
                    ),
                    id = 1673299896,
                ),
                Images(
                    imagesUrls = ImagesUrls(
                        entry = listOf(
                            URL(
                                size = "ORIGINAL",
                                url = "https://fr.shopping.rakuten.com/photo/1673299897.jpg"
                            ),
                            URL(
                                size = "SMALL",
                                url = "https://fr.shopping.rakuten.com/photo/1673299897.jpg"
                            ),
                            URL(
                                size = "MEDIUM",
                                url = "https://fr.shopping.rakuten.com/photo/1673299897.jpg"
                            ),
                            URL(
                                size = "LARGE",
                                url = "https://fr.shopping.rakuten.com/photo/1673299897.jpg"
                            )
                        )
                    ),
                    id = 1673299897,
                    )
            )
        )
    )
    val detail: StateFlow<ProductDetail> = _detail


    fun fetchData(query: String) {
        clearStates()
        viewModelScope.launch {
            try {
                _data.value = repository.getSearch(query)
            } catch (e: Exception) {
                // Gérer les erreurs
            }
        }
    }

    fun isPressed() {
        _isPressed.value = !_isPressed.value
        Log.d("IsPressed", _isPressed.value.toString())

    }

    private fun clearStates() {
        _data.value = Search()
        _isPressed.value = false
    }
}
package pt.iade.ei.lootx.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchasableItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int
) : Parcelable
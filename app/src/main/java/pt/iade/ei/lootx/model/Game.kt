package pt.iade.ei.lootx.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val title: String,
    val description: String,
    val imageResId: Int,
    val items: List<PurchasableItem>
) : Parcelable
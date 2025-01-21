package com.neighborly.neighborlyandroid.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class AddItemDetails(
    val title:String,
    val description:String,
    val thumbnailUri:String, // when user uploads, it gets uploaded to S3 in a temp directory. Then if item is added succesfully then the pictures are moved to their required locations
    val category: Category,
    val dayCharge:Double
)

@Parcelize
data class InventoryItem(
    val id:Long,
    val title: String,
    val dayCharge: Double,
    val category: Category?,
    val thumbnailUrl:String?,
):Parcelable

@Parcelize
data class InventoryItemDetail(
    val description: String? = null,
    val dateAdded: ZonedDateTime? = null
):Parcelable
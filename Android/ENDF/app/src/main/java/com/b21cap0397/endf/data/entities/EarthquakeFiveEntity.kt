package com.b21cap0397.endf.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class EarthquakeFiveEntity(
    var id: String = "",
    var tanggal: String = "",
    var jam: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var magnitude: String = "",
    var kedalaman: String = "",
    var wilayah: String = ""
): Parcelable

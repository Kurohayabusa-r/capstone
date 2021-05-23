package com.b21cap0397.endf.data.entities

import android.net.Uri

data class ReportEntity(
    var id: String = "",
    var picture: Uri? = null,
    var title: String = "",
    var location: String = "",
    var user: String = "",
    var description: String = ""
)

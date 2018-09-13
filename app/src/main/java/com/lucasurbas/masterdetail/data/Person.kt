package com.lucasurbas.masterdetail.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    val id: String,
    var name: String,
    var description: String = "",
    var weight: Int? = null,
    var height: Int? = null,
    var department: String = "",
    var priority: Priority = Priority.ELEKTIV
) : Parcelable

enum class Priority(val description: String) {
    AKUT("Akut"), SAERLIGE_FORHOLD("Særlige forhold"), ELEKTIV("Elektiv")
}

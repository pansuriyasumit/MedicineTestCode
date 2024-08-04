package com.fifteen11.checkappversion.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineResponse(
    @Json(name = "status") val status: Int = -1,
    @Json(name = "code") val code: Int = -1,
    @Json(name = "message") val message: String? = "",
    @Json(name = "problems") val problems: List<ProblemsItem>? = null
) : Parcelable

@Parcelize
data class ProblemsItem(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "medications") val medications: List<MedicationsItem?>? = null,
    @Json(name = "labs") val labs: List<LabsItem?>? = null
) : Parcelable

@Parcelize
data class MedicationsItem(
    @Json(name = "medicationsClasses") val medicationsClasses: List<MedicationsClassesItem?>? = null
) : Parcelable

@Parcelize
data class MedicationsClassesItem(
    @Json(name = "drugs_name") val drugsName: List<DrugsNameItem?>? = null
) : Parcelable

@Parcelize
data class DrugsNameItem(
    @Json(name = "associated_drugtype_1") val associatedDrugType1: List<AssociatedDrugType1Item?>? = null,
    @Json(name = "associated_drugtype_2") val associatedDrugType2: List<AssociatedDrugType2Item?>? = null
) : Parcelable

@Parcelize
data class AssociatedDrugType1Item(
    @Json(name = "name") val name: String? = null,
    @Json(name = "dose") val dose: String? = null,
    @Json(name = "strength") val strength: String? = null
) : Parcelable

@Parcelize
data class AssociatedDrugType2Item(
    @Json(name = "name") val name: String? = null,
    @Json(name = "dose") val dose: String? = null,
    @Json(name = "strength") val strength: String? = null
) : Parcelable

@Parcelize
data class LabsItem(
    @Json(name = "missing_field") val missingField: String? = null
) : Parcelable















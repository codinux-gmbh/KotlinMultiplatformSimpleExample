package net.codinux.kotlin.example.domain.model

import kotlinx.serialization.Serializable


@Serializable
open class BankInfo(
    open var name: String,
    open val bankCode: String,
    open val bic: String,
    open val postalCode: String,
    open val city: String,
    open val pinTanAddress: String?,
    open val pinTanVersion: String?,
    open var branchesInOtherCities: List<String> = listOf()
) {

    protected constructor() : this("", "", "", "", "", null, "") // for object deserializers


    override fun toString(): String {
        return "$bankCode $name $city"
    }

}
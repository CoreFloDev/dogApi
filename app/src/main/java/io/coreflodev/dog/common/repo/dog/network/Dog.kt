package io.coreflodev.dog.common.repo.dog.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    @SerialName("id") val id: String,
    @SerialName("url") val image: String,
    @SerialName("breeds") val breeds: List<Breed> = emptyList()
)

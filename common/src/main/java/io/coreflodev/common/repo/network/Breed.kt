package io.coreflodev.common.repo.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    @SerialName("name") val name: String? = null,
    @SerialName("temperament") val temperament: String? = null,
    @SerialName("wikipedia_url") val wiki: String? = null,
    @SerialName("origin") val origin: String? = null
)

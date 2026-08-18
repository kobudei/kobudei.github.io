package org.kobudei.website

data class Location(
    val name: String,
    val address: String?,
    val url: String,
) {
    companion object {
        val AD01 =
            Location(
                name = "AD/01",
                address = "Bucharest, 165 Splaiul Unirii, Timpuri Noi Square, Building O3B",
                url = "https://ad01.com"
            )
    }
}
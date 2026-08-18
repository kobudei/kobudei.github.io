package org.kobudei.website

data class Location(
    val name: String,
    val address: String?,
    val url: String,
) {
    companion object {
        val QuatreSH = Location("4SH", "4SH, Le Haillan", "https://4sh.fr")
        val AD01 =
            Location("AD/01", "Bucharest, 165 Splaiul Unirii, Timpuri Noi Square, Building O3B", "https://ad01.com")
    }
}
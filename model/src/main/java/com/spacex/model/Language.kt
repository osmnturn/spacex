package com.spacex.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Language(@SerializedName("flight_number") var flightNumber: Int?,
                    @SerializedName("mission_name") var missionName: String?, @SerializedName("links") var links: Links,
                    @SerializedName("launch_date_utc") var launchDate: Date) {

    fun logo(): String? = links.logo

}
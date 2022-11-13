package com.example.bindimapstest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bindimaps.SessionsItem
import com.example.bindimaps.VenueItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class MainViewModel : ViewModel() {

    val venueDwellInfoLiveData: MutableLiveData<List<VenueDwellInfoData>> by lazy {
        MutableLiveData<List<VenueDwellInfoData>>()
    }

    private val venueDwellInfoData: MutableList<VenueDwellInfoData> = mutableListOf()

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultSessions = SessionsApi.retrofitServiceSession.getSessions()
                val resultVenues = SessionsApi.retrofitServiceVenue.getVenues()
                val resultList = calculateRoutes(resultSessions, resultVenues)
                withContext(Dispatchers.Main) {
                    venueDwellInfoLiveData.value = resultList
                }
            } catch (e: Exception) {
                print("Failure: ${e.message}")
            }
        }
    }

    private fun calculateRoutes(
        sessions: List<SessionsItem>,
        venues: List<VenueItem>
    ): MutableList<VenueDwellInfoData> {
        //Loop each venue and extract the session times
        venues.forEach { venue ->

            //get path data from session
            val pathInfo = sessions.asSequence()
                .flatMap { it.path }
                .firstOrNull {
                    (it.position.x.startsWith(venue.position.x.subSequence(0, 2))
                            &&
                     it.position.x.toDouble().isCloserTo(venue.position.x.toDouble()))
                            &&
                     (it.position.y.startsWith(venue.position.y.subSequence(0, 2))
                             &&
                     it.position.y.toDouble().isCloserTo(venue.position.y.toDouble()))
                }

            //Get path session
            val pathSession =
                sessions.firstOrNull { item ->
                    item.path.contains(pathInfo)
                }

            //calculate dwell time in minutes for each venue
            val userTime = pathInfo?.userTimeUtc?.toDate()
            val endTimeUtc = pathSession?.endTimeUtc?.toDate()
            val dwellTime =
                (userTime?.time?.let { endTimeUtc?.time?.minus(it) }?.rem(3600))?.div(60)

            //store in list
            venueDwellInfoData.add(
                VenueDwellInfoData(
                    venue.name,
                    venue.position,
                    pathSession?.endTimeUtc ?: "",
                    pathSession?.startTimeLocal ?: "",
                    pathSession?.startTimeUtc ?: "",
                    pathInfo?.userTimeUtc ?: "",
                    dwellTime.toString() + "mins"
                )
            )
        }
        return venueDwellInfoData
    }

    private fun Double.isCloserTo(other: Double): Boolean {
        return abs(this / other - 1) < 0.01
    }

    private fun String.toDate(
        dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }
}
package com.example.triviaapp.util

import android.annotation.SuppressLint
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtil {

    private const val MILLISECONDS_IN_DAY : Long = 86400000L

    private fun fromMilliSecondsToMinutes(milli : Long ) : Long = TimeUnit.MINUTES.convert(milli , TimeUnit.MILLISECONDS)

    fun fromMinutesToMilliSeconds( min : Long ) : Long = TimeUnit.MILLISECONDS.convert(min , TimeUnit.MINUTES)

    fun fromMillisecondsToHour( ms : Long ) = TimeUnit.HOURS.convert( ms , TimeUnit.MILLISECONDS )

    /**
     * returns 'hh:mm' format of millisecond from "00:00"
     * @param milli milliseconds from midnight
     */
    @Suppress("unused")
    fun fromMilliSecondsToString( milli : Long ) : String{
        val timeMilli = fromMilliSecondsToMinutes(milli)
        return "${ if(timeMilli / 60 < 10) "0"+(timeMilli / 60).toString() else timeMilli / 60 }:${ if(timeMilli % 60 < 10) "0" + (timeMilli % 60).toString() else timeMilli %60 }"
    }

    /**
     * Returns epoch time to date string 'mm:HH dd-MM-yyyy' format
     * @param milli milliseconds from midnight
     */
    fun fromMStoDateString( milli: Long ) : String {
        val format = "HH:mm dd-MM-yyyy"
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("IST")
        return sdf.format(Date(milli))
    }

    @Suppress("unused")
    fun getDate() : Int{
        val c = Calendar.getInstance()
        return c.get(Calendar.DAY_OF_MONTH)
    }

    fun getMonth(month : Int ) = DateFormatSymbols().months[ month - 1 ]

    /**
     * Returns Date-Format(dd MMMM) offset to X days in Past
     *
     * @param offset -> days offset to (from today)
     * @return -> String formatted by "DD MMMM"
     * */
    @SuppressLint("SimpleDateFormat")
    fun getPastDateFromOffset(offset : Int) : String {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_YEAR , -offset)
        return "${SimpleDateFormat("dd").format( Date( c.timeInMillis ) )} ${getMonth(SimpleDateFormat("MM").format( Date( c.timeInMillis ) ).toInt())}"
    }

    /**
     * Returns Date-Format(dd MMMM) offset to X days in Future
     *
     * @param offset -> days offset to (from today)
     * @return -> String formatted by "DD MMMM"
     * */
    @SuppressLint("SimpleDateFormat")
    fun getFutureDateFromOffset(offset : Int) : String {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_YEAR , offset)
        return "${SimpleDateFormat("dd").format( Date( c.timeInMillis ) )} ${getMonth(SimpleDateFormat("MM").format( Date( c.timeInMillis ) ).toInt())}"
    }

    /**
     * Returns number of days from epoch
     */
    fun getTodayFromEpoch( ) : Long  {
        val calender = Calendar.getInstance()
        return (
                calender.timeInMillis + calender.get( Calendar.ZONE_OFFSET ) +
                        calender.get( Calendar.DST_OFFSET )
                ) / MILLISECONDS_IN_DAY
    }

    /**
     * Returns milliseconds from epoch
     */
    @Suppress("SpellCheckingInspection")
    fun getMSfromEpoch( ) : Long {
        val calender = Calendar.getInstance()
        return calender.timeInMillis + calender.get( Calendar.ZONE_OFFSET ) + calender.get( Calendar.DST_OFFSET )
    }

    @Suppress("SpellCheckingInspection")
    fun getMSfromMidnight():Long = getMSfromEpoch() % MILLISECONDS_IN_DAY

    fun daysToMilliseconds( X : Long ) = X * MILLISECONDS_IN_DAY

    fun millisecondsToDays( X : Long ) = X / MILLISECONDS_IN_DAY
}
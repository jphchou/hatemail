package edu.washington.info448.hatemail

import android.app.PendingIntent
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class History(val recipient: String, val time: Date, val message: String): Parcelable

@Parcelize
data class Schedule(val recipient: String, val message: String, val frequency: Long, val intent: Int?): Parcelable

@Parcelize
data class MessageType(val name: String, val url: String, val fields: List<String>): Parcelable
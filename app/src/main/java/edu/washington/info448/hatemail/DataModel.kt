package edu.washington.info448.hatemail

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
class Schedule (var recipient:String, var frequency: Long,  var messsage: String):Parcelable

@Parcelize
class History (var recipient:String, var time: String,  var messsage: String):Parcelable



val his1 = History("ZK", "3 Days ago" ,  "fuckkkkkk")
val his2 = History("ZK1", "5 Days ago" ,  "yas")
val his3 = History("ZK2", "10 Days ago" ,  "what's wrong with you?")


val sche1 = Schedule("Charlane", 2000, "Hello")
val sche2 = Schedule("Charlane1", 2000, "from")
val sche3 = Schedule("Charlane2", 2000, "the other side")

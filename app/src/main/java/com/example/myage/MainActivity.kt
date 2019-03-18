package com.example.myage

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import android.content.Context
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.AlarmClock
import android.service.wallpaper.WallpaperService
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import kotlinx.android.synthetic.main.activity_main.*
import java.time.MonthDay
import java.time.Year
import java.util.*

open class MainActivity : AppCompatActivity() {

    private val yearv:Year=Year.now()
    private val year=yearv.toString().toInt()
    private val month= MonthDay.now().monthValue
    private val monthv= MonthDay.now().month!!
    private val lastmonthv= (monthv-1)
    private val value=lastmonthv.maxLength()
    private val day= MonthDay.now().dayOfMonth
    private val hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    private val minute=Calendar.getInstance().get(Calendar.MINUTE)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // for hide actionbar:
        supportActionBar!!.hide()

    }
    @SuppressLint("SetTextI18n", "ShowToast")
    fun age(v:View){
        val myear:Int;        val eyear= edityear
        val mmonth:Int;        val emonth= editmonth
        val mday:Int;        val eday=editday
        val mhour:Int;        val ehour=edithour
        val mminute:Int ;        val eminute=editminute
        val vibrator=getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // if the user let some input empty, well we must show him popup error message.
        if (TextUtils.isEmpty(eyear.text.toString())){eyear.error="Empty!";return}
        else{myear=eyear.text.toString().toInt()}
        if (TextUtils.isEmpty(emonth.text.toString())){emonth.error="Empty!";return}
        else{mmonth=emonth.text.toString().toInt()}
        if (TextUtils.isEmpty(eday.text.toString())){eday.error="Empty!";return}
        else{mday=eday.text.toString().toInt()}

        // if we want to make default value to some inputs.
        mhour= if (TextUtils.isEmpty(edithour.text.toString())){-1}else{
            ehour.text.toString().toInt()}
        mminute= if (TextUtils.isEmpty(eminute.text.toString())){-1}else{
            eminute.text.toString().toInt()}

        // for make vibrator button:
        vibrator.vibrate(50)

            // get a year:
            val uyear: Any = when {
                myear !in 0..year -> "out!"
                myear < year -> (year - myear) - 1
                else -> "error!"
            }
            // get a month:
            val umonth: Any = when {
                mmonth !in 1..12 -> "out!"
                mmonth < month -> month - mmonth
                mmonth == month -> 0
                mmonth > month -> (12 - mmonth) + month
                else -> "error!"
            }
            // get a day:
            val uday: Any = when {
                mday !in 1..31 -> "out!"
                mday < day && mmonth == 3 -> (28 - mday) + day
                mday < day -> day - mday
                mday == day -> 0
                mday > day -> (value - mday) + day
                else -> "error!"
            }
            // get a hour:
            val uhour: Any = when {
                mhour==-1 -> "."
                mhour !in 0..23 -> "out!"
                mhour < hour -> hour - mhour
                mhour == hour -> 0
                mhour > hour -> 23 - (mhour - hour)
                else -> "!!"
            }
            // get a minute:
            val uminute: Any = when {
                mminute==-1 -> "."
                mminute !in 0..59 -> "out!"
                mminute < minute -> minute - mminute
                mminute == minute -> 0
                mminute > minute -> 59 - (mminute - minute)
                else -> "!!"
            }
        // for show some text when click start button.
        val toast: Toast =when{
            umonth==month && uday==day -> makeText(this,"Happy Birthday",Toast.LENGTH_LONG)
            uyear in 5..13 -> makeText(this,"Child",Toast.LENGTH_LONG)
            uyear in 13..19 -> makeText(this,"Teen",Toast.LENGTH_LONG)
            uyear in 19..25 -> makeText(this,"Too Young",Toast.LENGTH_LONG)
            uyear in 25..35 -> makeText(this,"Young",Toast.LENGTH_LONG)
            uyear in 35..50 -> makeText(this,"Adult",Toast.LENGTH_LONG)
            uyear in 50..65 -> makeText(this,"Old Age",Toast.LENGTH_LONG)
                else -> makeText(this,"...",Toast.LENGTH_LONG)
        }
        toast.setGravity(Gravity.TOP,0,80);toast.show()
        // get an interview:
            viewyear.text = uyear.toString()
            viewmonth.text = umonth.toString()
            viewday.text = uday.toString()
            viewhour.text = uhour.toString()
            viewminute.text = uminute.toString()
    }
    // for clear and reset all edit's.
    fun clickreset(v:View){
        edityear.text.clear();editmonth.text.clear();editday.text.clear()
        edithour.text.clear();editminute.text.clear()

        // for vibrator on click button.
        val vibrator=getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(50)
    }
}

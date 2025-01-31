package com.alenniboris.nba_app.domain.utils

import android.util.Log
import com.alenniboris.nba_app.BuildConfig

object LogPrinter {
    fun printLog(tag: String, message: String){
        if(BuildConfig.DEBUG){
            Log.e(tag,message)
        } else{
            println("""
                $tag
                --------------
                $message
            """.trimIndent())
        }
    }
}
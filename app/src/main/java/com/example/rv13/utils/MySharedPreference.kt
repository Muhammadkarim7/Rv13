package com.example.rv13.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.rv13.User.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MySharedPreference {
    private const val NAME = "catch_file_name"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var list:ArrayList<User>
        get() = gsonStringToList(preferences.getString("listUser", "[]")!!)
        set(value) = preferences.edit{
            if (value!=null){
                it.putString("listUser", listToGsonString(value))
            }
        }

    private fun gsonStringToList(gsonString:String):ArrayList<User>{
        val list = ArrayList<User>()

        val type = object : TypeToken<ArrayList<User>>(){}.type
        list.addAll(Gson().fromJson(gsonString, type))

        return list
    }

    private fun listToGsonString(list: ArrayList<User>): String {
        return Gson().toJson(list)
    }

}
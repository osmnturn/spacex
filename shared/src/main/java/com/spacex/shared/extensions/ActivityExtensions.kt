package com.spacex.shared.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import org.parceler.Parcels

inline fun <reified T : Any> AppCompatActivity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (requestCode > -1)
        startActivityForResult(intent, requestCode, options)
    else
        startActivity(intent, options)
}

inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

fun AppCompatActivity.replaceFragment(fragment: androidx.fragment.app.Fragment, @IdRes containerId: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(containerId, fragment, fragment.javaClass.simpleName)
    fragmentTransaction.commitAllowingStateLoss()
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)

inline fun <reified T : Any> AppCompatActivity.parcelUnwrap(key: String): T? {
    if (intent == null || TextUtils.isEmpty(key)) {
        return null
    }
    return Parcels.unwrap<T>(intent.getParcelableExtra<Parcelable>(key))
}

inline fun <reified T : Any> androidx.fragment.app.Fragment.parcelUnwrap(key: String): T? {
    if (arguments == null || TextUtils.isEmpty(key)) {
        return null
    }
    return Parcels.unwrap<T>(arguments!!.getParcelable(key))
}

inline fun <reified T : Any> parcelWrap(data: T): Parcelable? {
    return Parcels.wrap(data)
}

package net.alienminds.ethnogram.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent


internal fun Context.openLink(url: String) = openLink(Uri.parse(url))

internal fun Context.openLink(url: Uri){
    val intent = CustomTabsIntent.Builder().build()
    intent.launchUrl(this, url)
}

internal fun Context.openLinkExternal(url: String){
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)));
}

internal fun Context.openLinkInApp(
    link: String,
    webLink: String = link,
    appPackage: String? = null
){
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        appPackage?.let { intent.setPackage(appPackage) }
        startActivity(intent)
    } catch (e: Exception){
        Log.w("Mapper.User", "Failed open link in app", e)
        openLinkExternal(webLink)
    }
}
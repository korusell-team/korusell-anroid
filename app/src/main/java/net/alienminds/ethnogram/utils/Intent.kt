package net.alienminds.ethnogram.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


object IntentActions{

    fun callNumber(
        context: Context,
        phone: String
    ){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:$phone"))
        context.startActivity(intent)
    }

    fun sendMessage(
        context: Context,
        phone: String,
        body: String? = null
    ){
        val uri = Uri.parse("smsto:$phone")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        body?.let { intent.putExtra("sms_body", it) }
        context.startActivity(intent)
    }

}
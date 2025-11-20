package com.android.kairoscoffeemovile.utils

import android.util.Base64
import org.json.JSONObject

object JwtUtils {

    private fun decodePayload(token: String): JSONObject? {
        return try {
            val parts = token.split(".")
            val json = String(Base64.decode(parts[1], Base64.URL_SAFE))
            JSONObject(json)
        } catch (e: Exception) {
            null
        }
    }

    fun extractEmail(token: String): String? {
        val payload = decodePayload(token) ?: return null
        return payload.optString("email", null)
    }
}

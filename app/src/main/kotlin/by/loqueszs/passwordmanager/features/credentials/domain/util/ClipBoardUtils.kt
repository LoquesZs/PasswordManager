package by.loqueszs.passwordmanager.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

internal fun Context.getClipboardManager(): ClipboardManager {
    return getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}

internal fun copyTextToClipBoard(context: Context, label: String, text: String) {
    val cbManager = context.getClipboardManager()
    val clip = ClipData.newPlainText(label, text)
    cbManager.setPrimaryClip(clip)
}
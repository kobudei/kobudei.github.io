package org.kobudei.website

// wasmJsMain
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

@OptIn(ExperimentalWasmJsInterop::class)
actual fun openEmail(
    to: String,
    subject: String,
    body: String
) {
    val params = URLSearchParams().apply {
        append("subject", subject)
        append("body", body)
    }

    val mailtoUrl = "mailto:$to?${params}"
    window.location.href = mailtoUrl
}

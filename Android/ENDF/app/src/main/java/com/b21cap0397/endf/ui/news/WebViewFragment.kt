package com.b21cap0397.endf.ui.news

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.b21cap0397.endf.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class WebViewFragment : CBottomSheetDialogFragment() {

    companion object {
        const val URL = "url"
    }

    override fun onStart() {
        super.onStart()
        setFullScreen()//initiated at onActivityCreated(), onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            val url = bundle.getString(URL)
            if (url != null) {
                showWebView(url)
            }
        }
    }

    private fun showWebView(url: String) {
        val wvNews = view?.findViewById<WebView>(R.id.wv_news)
        wvNews?.webViewClient = WebViewClient()
        wvNews?.apply {
            loadUrl(url)
        }
    }
}


open class CBottomSheetDialogFragment : BottomSheetDialogFragment() {

    //wanna get the bottomSheetDialog
    private lateinit var dialog: BottomSheetDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    //set the behavior here
    fun setFullScreen() {
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
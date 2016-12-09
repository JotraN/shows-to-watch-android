package io.josephtran.showstowatch.login

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import io.josephtran.showstowatch.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginView {
    private var presenter: LoginPresenter? = null
    private var fragmentListener: LoginFragmentListener? = null
    private val webView by lazy { webview }

    interface LoginFragmentListener {
        fun onLoggedIn()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        webView!!.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (presenter != null) {
                    val loadUrl = presenter!!.onUrlLoad(url)
                    if (loadUrl.isEmpty()) return false
                    view.loadUrl(loadUrl)
                    view.visibility = View.INVISIBLE
                    return true
                }
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                view.loadUrl("javascript:HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0]" +
                        ".innerHTML+'</html>');")
            }
        })
        webView!!.getSettings().setJavaScriptEnabled(true);
        webView!!.addJavascriptInterface(RedirectJSInterface(presenter!!), "HtmlViewer")
        clearCookies()

        presenter = LoginPresenter(context, this)
        presenter?.login()
    }

    class RedirectJSInterface(val listener: LoginListener) {
        @JavascriptInterface
        fun showHTML(html: String) = listener.onRedirect(html)
    }

    private fun clearCookies() {
        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            cookieManager.removeAllCookies(null)
        else
            cookieManager.removeAllCookie()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is LoginFragmentListener) {
            fragmentListener = context
        } else {
            throw RuntimeException(context!!.toString()
                    + " must implement LoginFragmentListener")
        }
        if (presenter == null)
            presenter = LoginPresenter(context, this)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
        presenter = null
    }

    override fun showLogin(loginUrl: String): Unit = webView?.loadUrl(loginUrl)!!

    override fun showHome(): Unit = fragmentListener?.onLoggedIn()!!
}

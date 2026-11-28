package com.amon3411.lumen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        web = new WebView(this);
        web.setBackgroundColor(Color.parseColor("#131316"));
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);
        // Lets the bundled page call Home Assistant on the home network
        s.setAllowUniversalAccessFromFileURLs(true);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        s.setMediaPlaybackRequiresUserGesture(false);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                Uri u = r.getUrl();
                if ("file".equals(u.getScheme())) return false;
                try {
                    if ("intent".equals(u.getScheme())) {
                        Intent i = Intent.parseUri(u.toString(), Intent.URI_INTENT_SCHEME);
                        try { startActivity(i); }
                        catch (Exception e) {
                            String fb = i.getStringExtra("browser_fallback_url");
                            if (fb != null) startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fb)));
                        }
                    } else startActivity(new Intent(Intent.ACTION_VIEW, u));
                } catch (Exception ignored) {}
                return true;
            }
        });
        web.addJavascriptInterface(new Native(), "VestaNative");
        // Android 15+ draws apps edge-to-edge; keep Vesta clear of the status bar,
        // gesture bar and keyboard by padding a container with the system insets.
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.parseColor("#131316"));
        root.addView(web, new FrameLayout.LayoutParams(-1, -1));
        if (Build.VERSION.SDK_INT >= 30) {
            root.setOnApplyWindowInsetsListener((v, insets) -> {
                android.graphics.Insets b = insets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout() | WindowInsets.Type.ime());
                v.setPadding(b.left, b.top, b.right, b.bottom);
                return WindowInsets.CONSUMED;
            });
        }
        setContentView(root);
        if (state != null) web.restoreState(state);
        else web.loadUrl("file:///android_asset/lumen.html");
    }

    /** Small bridge so the page can open other apps (like a router or speaker app). */
    class Native {
        @JavascriptInterface
        public boolean openApp(String pkg) {
            Intent i = getPackageManager().getLaunchIntentForPackage(pkg);
            if (i == null) return false;
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            runOnUiThread(() -> startActivity(i));
            return true;
        }

        @JavascriptInterface
        public void buzz() {
            runOnUiThread(() -> web.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        web.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        web.onResume();
        // Pull fresh state from Home Assistant every time the app comes back
        web.evaluateJavascript("window.lumenResync && lumenResync()", null);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    @Override
    public void onBackPressed() {
        // Let Lumen close an open panel first; otherwise leave the app
        web.evaluateJavascript("window.lumenBack ? lumenBack() : false", r -> {
            if ("true".equals(r)) return;
            if (web.canGoBack()) web.goBack();
            else finish();
        });
    }
}

package com.android.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Mochamad Taufik on 04-Oct-17.
 * Email   : thidayat13@gmail.com
 * Company : TRUSTUDIO
 */

public class WebViewActivity extends AppCompatActivity {

    private WebView myWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.myWebView);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("link_webview");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("Test", "Value is: " + value);

                setWebView(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Test", "Failed to read value.", error.toException());
            }
        });

    }

    private void setWebView(String value) {
        myWebView.loadUrl(value);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

            }
        });
    }

    // Use When the user clicks a link from a web page in your WebView
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // you are using siteView here instead of view
            return true;

        }
    }
}

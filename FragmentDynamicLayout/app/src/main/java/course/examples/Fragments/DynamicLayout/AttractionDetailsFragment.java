package course.examples.Fragments.DynamicLayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AttractionDetailsFragment extends Fragment {

    private static final String TAG = "QuotesFragment";

    private WebView mAttractionQuoteView = null;
    private int mAttractionCurrIdx = -1;
    private int mAttractionQuoteArrLen;

    int getShownIndex() {
        return mAttractionCurrIdx;
    }

    // Show the Quote string at position newIndex
    void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mAttractionQuoteArrLen)
            return;
        Log.i("Click on selection"," "+newIndex);
        mAttractionCurrIdx = newIndex;
        mAttractionQuoteView.loadUrl(AttractionViewerActivity.mAttractionQuoteArray[mAttractionCurrIdx]);

        WebSettings webSettings = mAttractionQuoteView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mAttractionQuoteView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");

        // Inflate the layout defined in quote_fragment.xml
        // The last parameter is false because the returned view does not need to be
        // attached to the container ViewGroup
        return inflater.inflate(R.layout.quote_fragment, container, false);
    }

    // Set up some information about the mQuoteView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        mAttractionQuoteView = (WebView) getActivity().findViewById(R.id.quoteView);
        mAttractionQuoteArrLen = AttractionViewerActivity.mAttractionQuoteArray.length;
        if(savedInstanceState!=null){
            mAttractionCurrIdx = savedInstanceState.getInt("attractionIndex");
        }
        showQuoteAtIndex(mAttractionCurrIdx);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("attractionIndex",mAttractionCurrIdx);

    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
        super.onDestroyView();
    }


}

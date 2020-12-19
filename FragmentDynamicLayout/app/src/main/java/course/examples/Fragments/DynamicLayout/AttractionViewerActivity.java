package course.examples.Fragments.DynamicLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import course.examples.Fragments.DynamicLayout.AttractionTitlesFragment.ListSelector;

public class AttractionViewerActivity extends FragmentActivity implements ListSelector {

    public static String[] mAttractionTitleArray;
    public static String[] mAttractionQuoteArray;

    private AttractionDetailsFragment mAttractionDetailFragment = new AttractionDetailsFragment();
    private AttractionTitlesFragment mAttractionTitleFragment = new AttractionTitlesFragment();

    private android.support.v4.app.FragmentManager mAttractionFragmentManager;

    private FrameLayout mAttractionTitleFrameLayout, mAttractionQuotesFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "AttractionViewer";
    public static int attractionSelection = 0;
    private int attractionOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and quotes
        mAttractionTitleArray = getResources().getStringArray(R.array.attraction_titles);
        mAttractionQuoteArray = getResources().getStringArray(R.array.attraction_websites);

        ActionBar actionBar=getActionBar();
        actionBar.setTitle("Attractions Viewer");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(48,63,159)));
        setContentView(R.layout.main);




        // Get references to the TitleFragment and to the QuotesFragment
        mAttractionTitleFrameLayout = (FrameLayout) findViewById(R.id.title_fragment_container);
        mAttractionQuotesFrameLayout = (FrameLayout) findViewById(R.id.quote_fragment_container);

        // Get reference to the SupportFragmentManager instead of original FragmentManager
        mAttractionFragmentManager = getSupportFragmentManager();
        Log.i("click selection "," "+attractionSelection);

        if(savedInstanceState!=null) {
            Log.i("Click saved"," "+savedInstanceState);
            mAttractionTitleFragment = (AttractionTitlesFragment)
                    mAttractionFragmentManager.getFragment(savedInstanceState,"attractionTitleFragment");
            if(mAttractionFragmentManager.findFragmentByTag("ATTRACTION_QUOTES_FRAGMENT_TAG")!=null) {
                mAttractionDetailFragment = (AttractionDetailsFragment)
                        mAttractionFragmentManager.getFragment(
                                savedInstanceState,"attractionQuotesFragment");
            }
            attractionSelection = savedInstanceState.getInt("attractionSelection");
            Log.i("click selection saved"," "+attractionSelection);

        }

        // Start a new FragmentTransaction with backward compatibility
        android.support.v4.app.FragmentTransaction attractionFragmentTransaction = mAttractionFragmentManager
                .beginTransaction();

        // Add the TitlesFragment to the layout
        // UB: 10/2/2016 Changed add() to replace() to avoid overlapping fragments
        attractionFragmentTransaction.replace(
                R.id.title_fragment_container,
                mAttractionTitleFragment, "ATTRACTION_TITLES_FRAGMENT_TAG");

        // Commit the FragmentTransaction
        attractionFragmentTransaction.commit();

        attractionOrientation = getResources().getConfiguration().orientation;
        if(attractionOrientation == Configuration.ORIENTATION_LANDSCAPE) {

            Log.i("click layout landscape","added");
            // Make the TitleLayout take 1/3 of the layout's width
//            mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//                    MATCH_PARENT, 1f));
//
//            // Make the QuoteLayout take 2/3's of the layout's width
//            mAttractionQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//                    MATCH_PARENT, 2f));
            setLayout();
        }
        else {
            Log.i("click layout portrait","added");
            if(attractionSelection==1) {
                Log.i("click portrait","here");
                mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
                mAttractionQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }
            else {
                Log.i("click else block","here");
                mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mAttractionQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            }

        }

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mAttractionFragmentManager.addOnBackStackChangedListener(
                // UB 2/24/2019 -- Use support version of Listener
                new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
//						mTitleFragment.onUncheckItem();
                        Log.i("Click Backstack","Backstack changed");
                        setLayout();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.res:
                Log.i("Click res in act","entered switch");
                Intent i = new Intent(this, QuoteViewerActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                return true;
            case R.id.att:
                Log.i("Click act in act","entered switch");
                Intent i1 = getIntent();
                startActivity(i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        setLayout();
        mAttractionTitleFragment.onUncheckItem();
    }

    private void setLayout() {
//
//		if(orientation==Configuration.ORIENTATION_LANDSCAPE) {
//			mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//					MATCH_PARENT, 1f));
//
//			// Make the QuoteLayout take 2/3's of the layout's width
//			mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//					MATCH_PARENT, 2f));
//		}
//		else {
//			if(selection==0) {
//				mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
//						MATCH_PARENT, MATCH_PARENT));
//				mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//						MATCH_PARENT));
//			}
//			else {
//				mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//						MATCH_PARENT));
//				mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
//						MATCH_PARENT));
//			}
//		}

//		 Determine whether the QuoteFragment has been added
        if (!mAttractionDetailFragment.isAdded()) {
            Log.i("click layout", "not added");
            // Make the TitleFragment occupy the entire layout
            mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mAttractionQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {
            Log.i("click layout","added");
            // Make the TitleLayout take 1/3 of the layout's width
            mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));

            // Make the QuoteLayout take 2/3's of the layout's width
            mAttractionQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));
        }
    }

    @Override
    public void onSelection(int index) {

        attractionSelection = 1;

        // If the QuoteFragment has not been added, add it now
        if (!mAttractionDetailFragment.isAdded()) {

            Log.i("click here"," "+index);

            // Start a new FragmentTransaction
            // UB 2/24/2019 -- Now must use compatible version of FragmentTransaction
            android.support.v4.app.FragmentTransaction attractionFragmentTransaction = mAttractionFragmentManager
                    .beginTransaction();

            // Add the QuoteFragment to the layout
            attractionFragmentTransaction.add(R.id.quote_fragment_container, mAttractionDetailFragment,"ATTRACTION_QUOTES_FRAGMENT_TAG");

            // Add this FragmentTransaction to the backstack
            attractionFragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            attractionFragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mAttractionFragmentManager.executePendingTransactions();
        }
        if(attractionOrientation==Configuration.ORIENTATION_LANDSCAPE) {
//            setLayout();
        }
        else {
            Log.i("Click on selection"," "+index);
            mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
            mAttractionQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                    MATCH_PARENT));
        }
        if (mAttractionDetailFragment.getShownIndex() != index) {
            Log.i("Click on selection"," "+index);

            // Tell the QuoteFragment to show the quote string at position index
            mAttractionDetailFragment.showQuoteAtIndex(index);

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAttractionFragmentManager.putFragment(outState, "attractionTitleFragment",
                mAttractionFragmentManager.findFragmentByTag("ATTRACTION_TITLES_FRAGMENT_TAG"));
        if(mAttractionFragmentManager.findFragmentByTag("ATTRACTION_QUOTES_FRAGMENT_TAG")!=null) {
            mAttractionFragmentManager.putFragment(outState,"attractionQuotesFragment",
                    mAttractionFragmentManager.findFragmentByTag("ATTRACTION_QUOTES_FRAGMENT_TAG"));
        }
        outState.putInt("attractionSelection",attractionSelection);
        Log.i("click selection on save"," "+attractionSelection);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

}

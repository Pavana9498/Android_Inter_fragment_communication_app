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

import course.examples.Fragments.DynamicLayout.TitlesFragment.ListSelectionListener;

//Several Activity lifecycle methods are instrumented to emit LogCat output
//so you can follow this class' lifecycle
public class QuoteViewerActivity
        extends FragmentActivity
		implements ListSelectionListener {

	public static String[] mTitleArray;
	public static String[] mQuoteArray;


	private QuotesFragment mQuoteFragment = new QuotesFragment();
	private TitlesFragment mTitleFragment = new TitlesFragment();

	// UB 2/24/2019 -- Android Pie twist: Original FragmentManager class is now deprecated.
	private android.support.v4.app.FragmentManager mFragmentManager;

	private FrameLayout mTitleFrameLayout, mQuotesFrameLayout;
	private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	private static final String TAG = "QuoteViewerActivity";
	public static int selection = 0;
	private int orientation;
	private Bundle state=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

		super.onCreate(savedInstanceState);
		state = savedInstanceState;

		// Get the string arrays with the titles and quotes
		mTitleArray = getResources().getStringArray(R.array.restaurant_titles);
		mQuoteArray = getResources().getStringArray(R.array.restaurant_websites);

        ActionBar actionBar=getActionBar();
        actionBar.setTitle("Restaurants Viewer");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(48,63,159)));
		setContentView(R.layout.main);




		// Get references to the TitleFragment and to the QuotesFragment
		mTitleFrameLayout = (FrameLayout) findViewById(R.id.title_fragment_container);
		mQuotesFrameLayout = (FrameLayout) findViewById(R.id.quote_fragment_container);


		// Get reference to the SupportFragmentManager instead of original FragmentManager
		mFragmentManager = getSupportFragmentManager();
		Log.i("click selection "," "+selection);

		if(savedInstanceState!=null) {
			Log.i("Click saved"," "+savedInstanceState);
			mTitleFragment = (TitlesFragment) mFragmentManager.getFragment(savedInstanceState,"titleFragment");
			if(mFragmentManager.findFragmentByTag("QUOTES_FRAGMENT_TAG")!=null) {
				mQuoteFragment = (QuotesFragment) mFragmentManager.getFragment(savedInstanceState,"quotesFragment");
			}
			selection = savedInstanceState.getInt("selection");
			Log.i("click selection saved"," "+selection);

		}

		// Start a new FragmentTransaction with backward compatibility
		android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();

		// Add the TitlesFragment to the layout
		// UB: 10/2/2016 Changed add() to replace() to avoid overlapping fragments
		fragmentTransaction.replace(
		        R.id.title_fragment_container,
				mTitleFragment, "TITLES_FRAGMENT_TAG");
		
		// Commit the FragmentTransaction
		fragmentTransaction.commit();

		orientation = getResources().getConfiguration().orientation;
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

			Log.i("click layout landscape","added");
			// Make the TitleLayout take 1/3 of the layout's width
//			mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//					MATCH_PARENT, 1f));
//
//			// Make the QuoteLayout take 2/3's of the layout's width
//			mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
//					MATCH_PARENT, 2f));
			setLayout();
		}
		else {
			Log.i("click layout portrait","added");
			if(selection==1) {
				Log.i("click portrait","here");
				mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
						MATCH_PARENT));
				mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
						MATCH_PARENT));
			}
			else {
				Log.i("click else block","here");
				mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
						MATCH_PARENT, MATCH_PARENT));
				mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
						MATCH_PARENT));
			}

		}



		// Add a OnBackStackChangedListener to reset the layout when the back stack changes
		mFragmentManager.addOnBackStackChangedListener(
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
				Log.i("Click res in res","entered switch");
				Intent i = getIntent();
				startActivity(i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
				return true;
            case R.id.att:
				Log.i("Click act in res","entered switch");
				Intent i1 = new Intent(this, AttractionViewerActivity.class);
                startActivity(i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


    @Override
	public void onBackPressed() {
		super.onBackPressed();
//		setLayout();
		mTitleFragment.onUncheckItem(); //Unchecks an item when back button is pressed.
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
		if (!mQuoteFragment.isAdded()) {
			Log.i("click layout", "not added");
			// Make the TitleFragment occupy the entire layout
			mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
					MATCH_PARENT, MATCH_PARENT));
			mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT));
		} else {
				Log.i("click layout","added");
			// Make the TitleLayout take 1/3 of the layout's width
			mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT, 1f));

			// Make the QuoteLayout take 2/3's of the layout's width
			mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT, 2f));
		}
	}

	// Implement Java interface ListSelectionListener defined in TitlesFragment
    // Called by TitlesFragment when the user selects an item in the TitlesFragment
	@Override
	public void onListSelection(int index) {

		selection = 1;

		// If the QuoteFragment has not been added, add it now
		if (!mQuoteFragment.isAdded()) {

			Log.i("click here"," "+index);

			// Start a new FragmentTransaction
            // UB 2/24/2019 -- Now must use compatible version of FragmentTransaction
			android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager
					.beginTransaction();

			// Add the QuoteFragment to the layout
			fragmentTransaction.add(R.id.quote_fragment_container, mQuoteFragment,"QUOTES_FRAGMENT_TAG");

			// Add this FragmentTransaction to the backstack
			fragmentTransaction.addToBackStack(null);

			// Commit the FragmentTransaction
			fragmentTransaction.commit();

			// Force Android to execute the committed FragmentTransaction
				mFragmentManager.executePendingTransactions();
		}
		if(orientation==Configuration.ORIENTATION_LANDSCAPE) {
//			setLayout();
		}
		else {
			mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT));
			mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
					MATCH_PARENT));
		}
		if (mQuoteFragment.getShownIndex() != index) {

			// Tell the QuoteFragment to show the quote string at position index
			mQuoteFragment.showQuoteAtIndex(index);
		
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mFragmentManager.putFragment(outState, "titleFragment",
				mFragmentManager.findFragmentByTag("TITLES_FRAGMENT_TAG"));
		if(mFragmentManager.findFragmentByTag("QUOTES_FRAGMENT_TAG")!=null) {
			mFragmentManager.putFragment(outState,"quotesFragment",
					mFragmentManager.findFragmentByTag("QUOTES_FRAGMENT_TAG"));
		}
		outState.putInt("selection",selection);
		Log.i("click selection on save"," "+selection);
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
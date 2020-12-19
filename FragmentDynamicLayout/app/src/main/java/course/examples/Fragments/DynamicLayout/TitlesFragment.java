package course.examples.Fragments.DynamicLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//Several Activity and Fragment lifecycle methods are instrumented to emit LogCat output
//so you can follow the class' lifecycle
// UB 2/24/2019 -- Changed deprecated ListFragment class to support.v4 version
public class TitlesFragment extends android.support.v4.app.ListFragment {
	private static final String TAG = "TitlesFragment";
	private ListSelectionListener mListener = null;
	private int mCurrIdx = -1;

	// Callback interface that allows this Fragment to notify the QuoteViewerActivity when  
	// user clicks on a List Item  
	public interface ListSelectionListener {
		public void onListSelection(int index);
	}

	// Called when the user selects an item from the List
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		if(mCurrIdx!=pos){
			Log.i("click list position", " "+pos);
			mCurrIdx = pos;

			// Inform the QuoteViewerActivity that the item in position pos has been selected
			mListener.onListSelection(pos);

		}
		// Indicates the selected item has been checked
		getListView().setItemChecked(pos, true);
		
	}

	@Override
	public void onAttach(Context activity) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
		
		try {

			// Set the ListSelectionListener for communicating with the QuoteViewerActivity
			// Try casting the containing activity to a ListSelectionListener
			mListener = (ListSelectionListener) activity;
		
		} catch (ClassCastException e) {
			// Cast failed: This is not going to work because containing activity may not
			// have implemented onListSelection() method
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	// UB:  Notice that the superclass's method does an OK job of inflating the
	//      container layout.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		
		// Set the list adapter for the ListView 
		// Discussed in more detail in the user interface classes lesson  
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.title_item, QuoteViewerActivity.mTitleArray));

		// Set the list choice mode to allow only one selection at a time
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		if(savedState != null) {
			mCurrIdx = savedState.getInt("Index");
		}
		if (-1 != mCurrIdx) {
			Log.i("Click"," "+mCurrIdx);
			getListView().setItemChecked(mCurrIdx, true);

			// UB:  10-6-2017 Added this call to handle configuration changes
			// that broke in API 25
			mListener.onListSelection(mCurrIdx);
			Log.i("Click act created","done");

		}
	}

	public void onUncheckItem() // unchecking an item for backward navigation
	{
		getListView().setItemChecked(mCurrIdx,false);
		mCurrIdx = -1;
		QuoteViewerActivity.selection = 0;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("Index",mCurrIdx);

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
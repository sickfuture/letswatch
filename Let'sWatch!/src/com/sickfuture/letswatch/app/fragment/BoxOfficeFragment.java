package com.sickfuture.letswatch.app.fragment;

import android.support.v4.widget.CursorAdapter;

import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;

public class BoxOfficeFragment extends CommonMovieListFragment {

	public BoxOfficeFragment(CursorAdapter adapter, int section, int urlResource) {
		super(adapter, section, urlResource);
	}

}
//	private BoxOfficeCursorAdapter mBoxOfficeCursorAdapter;
//
//	private final Uri mUri = ContractUtils
//			.getProviderUriFromContract(Contract.MovieColumns.class);
//
//	@Override
//	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//		if (InetChecker.checkInetConnection(getSherlockActivity())) {
//			getSherlockActivity().getContentResolver()
//					.delete(mUri,
//							MovieColumns.SECTION + " = ?",
//							new String[] { String
//									.valueOf(Contract.BOX_OFFICE_SECTION) });
//			Intent intent = new Intent(getSherlockActivity(),
//					LoadingService.class);
//			LoadingRequest request = new LoadingRequest(RequestType.GET,
//					getString(R.string.API_BOX_OFFICE_REQUEST_URL),
//					Contract.BOX_OFFICE_SECTION,
//					RequestHelper.PROCESS_MOVIE_LIST, mUri);
//			intent.putExtra("request", request);
//			getSherlockActivity().startService(intent);
//		} else {
//			refreshView.onRefreshComplete();
//		}
//
//	}
//
//	@Override
//	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//		return new CursorLoader(getSherlockActivity(), mUri, null,
//				Contract.MovieColumns.SECTION + " = ?",
//				new String[] { String.valueOf(Contract.BOX_OFFICE_SECTION) },
//				null);
//	}
//
//	@Override
//	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//		/*
//		 * if (cursor.getCount() == 0) { onRefresh(mListView); }
//		 */
//		mBoxOfficeCursorAdapter.swapCursor(data);
//	}
//
//	@Override
//	public void onLoaderReset(Loader<Cursor> loader) {
//		mBoxOfficeCursorAdapter.swapCursor(null);
//	}
//
//	@Override
//	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onScrollStateChanged(AbsListView arg0, int arg1) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onListItemClick(AdapterView<?> list, View view, int position,
//			long id, IListClickable clickable) {
//		Cursor cursor = (Cursor) list.getItemAtPosition(position);
//		Bundle arguments = new Bundle();
//		arguments.putInt(Contract.SECTION, Contract.BOX_OFFICE_SECTION);
//		arguments.putInt(Contract.ID,
//				cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
//		arguments
//				.putInt(MainActivity.FRAGMENT, MainActivity.FRAGMENT_BOXOFFICE);
//		clickable.onItemListClick(arguments);
//	}
//
//	@Override
//	public IntentFilter filter() {
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(LoadingService.ACTION_ON_ERROR);
//		filter.addAction(LoadingService.ACTION_ON_SUCCESS);
//		return filter;
//	}
//
//	@Override
//	public void handleOnRecieve(Context context, Intent intent) {
//		String action = intent.getAction();
//		if (action.equals(LoadingService.ACTION_ON_ERROR)) {
//			mListView.onRefreshComplete();
//			Toast.makeText(getSherlockActivity(),
//					intent.getStringExtra(LoadingService.EXTRA_KEY_MESSAGE),
//					Toast.LENGTH_SHORT).show();
//		} else if (action.equals(LoadingService.ACTION_ON_SUCCESS)) {
//			mListView.onRefreshComplete();
//		}
//
//	}
//
//	@Override
//	public CursorAdapter cursorAdapter() {
//		mBoxOfficeCursorAdapter = new BoxOfficeCursorAdapter(getSherlockActivity(), null);
//		return mBoxOfficeCursorAdapter;
//	}


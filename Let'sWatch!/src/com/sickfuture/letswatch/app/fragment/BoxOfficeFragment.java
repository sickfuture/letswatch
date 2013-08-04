package com.sickfuture.letswatch.app.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.content.contract.Contract;

import java.io.InputStream;

public class BoxOfficeFragment extends CommonMovieListFragment {

    private static final String LOG_TAG = BoxOfficeFragment.class
            .getSimpleName();

    public BoxOfficeFragment() {
        super();
    }

    @Override
    protected int getSection() {
        return Contract.BOX_OFFICE_SECTION;
    }

    @Override
    protected void loadData() {
        DataSourceRequest<InputStream, ContentValues[]> request = new
                DataSourceRequest<InputStream, ContentValues[]>(getActivity().getString(R.string.API_BOX_OFFICE_REQUEST_URL));
        request.setIsCacheable(true);
        SourceService.execute(getSherlockActivity(), request,
                LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
                LetsWatchApplication.BOX_OFFICE_PROCESSOR_SERVICE);
    }

    @Override
    public CursorAdapter cursorAdapter() {
        return new BoxOfficeCursorAdapter(getSherlockActivity(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Activity activity) {

        Log.d(LOG_TAG, "onAttach: ");
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {

        Log.d(LOG_TAG, "onDetach: ");
        super.onDetach();
    }

    @Override
    public void onDestroy() {

        Log.d(LOG_TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onPause() {

        Log.d(LOG_TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onResume() {

        Log.d(LOG_TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onStart() {

        Log.d(LOG_TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public void onStop() {

        Log.d(LOG_TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }
}
// private BoxOfficeCursorAdapter mBoxOfficeCursorAdapter;
//
// private final Uri mUri = ContractUtils
// .getProviderUriFromContract(Contract.MovieColumns.class);
//
// @Override
// public void onRefresh(PullToRefreshBase<ListView> refreshView) {
// if (InetChecker.checkInetConnection(getSherlockActivity())) {
// getSherlockActivity().getContentResolver()
// .delete(mUri,
// MovieColumns.SECTION + " = ?",
// new String[] { String
// .valueOf(Contract.BOX_OFFICE_SECTION) });
// Intent intent = new Intent(getSherlockActivity(),
// LoadingService.class);
// LoadingRequest request = new LoadingRequest(RequestType.GET,
// getString(R.string.API_BOX_OFFICE_REQUEST_URL),
// Contract.BOX_OFFICE_SECTION,
// RequestHelper.PROCESS_MOVIE_LIST, mUri);
// intent.putExtra("request", request);
// getSherlockActivity().startService(intent);
// } else {
// refreshView.onRefreshComplete();
// }
//
// }
//
// @Override
// public Loader<Cursor> onCreateLoader(int id, Bundle args) {
// return new CursorLoader(getSherlockActivity(), mUri, null,
// Contract.MovieColumns.SECTION + " = ?",
// new String[] { String.valueOf(Contract.BOX_OFFICE_SECTION) },
// null);
// }
//
// @Override
// public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
// /*
// * if (cursor.getCount() == 0) { onRefresh(mListView); }
// */
// mBoxOfficeCursorAdapter.swapCursor(data);
// }
//
// @Override
// public void onLoaderReset(Loader<Cursor> loader) {
// mBoxOfficeCursorAdapter.swapCursor(null);
// }
//
// @Override
// public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onScrollStateChanged(AbsListView arg0, int arg1) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onListItemClick(AdapterView<?> list, View view, int position,
// long id, IListClickable clickable) {
// Cursor cursor = (Cursor) list.getItemAtPosition(position);
// Bundle arguments = new Bundle();
// arguments.putInt(Contract.SECTION, Contract.BOX_OFFICE_SECTION);
// arguments.putInt(Contract.ID,
// cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
// arguments
// .putInt(MainActivity.FRAGMENT, MainActivity.FRAGMENT_BOXOFFICE);
// clickable.onItemListClick(arguments);
// }
//
// @Override
// public IntentFilter filter() {
// IntentFilter filter = new IntentFilter();
// filter.addAction(LoadingService.ACTION_ON_ERROR);
// filter.addAction(LoadingService.ACTION_ON_SUCCESS);
// return filter;
// }
//
// @Override
// public void handleOnRecieve(Context context, Intent intent) {
// String action = intent.getAction();
// if (action.equals(LoadingService.ACTION_ON_ERROR)) {
// mListView.onRefreshComplete();
// Toast.makeText(getSherlockActivity(),
// intent.getStringExtra(LoadingService.EXTRA_KEY_MESSAGE),
// Toast.LENGTH_SHORT).show();
// } else if (action.equals(LoadingService.ACTION_ON_SUCCESS)) {
// mListView.onRefreshComplete();
// }
//
// }
//
// @Override
// public CursorAdapter cursorAdapter() {
// mBoxOfficeCursorAdapter = new BoxOfficeCursorAdapter(getSherlockActivity(),
// null);
// return mBoxOfficeCursorAdapter;
// }


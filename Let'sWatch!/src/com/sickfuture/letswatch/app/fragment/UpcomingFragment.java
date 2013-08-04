package com.sickfuture.letswatch.app.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.http.HttpManager.RequestType;
import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.InetChecker;
import com.android.sickfuture.sickcore.utils.ui.ViewHider;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.adapter.UpcomingCursorAdapter;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.MainActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.app.fragment.common.SickCursorListFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.request.LoadingRequest;
import com.sickfuture.letswatch.request.LoadingRequest.RequestHelper;
import com.sickfuture.letswatch.service.LoadingService;

import java.io.InputStream;

public class UpcomingFragment extends CommonMovieListFragment {

    private static final String LOG_TAG = UpcomingFragment.class.getSimpleName();


    @Override
    protected int getSection() {
        return Contract.UPCOMING_SECTION;
    }

    @Override
    protected void loadData() {
        DataSourceRequest<InputStream, ContentValues[]> request = new
                DataSourceRequest<InputStream, ContentValues[]>(getActivity().getString(R.string.API_UPCOMING_REQUEST_URL));
        request.setIsCacheable(true);
        SourceService.execute(getActivity(), request,
                LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
                LetsWatchApplication.UPCOMING_PROCESSOR_SERVICE);
    }

    @Override
    public CursorAdapter cursorAdapter() {
        return new BoxOfficeCursorAdapter(getActivity(), null);
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }
}

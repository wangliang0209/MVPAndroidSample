package com.wl.demo.mvpsample.user.list;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.wl.demo.mvpsample.R;
import com.wl.demo.mvpsample.base.BaseActivity;
import com.wl.demo.mvpsample.net.resp.model.UserListResp;

import butterknife.ButterKnife;

/**
 * Created by wangliang on 16-10-14.
 */

public class ListActivity extends BaseActivity implements ListContact.View {
    private static final String TAG = ListActivity.class.getSimpleName();

    private ListContact.Presenter mPresenter;

    private ListView mListView;

    private UserListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initActionBarWithBack("列表");

        mPresenter = new ListPresenterImpl(this, this);

        mListView = ButterKnife.findById(this, R.id.user_list_lv);

        mAdapter = new UserListAdapter(this);
        mListView.setAdapter(mAdapter);

        mPresenter.getData();
    }

    @Override
    public void showProgress() {
        Log.d("WLTest", "showProgress");
    }

    @Override
    public void hideProgress() {
        Log.d("WLTest", "hideProgress");
    }

    @Override
    public void getDataSucc(UserListResp model) {
        //TODO 页面处理
        if(model != null) {
            mAdapter.refreshData(model.getList());
        }
    }

    @Override
    public void getDataFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}

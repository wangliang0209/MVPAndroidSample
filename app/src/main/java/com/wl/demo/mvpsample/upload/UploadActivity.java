package com.wl.demo.mvpsample.upload;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cocolover2.lis.AlbumHelper;
import com.cocolover2.lis.entity.ImageItem;
import com.wl.demo.mvpsample.R;
import com.wl.demo.mvpsample.base.BaseActivity;
import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;
import com.wl.demo.mvpsample.net.resp.model.UploadResp;
import com.wl.demo.mvpsample.selector.DefaultSelectorActivity;

import java.util.List;

/**
 * Created by wangliang on 16-12-20.
 */

public class UploadActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }

    public void gotoAlbum(View v) {
        Intent intent = new Intent(this, DefaultSelectorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<ImageItem> list = AlbumHelper.getHasSelectImgs();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Log.d("WLTest", list.get(i).imagePath);
                new CommonRequest(this, getTagName()).upload(list.get(i).imagePath, new MySubscriber<UploadResp>() {
                    @Override
                    public void onSucc(UploadResp uploadResp) {
                        if (uploadResp.files != null) {
                            for (int j = 0; j < uploadResp.files.size(); j++) {
                                Log.d("WLTest", "onSucc " + uploadResp.files.get(j));
                            }
                        }

                    }

                    @Override
                    public void onError(String error) {
                        Log.d("WLTest", "onError " + error);
                    }
                });
            }
        }

    }

    @Override
    public String getTagName() {
        return UploadActivity.class.getSimpleName();
    }
}

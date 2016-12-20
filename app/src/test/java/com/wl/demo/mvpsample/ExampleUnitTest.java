package com.wl.demo.mvpsample;

import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;
import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Mock
    private CommonRequest mRequest;

    @Test
    public void testAPI() {

        CommonRequest request = mock(CommonRequest.class); //new CommonRequest(null);//
        MySubscriber<UserDetailResp> obj = new MySubscriber<UserDetailResp>() {
            @Override
            public void onSucc(UserDetailResp userInfo) {
                //Log.d("WLTest", "onSucc");
            }

            @Override
            public void onError(String error) {
                //Log.d("WLTest", "onError");
            }
        };

        request.getUserDetail("1", obj);

        verify(request).getUserDetail("1", obj);
    }
}
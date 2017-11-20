package com.qin.tao.share.app.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;


/**
 * Fragment 基类
 */
public class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected BaseActivity mBaseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mBaseActivity = mActivity instanceof BaseActivity ? (BaseActivity) mActivity : null;
        //        BaseActivityManager.getInstance().addActivity(mActivity);
    }

    /**
     * 在 {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, Bundle)}之后调用，否者会空指针异常
     *
     * @param id
     * @return
     */
    public View findViewById(int id) {
        return getView().findViewById(id);
    }

    public void onScreen() {
    }

    public void startEnterActivity(Class cla) {
        if (mBaseActivity != null && mActivity != null) {
            mBaseActivity.startActivity(new Intent(mActivity, cla));
            mBaseActivity.animLeftToRight();
        } else if (mActivity != null) {
            startActivity(new Intent(mActivity, cla));
            animLeftToRight(mActivity);
        }
    }

    /**
     * 当前Activity进入时的动画
     */
    public void animLeftToRight(Activity activity) {
       // activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}

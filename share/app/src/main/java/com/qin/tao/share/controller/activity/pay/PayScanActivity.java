package com.qin.tao.share.controller.activity.pay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.qin.tao.share.R;
import com.qin.tao.share.app.base.BaseActivity;
import com.qin.tao.share.app.base.BaseTextView;
import com.qin.tao.share.app.intent.IntentKey;
import com.qin.tao.share.app.utils.SDCardUtils;
import com.qin.tao.share.widget.title.TitleView;


/**
 * @author qintao
 *         支付扫码
 */

public class PayScanActivity extends BaseActivity {

    private int payType = 0;
    private ImageView pay_image;
    private BaseTextView tvDex;
    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void receiveParam() {
        payType = getIntent().getIntExtra(IntentKey.PAY_TYPE, 0);
    }


    @Override
    public void initViews() {
        setContentView(R.layout.activity_pay_scan);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("扫码赞赏").setRightText("保存").setTitleOnClickListener(new TitleView.ITitleOnClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onDoneClick() {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), payType == 1 ? R.drawable.wechat_qrcode : R.drawable.alipay_qrcode);
                if (bmp != null) {
                    SDCardUtils.saveImageToGallery(PayScanActivity.this, bmp);
                }
            }
        });
        pay_image = (ImageView) findViewById(R.id.pay_image);
        tvDex = (BaseTextView) findViewById(R.id.tvDex);
    }

    @Override
    public void initData() {
        if (payType == 0)
            return;
        else if (payType == 1) {
            pay_image.setImageResource(R.drawable.wechat_qrcode);
            tvDex.setText("请您保存图片，打开微信扫一扫，扫描此图片");
        } else if (payType == 2) {
            pay_image.setImageResource(R.drawable.alipay_qrcode);
            tvDex.setText("请您保存图片，打开支付宝扫一扫，扫描此图片");
        }
    }

}

package com.qin.tao.share.model.pay;

import com.qin.tao.share.app.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author qintao on 2017/9/14 23:56
 */

public class PayData {
    private volatile static PayData mInstance;
    private List<PayInfo> payInfoList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();

    public static PayData getInstance() {
        if (mInstance == null) {
            synchronized (PayData.class) {
                if (mInstance == null) {
                    mInstance = new PayData();
                }
            }
        }
        return mInstance;
    }

    public void initPayInfo() {
        payInfoList.add(new PayInfo("拿去拿去", 100));
        payInfoList.add(new PayInfo("一路发", 168));
        payInfoList.add(new PayInfo("爱你哦", 520));
        payInfoList.add(new PayInfo("发发发,我们都要发", 888));
        payInfoList.add(new PayInfo("一生一世", 1314));
        payInfoList.add(new PayInfo("一起发大财", 1688));
        payInfoList.add(new PayInfo("天长地久", 999));
        payInfoList.add(new PayInfo("么么哒", 256));
        payInfoList.add(new PayInfo("棒棒哒", 318));
        payInfoList.add(new PayInfo("顺顺利利", 1666));
        payInfoList.add(new PayInfo("666", 666));
        payInfoList.add(new PayInfo("哇,你真是超赞哦", 1212));
    }

    public void initData() {
        stringList.add("打赏2块钱,帮我买杯咖啡,继续开发,谢谢大家!");
        stringList.add("爱,就供养;喜欢,就打赏!2元请我喝咖啡.");
        stringList.add("如果喜欢,请打赏——1元就足够感动我.");
        stringList.add("打赏不能超过你的早餐钱哦.");
        stringList.add("打赏不准超过你工资的一半.");
        stringList.add("如果有用,请我吃3块好吃的.");
        stringList.add("大爷,赏个铜板呗.");
        stringList.add("您打赏的一小步,是我的一大步( •̀ ω •́ ).");
        stringList.add("此处应有打赏.");
        stringList.add("喜欢你就打赏一下.");
        stringList.add("爷,您要是看的乐,打个赏也让小的乐一乐.");
        stringList.add("请简单粗暴地爱我.");
        stringList.add("赏点就好,么么哒～～");
        stringList.add("最喜欢你一言不合就打赏的样子了~~~么么!");
        stringList.add("么么哒,夏天来块儿冰西瓜!");
        stringList.add("听说,打赏我的人最后都找到了真爱.");
        stringList.add("听说打赏我的人都是天使.");
        stringList.add("哇,你真是超赞哦!");
    }

    public String getRoundString() {
        if (CollectionUtils.isEmpty(stringList))
            return null;
        int size = stringList.size();
        int index = new Random().nextInt(size);
        return stringList.get(index);
    }

    public PayInfo getRoundPayInfo(PayInfo info) {

        if (CollectionUtils.isEmpty(payInfoList))
            return null;
        int size = payInfoList.size();
        PayInfo payInfo = null;
        int index = new Random().nextInt(size);
        payInfo = payInfoList.get(index);
        if (info != null && payInfo != null && info.getAmount() == payInfo.getAmount()) {
            return getRoundPayInfo(info);
        }
        return payInfo;
    }
}

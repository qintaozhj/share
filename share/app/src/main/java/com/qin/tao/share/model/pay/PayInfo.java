package com.qin.tao.share.model.pay;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author qintao on 2017/9/14 23:46
 */

public class PayInfo {
    private String description;
    private int amount;
    private DecimalFormat decimalFormat;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public PayInfo() {
    }

    public PayInfo(String description) {
        this.description = description;
    }

    public PayInfo(String description, int amount) {
        this.description = description;
        this.amount = amount;
        decimalFormat = new DecimalFormat("##0.00");
    }

    public String getAmountString() {
        return formatFenToYuan(amount);
    }

    public String getAmountString(float amount) {
        return decimalFormat.format(amount);
    }

    private String formatFenToYuan(int amount) {
        return new BigDecimal(Long.valueOf(amount)).divide(new BigDecimal(100f)).setScale(2).toString();
    }
}

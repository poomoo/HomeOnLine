package com.poomoo.model.response;

/**
 * 类名 TicketBO
 * 描述  优惠券
 * 作者 李苜菲
 * 日期 2016/9/23 14:57
 */
public class TicketBO {
    public int id;
    public double money;//优惠金额
    public String voucherPic;//未使用显示
    public String voucheredPic;//已使用显示
    public String expiredCouponsPic;//已失效显示
    public String updateTime;
    public String periodValidity;
    public String status;//
}
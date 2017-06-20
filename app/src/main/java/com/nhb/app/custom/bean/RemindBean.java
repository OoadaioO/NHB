package com.nhb.app.custom.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nhb.app.custom.BR;


public class RemindBean extends BaseObservable {

	/**
	 * 通知
	 */
	private int     notification_num;
	/**
	 * 私信
	 */
	private int     private_num;
	/**
	 * 回复我的话题
	 */
	private int     reply_msg_num;
	/**
	 * 我的美分
	 */
	private int     points_num;
	/**
	 * 是否弹出提示
	 */
	private boolean alert_rating;
	/**
	 * 提示语
	 */
	private String  message;

	/**
	 * 退款待处理数量
	 *
	 * @since 1.4.0
	 */
	private int refund_processing_total;

	/**
	 * 待办预约数量
	 *
	 * @since 1.5.0
	 */
	private int reserve_num;

	/**
	 * 医院托管代理
	 *
	 * @since 1.7.0
	 */
	private boolean allow_hospital_agent;

	@Bindable
	public int getNotification_num() {
		return notification_num;
	}

	public void setNotification_num(int notification_num) {
		this.notification_num = notification_num;
		notifyPropertyChanged(BR.notification_num);
	}

	@Bindable
	public int getPrivate_num() {
		return private_num;
	}

	public void setPrivate_num(int private_num) {
		this.private_num = private_num;
		notifyPropertyChanged(BR.private_num);
	}

	@Bindable
	public int getReply_msg_num() {
		return reply_msg_num;
	}

	public void setReply_msg_num(int reply_msg_num) {
		this.reply_msg_num = reply_msg_num;
		notifyPropertyChanged(BR.reply_msg_num);
	}

	@Bindable
	public int getPoints_num() {
		return points_num;
	}

	public void setPoints_num(int points_num) {
		this.points_num = points_num;
		notifyPropertyChanged(BR.points_num);
	}

	@Bindable
	public boolean isAlert_rating() {
		return alert_rating;
	}

	public void setAlert_rating(boolean alert_rating) {
		this.alert_rating = alert_rating;
		notifyPropertyChanged(BR.alert_rating);
	}

	@Bindable
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		notifyPropertyChanged(BR.message);
	}

	@Bindable
	public int getRefund_processing_total() {
		return refund_processing_total;
	}

	public void setRefund_processing_total(int refund_processing_total) {
		this.refund_processing_total = refund_processing_total;
		notifyPropertyChanged(BR.refund_processing_total);
	}

	@Bindable
	public int getReserve_num() {
		return reserve_num;
	}

	public void setReserve_num(int reserve_num) {
		this.reserve_num = reserve_num;
		notifyPropertyChanged(BR.reserve_num);
	}

	@Bindable
	public boolean isAllow_hospital_agent() {
		return allow_hospital_agent;
	}

	public void setAllow_hospital_agent(boolean allow_hospital_agent) {
		this.allow_hospital_agent = allow_hospital_agent;
		notifyPropertyChanged(BR.allow_hospital_agent);
	}
}

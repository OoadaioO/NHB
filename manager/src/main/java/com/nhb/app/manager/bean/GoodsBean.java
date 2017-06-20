package com.nhb.app.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fast.library.bean.Pojo;

/**
 * 说明：商品
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/6/24 0:17
 * <p/>
 * 版本：verson 1.0
 */
public class GoodsBean  extends Pojo implements Parcelable{

    /**
     * itemPic : http://img4.imgtn.bdimg.com/it/u=2380563160,451053762&fm=21&gp=0.jpg
     * buyCount : 90
     * itemName : 促销套餐
     * price : 145.2
     * priceInStore : 312.3
     * itemId : 1003534
     */

    public String itemPic;
    public String buyCount;
    public String itemName;
    public String price;
    public String priceInStore;
    public String itemId;

    public GoodsBean(){}

    protected GoodsBean(Parcel in) {
        itemPic = in.readString();
        buyCount = in.readString();
        itemName = in.readString();
        price = in.readString();
        priceInStore = in.readString();
        itemId = in.readString();
    }


    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel in) {
            return new GoodsBean(in);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemPic);
        dest.writeString(buyCount);
        dest.writeString(itemName);
        dest.writeString(price);
        dest.writeString(priceInStore);
        dest.writeString(itemId);
    }
}

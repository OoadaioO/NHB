package com.nhb.app.manager.presenter;

import com.fast.library.utils.UIUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.nhb.app.library.tab.TabEntity;
import com.nhb.app.library.tab.ViewPageInfo;
import com.nhb.app.manager.R;
import com.nhb.app.manager.contract.MainContract;
import com.nhb.app.manager.ui.fragment.HomeFragment;
import com.nhb.app.manager.ui.fragment.MessageFragment;
import com.nhb.app.manager.ui.fragment.MineFragment;

import java.util.ArrayList;

/**
 * 说明：MainPresenterImpl
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/23 10:00
 * <p>
 * 版本：verson 1.0
 */
public class MainPresenterImpl extends MainContract.Presenter{

    private int[] mIconUnselectIds = {
            R.drawable.main_normal, R.drawable.message_normal,
            R.drawable.mine_normal};

    private int[] mIconSelectIds = {
            R.drawable.main_selected, R.drawable.message_selected,
            R.drawable.mine_selected};

    @Override
    public void onStart() {}

    /**
     * 创建首页的Fragmetn
     * @return
     */
    @Override
    public ArrayList<CustomTabEntity> createTab() {
        String []titles = UIUtils.getStringArray(R.array.tab_bottom);
        ArrayList<CustomTabEntity> list = new ArrayList<>(titles.length);
        for (int i = 0;i < titles.length;i++){
            list.add(new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return list;
    }

    @Override
    public ArrayList<ViewPageInfo> createFragment() {
        ArrayList<ViewPageInfo> list = new ArrayList<>(3);
        list.add(0, new ViewPageInfo("",HomeFragment.class,null));
        list.add(1, new ViewPageInfo("",MessageFragment.class,null));
        list.add(2, new ViewPageInfo("",MineFragment.class,null));
        return list;
    }

}

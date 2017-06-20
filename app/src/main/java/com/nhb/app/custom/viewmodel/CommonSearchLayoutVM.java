package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.ItemVMFactory;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

import java.util.List;

import static com.nhb.app.custom.base.FetchDataViewModel.CODE_ERROR;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-01
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class CommonSearchLayoutVM extends NHBViewModel {

    public ObservableField<String> content = new ObservableField<>();

    public ObservableField<String> hintContent = new ObservableField<>();

    public ObservableInt imeOptions = new ObservableInt(EditorInfo.IME_ACTION_SEARCH);

    public ObservableBoolean defaultModeShow = new ObservableBoolean(true);

    public ObservableBoolean recyclerViewShow = new ObservableBoolean(false);

    public ObservableArrayList<String> searchKeyWordsList = new ObservableArrayList<>();

    // 是否可以搜索
    public ObservableBoolean canAutoComplete = new ObservableBoolean();
    //是否item点击
    public ObservableField<String> isItemClick = new ObservableField<>();

    public ItemVMFactory keyWordsFactory = new ItemVMFactory() {
        @NonNull
        @Override
        public RecyclerItemVM getItemVM(int viewType) {
            return new SearchKeywordItemVM();
        }
    };

    public void clickKeyWords(View view, int position) {
        // 清空搜索框，隐藏搜索结果，隐藏输入法键盘
        isItemClick.set(searchKeyWordsList.get(position));
    }

    public void fetchKeyWords(String keywords) {
        fetchRemoteData(FetchDataViewModel.CODE_INIT, RestClient.getService().toGetKeywords(keywords.trim()));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        searchKeyWordsList.clear();
        searchKeyWordsList.addAll((List<String>) data);
        canAutoComplete.set(CODE_ERROR != requestCode);
    }

    public void contentChange(Editable editable) {
        content.set(editable.toString().trim());
    }
}

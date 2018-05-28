package com.logindemo.service;

import java.util.Date;

/**
 * Created by marnon on 2018/5/22.
 */
public interface StoreService {

    String getStoreIndex(int employer, Date today);

    void getHotGoods();

    void getActivity();
}

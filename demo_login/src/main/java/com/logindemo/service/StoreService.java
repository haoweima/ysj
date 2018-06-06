package com.logindemo.service;

import com.logindemo.domain.ActivitySaleCouple;
import com.logindemo.domain.Goods;
import com.logindemo.domain.ReportDomain;

import java.util.List;

/**
 * Created by marnon on 2018/5/22.
 */
public interface StoreService {

    ReportDomain getStoreIndex(int employer, String today);

    List<Goods> getHotGoods(int employer, List<ReportDomain> var, String dateS, String dateE);

    List<ActivitySaleCouple> getActivity(int employer, List<Goods> hotGoods);
}

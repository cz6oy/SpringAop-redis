package cn.czboy.service;

import cn.czboy.annotation.AddCache;
import cn.czboy.dao.GoodsDao;
import cn.czboy.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsDao goodsDao;

    @Override
    @AddCache
    public Goods queryGoods(String id) {
        Goods goods = goodsDao.queryGoods(id);
        if (goods == null) {
            System.out.println("Goods为空！！！");
        }
        return goods;
    }
}

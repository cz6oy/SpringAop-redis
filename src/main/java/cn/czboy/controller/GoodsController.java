package cn.czboy.controller;

import cn.czboy.entity.Goods;
import cn.czboy.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/query")
    public void queryGoods(String id) {
        Goods goods = goodsService.queryGoods(id);
        System.out.println("=========="+goods);
    }
}

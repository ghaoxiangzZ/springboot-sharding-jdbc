package com.haoxiang.sharding.contoller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.haoxiang.sharding.mapper.ConfigRepository;
import com.haoxiang.sharding.mapper.OrderItemRepository;
import com.haoxiang.sharding.mapper.OrderRepository;
import com.haoxiang.sharding.model.TConfig;
import com.haoxiang.sharding.model.TOrder;
import com.haoxiang.sharding.model.TOrderDto;
import com.haoxiang.sharding.model.TOrderItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping
public class TestController {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private OrderItemRepository orderItemRepository;
    @Resource
    private ConfigRepository configRepository;

    @GetMapping(value = "/insertOrder")
    @ResponseBody
    public String insertOrder() {
        for (int i = 0; i < 50; i++) {
            TOrder order = new TOrder();
            order.setOrderNo("A000" + i);
            order.setCreateName("订单 " + i);
            order.setPrice(new BigDecimal(ThreadLocalRandom.current().nextInt(2000)));
            orderRepository.insert(order);
            order = orderRepository.selectOne(Wrappers.<TOrder>lambdaQuery().eq(TOrder::getOrderNo, "A000" + i));
            TOrderItem orderItem = new TOrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setItemName("服务项目" + i);
            orderItem.setPrice(order.getPrice());
            orderItemRepository.insert(orderItem);
        }
        return "success";
    }

    @GetMapping(value = "/config")
    @ResponseBody
    public String config() {
        TConfig tConfig = new TConfig();
        tConfig.setRemark("测试广播表");
        tConfig.setCreateTime(new Date());
        tConfig.setLastModifyTime(new Date());
        configRepository.insert(tConfig);
        return "success";
    }


    @GetMapping(value = "/getOneOrder")
    @ResponseBody
    public String getOne(Long orderId) {
        return JSON.toJSONString(orderRepository.selectOne(Wrappers.<TOrder>lambdaQuery().eq(TOrder::getOrderId, orderId)));
    }


    @GetMapping(value = "/selectOrderAndItemByOrderId")
    @ResponseBody
    public String selectOrderAndItemByOrderId(Long orderId) {
        return JSON.toJSONString(orderRepository.selectOrderAndItemByOrderId(orderId));
    }

    @GetMapping("/orderList")
    @ResponseBody
    public Object list() {
        return orderRepository.selectList(new QueryWrapper<>());
    }

    @GetMapping(value = "/selectOrderListPage")
    @ResponseBody
    public List<TOrderDto> selectOrderListPage() {
        return orderRepository.selectOrderListPage();
    }

    public static void main(String[] args) {

    }
}

package com.haoxiang.sharding.contoller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.haoxiang.sharding.mapper.*;
import com.haoxiang.sharding.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Controller
@RequestMapping
public class TestController {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private OrderOldRepository orderOldRepository;
    @Resource
    private OrderItemRepository orderItemRepository;
    @Resource
    private OrderItemOldRepository orderItemOldRepository;
    @Resource
    private ConfigRepository configRepository;

    @GetMapping(value = "/insertOrder")
    @ResponseBody
    public String insertOrder() {
        for (int i = 0; i < 50; i++) {
            int val = i * 2;
            TOrderOld orderOld = new TOrderOld();
            orderOld.setOrderNo("B000" + val);
            orderOld.setOrderId(ThreadLocalRandom.current().nextLong(2000));
            orderOld.setCreateName("订单 " + val);
            orderOld.setPrice(new BigDecimal(ThreadLocalRandom.current().nextInt(2000)));
            orderOldRepository.insert(orderOld);
            /*TOrderItemOld orderItemOld = new TOrderItemOld();
            orderItemOld.setOrderId(orderOld.getOrderId());
            orderItemOld.setItemName("服务项目" + val);
            orderItemOld.setPrice(orderOld.getPrice());
            orderItemOldRepository.insert(orderItemOld);*/
            // 假装是异步处理，不在一个事务
            TOrder order = new TOrder();
            order.setOrderNo("B000" + val);
            order.setCreateName("订单 " + val);
            order.setPrice(new BigDecimal(ThreadLocalRandom.current().nextInt(2000)));
            orderRepository.insert(order);
           /* TOrderItem orderItem = new TOrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setItemName("服务项目" + val);
            orderItem.setPrice(order.getPrice());
            orderItemRepository.insert(orderItem);*/
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
        TOrder orderNew = orderRepository.selectOne(Wrappers.<TOrder>lambdaQuery().eq(TOrder::getOrderId, orderId));
        if (Objects.isNull(orderNew)) {
            log.info("查询新表");
            TOrderOld oldOrder = orderOldRepository.selectOne(Wrappers.<TOrderOld>lambdaQuery().eq(TOrderOld::getOrderId, orderId));
            return JSON.toJSONString(oldOrder);
        }
        return JSON.toJSONString(orderNew);
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

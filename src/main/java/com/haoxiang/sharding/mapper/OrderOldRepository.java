package com.haoxiang.sharding.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haoxiang.sharding.model.TOrderOld;
import com.haoxiang.sharding.model.TOrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OrderOldRepository extends BaseMapper<TOrderOld> {


    List<TOrderDto> selectOrderAndItemByOrderId(@Param("orderId") Long orderId);

    List<TOrderDto> selectOrderListPage(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("orderId") Long orderId);

    List<TOrderDto> selectOrderListPage();
}


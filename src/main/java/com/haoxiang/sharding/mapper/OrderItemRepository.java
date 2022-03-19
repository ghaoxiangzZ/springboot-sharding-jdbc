package com.haoxiang.sharding.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haoxiang.sharding.model.TOrderItem;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderItemRepository extends BaseMapper<TOrderItem> {

}


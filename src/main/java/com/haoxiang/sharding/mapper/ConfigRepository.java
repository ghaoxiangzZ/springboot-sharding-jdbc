package com.haoxiang.sharding.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haoxiang.sharding.model.TConfig;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ConfigRepository extends BaseMapper<TConfig> {

}


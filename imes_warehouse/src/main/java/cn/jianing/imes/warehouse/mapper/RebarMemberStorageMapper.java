package cn.jianing.imes.warehouse.mapper;

import cn.jianing.imes.domain.warehouse.RebarMemberStorage;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@org.apache.ibatis.annotations.Mapper
public interface RebarMemberStorageMapper extends Mapper<RebarMemberStorage>, InsertListMapper<RebarMemberStorage> {
}

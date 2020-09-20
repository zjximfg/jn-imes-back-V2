package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.OutWarehouseRebarMember;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.jianing.imes.warehouse.mapper.OutWarehouseRebarMemberMapper;
import cn.jianing.imes.warehouse.service.OutWarehouseRebarMemberService;
@Service
public class OutWarehouseRebarMemberServiceImpl implements OutWarehouseRebarMemberService{

    @Resource
    private OutWarehouseRebarMemberMapper outWarehouseRebarMemberMapper;
    @Resource
    private IdWorker idWorker;

    @Override
    public void insertOutWarehouseRebarMember(OutWarehouseRebarMember outWarehouseRebarMember) {
        outWarehouseRebarMember.setId(String.valueOf(idWorker.nextId()));
        outWarehouseRebarMemberMapper.insertSelective(outWarehouseRebarMember);
    }
}

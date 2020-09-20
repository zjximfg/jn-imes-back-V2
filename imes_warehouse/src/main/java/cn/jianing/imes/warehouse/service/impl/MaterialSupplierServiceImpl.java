package cn.jianing.imes.warehouse.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.domain.warehouse.MaterialSupplier;
import cn.jianing.imes.warehouse.mapper.MaterialSupplierMapper;
import cn.jianing.imes.warehouse.service.MaterialSupplierService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MaterialSupplierServiceImpl implements MaterialSupplierService {

    @Resource
    private MaterialSupplierMapper materialSupplierMapper;
    @Resource
    private IdWorker idWorker;

    @Override
    public Page<MaterialSupplier> getMaterialSupplierPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map) {
        PageHelper.startPage(current, pageSize);
        Example example = new Example(MaterialSupplier.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId", companyId);
        criteria.andEqualTo("isDeleted", false);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("key")) {
                criteria.orLike("name", "%" + entry.getValue() + "%").orLike("liaison", "%" + entry.getValue() + "%").orLike("address", "%" + entry.getValue() + "%");
            }
        }
        return (Page<MaterialSupplier>) materialSupplierMapper.selectByExample(example);
    }

    @Override
    public MaterialSupplier getMaterialSupplierById(String id) {
        return materialSupplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteMaterialSupplierById(String id) {
        MaterialSupplier materialSupplier = materialSupplierMapper.selectByPrimaryKey(id);
        if (materialSupplier != null ) {
            materialSupplier.setIsDeleted(true);
            materialSupplierMapper.updateByPrimaryKeySelective(materialSupplier);
        }
    }

    @Override
    public void updateMaterialSupplierById(MaterialSupplier materialSupplier) {
        MaterialSupplier find = materialSupplierMapper.selectByPrimaryKey(materialSupplier.getId());
        if (find != null ) {
            materialSupplierMapper.updateByPrimaryKeySelective(materialSupplier);
        }
    }

    @Override
    public void insertMaterialSupplier(MaterialSupplier materialSupplier) {
        String id = String.valueOf(idWorker.nextId());
        materialSupplier.setId(id);
        materialSupplier.setIsDeleted(false);
        materialSupplierMapper.insertSelective(materialSupplier);
    }

    @Override
    public List<MaterialSupplier> getMaterialSupplierListByCompanyId(String companyId) {
        MaterialSupplier materialSupplier = new MaterialSupplier();
        materialSupplier.setCompanyId(companyId);
        materialSupplier.setIsDeleted(false);
        return materialSupplierMapper.select(materialSupplier);
    }
}

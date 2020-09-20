package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.MaterialSupplier;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MaterialSupplierService {
    Page<MaterialSupplier> getMaterialSupplierPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);

    MaterialSupplier getMaterialSupplierById(String id);

    void deleteMaterialSupplierById(String id);

    void updateMaterialSupplierById(MaterialSupplier materialSupplier);

    void insertMaterialSupplier(MaterialSupplier materialSupplier);

    List<MaterialSupplier> getMaterialSupplierListByCompanyId(String companyId);
}

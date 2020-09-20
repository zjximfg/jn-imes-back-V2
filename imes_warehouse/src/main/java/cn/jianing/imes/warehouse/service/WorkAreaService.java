package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.WorkArea;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface WorkAreaService {
    Page<WorkArea> getWorkAreaPageByCompanyId(int current, int pageSize, String companyId, Map<String, Object> map);

    WorkArea getWorkAreaById(String id);

    void deleteWorkAreaById(String id);

    void updateWorkAreaById(WorkArea workArea);

    void insertWorkArea(WorkArea workArea);

    List<WorkArea> getWorkAreaListByCompanyId(String companyId);
}

package cn.jianing.imes.warehouse.service;

import cn.jianing.imes.domain.warehouse.RebarEntry;
import cn.jianing.imes.domain.warehouse.RebarMemberStorage;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RebarMemberStorageService {
    boolean insertRebarMemberStorageList(String rebarStorageId, RebarEntry rebarEntry);

    Page<RebarMemberStorage> getRebarMemberStoragePageByRebarStorageId(int current, int pageSize, String rebarStorageId, Map<String, Object> map);

    List<RebarMemberStorage> getRebarMemberStorageListByRebarStorageId(String id);
}

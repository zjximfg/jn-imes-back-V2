package cn.jianing.imes.company.service.impl;

import cn.jianing.imes.common.utils.IdWorker;
import cn.jianing.imes.company.mapper.CompanyMapper;
import cn.jianing.imes.company.service.CompanyService;
import cn.jianing.imes.domain.company.Company;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private IdWorker idWorker;


    @Override
    public void insertCompany(Company company) {
        company.setId(String.valueOf(idWorker.nextId()));
        company.setIsDeleted(false);
        company.setState(1);
        company.setAuditState("未审核");
        company.setBalance(0D);
        company.setCreateTime(new Date());
        companyMapper.insertSelective(company);
    }

    @Override
    public void deleteCompanyById(String id) {
        Company company = companyMapper.selectByPrimaryKey(id);
        company.setIsDeleted(false);
        updateCompanyById(id, company);
    }

    @Override
    public void updateCompanyById(String id, Company company) {
        company.setId(id);
        companyMapper.updateByPrimaryKeySelective(company);
    }

    @Override
    public Company getCompanyById(String id) {
        return companyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Company> getCompanyList() {
        Company company = new Company();
        company.setIsDeleted(false);
        return companyMapper.select(company);
    }
}

package cn.jianing.imes.company.service;

import cn.jianing.imes.domain.company.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    void insertCompany(Company company);

    void deleteCompanyById(String id);

    void updateCompanyById(String id, Company company);

    Company getCompanyById(String id);

    List<Company> getCompanyList();
}

package cn.jianing.imes.company.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.company.service.CompanyService;
import cn.jianing.imes.domain.company.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("company/companies")
public class CompanyController extends BaseController {

    @Resource
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> insertCompany(@RequestBody Company company) {
        companyService.insertCompany(company);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable("id") String id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCompanyById(@PathVariable("id") String id, @RequestBody Company company) {
        companyService.updateCompanyById(id, company);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") String id) {
        if (id.equals("current")) {
            id = currentUser.getCompanyId();
        }
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping()
    public ResponseEntity<List<Company>> getCompanyList() {
        List<Company> companyList = companyService.getCompanyList();
        return ResponseEntity.ok(companyList);
    }

}

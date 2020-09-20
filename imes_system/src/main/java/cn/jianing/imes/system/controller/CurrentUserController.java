package cn.jianing.imes.system.controller;

import cn.jianing.imes.common.controller.BaseController;
import cn.jianing.imes.common.entity.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("system/currentUser")
public class CurrentUserController extends BaseController {


    @GetMapping
    public ResponseEntity<CurrentUser> getCurrentUser() {
        return ResponseEntity.ok(currentUser);
    }

}

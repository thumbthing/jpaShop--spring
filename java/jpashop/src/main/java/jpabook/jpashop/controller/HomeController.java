package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j          // 이거는 로그 관련된 것
public class HomeController {

    @GetMapping("/")
    public String home() {
      log.info("Home controller");
      return "home";
    }
}

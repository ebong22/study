package com.example.demo.fomatter_test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("formatter")
public class FormatterTestController {

    @ResponseBody
    @GetMapping("stringToInteger")
    public String stringToInteger (@RequestParam Integer data){
      log.info("data = {}", data);
      log.info("dataType = {}", data.getClass().getName());
      return "ok";
    }

    @GetMapping("integerToString")
    public String integerToString (@RequestParam Integer data, Model model){
        log.info("data = {}", data);
        log.info("dataType = {}", data.getClass().getName());
        model.addAttribute("data", data);
        return "formatter_test/formatter";
    }

}

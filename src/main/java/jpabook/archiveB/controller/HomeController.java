package jpabook.archiveB.controller;

import jpabook.archiveB.web.dto.book.BookSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
public class HomeController {
    @RequestMapping("/")
    public String home(@ModelAttribute("bookSearch")BookSearch bookSearch) {
        log.info("home controller");
        return "home";
    }

}

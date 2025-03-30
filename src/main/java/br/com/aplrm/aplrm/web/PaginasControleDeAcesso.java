package br.com.aplrm.aplrm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaginasControleDeAcesso {

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/publico/index.html");
        return modelAndView;
    }
}

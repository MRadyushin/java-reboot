package ru.sberbank.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.edu.service.FinanceService;

@Controller
@RequestMapping(value = "/finance")
public class FinanceController {

    private FinanceService financeService;

    @Autowired
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public ModelAndView finance() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/finance.jsp");

        return modelAndView;
    }


    @PostMapping
    public ModelAndView calculate(@RequestParam(name = "sum") String sum,
                                  @RequestParam(name = "percentage") String percentage,
                                  @RequestParam(name = "years") String years) {
        ModelAndView modelAndView = new ModelAndView();
        double result = 0;

        try {
            result = financeService.complexPercent(sum, percentage, years);

            modelAndView.setViewName("/result.jsp");
            modelAndView.addObject("result", String.format("%.2f", result));
        } catch (IllegalArgumentException e) {
            modelAndView.setViewName("/error.jsp");
            modelAndView.addObject("messages", new String[]{e.getMessage()});
        }

        return modelAndView;
    }
}
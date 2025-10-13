package com.learnharbor.Scholaro.controller;


import com.learnharbor.Scholaro.model.Holiday;
import com.learnharbor.Scholaro.repository.HolidaysRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
public class HolidaysController {

    private final HolidaysRepository holidaysRepository;

    public HolidaysController(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    @GetMapping("/holidays/{display}")
    public String displayHolidays(Model model, @PathVariable String display) {
        if(null != display && display.equals("all")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        }
        else if(null != display && display.equals("federal")) {
            model.addAttribute("federal", true);
        }
        else if(null != display && display.equals("festival")) {
            model.addAttribute("festival", true);
        }
        Iterable<Holiday> holidays = holidaysRepository.findAll();
        List<Holiday> holidaysList = StreamSupport.stream(holidays.spliterator(), false).collect(Collectors.toList());
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidaysList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
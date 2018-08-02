package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.repository.ErrorRecord;
import edu.xidian.sselab.cloudcourse.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/test")
public class test {
    private final ErrorRecord repository;

    @Autowired
    public test(ErrorRecord repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "错误数据");
        model.addAttribute("condition", new Record());
        return "test";
    }

    @PostMapping("")
    public String post(Model model, Record record) throws IOException {
        //ErrorRecord aa=new ErrorRecord();
        List<Record> recordList = repository.findAllByRecord();
        model.addAttribute("recordList", recordList);
        model.addAttribute("title", "错误数据");
    //    model.addAttribute("condition", record);
        return "test";
    }
}

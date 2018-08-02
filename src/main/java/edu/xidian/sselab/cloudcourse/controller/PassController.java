package edu.xidian.sselab.cloudcourse.controller;


import com.sun.prism.impl.Disposer;
import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.domain.passRecord;
import edu.xidian.sselab.cloudcourse.repository.PassCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/pass_count")
public class PassController {

    private final PassCount tabledata;

    @Autowired
    public PassController(PassCount tabledata) {
        this.tabledata = tabledata;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "过车次数");
        model.addAttribute("condition", new passRecord());
        return "pass_count";
    }

    @PostMapping("")
    public String post(Model model, passRecord record) throws IOException {
        List<passRecord> recordList = tabledata.findAllByRecord(record);
        model.addAttribute("recordList", recordList);
        model.addAttribute("title", "过车次数");
        model.addAttribute("condition", record);
        return "pass_count";
    }
}

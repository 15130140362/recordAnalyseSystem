package edu.xidian.sselab.cloudcourse.controller;


import com.sun.prism.impl.Disposer;
import edu.xidian.sselab.cloudcourse.domain.MeetRecord;
import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.domain.passRecord;
import edu.xidian.sselab.cloudcourse.repository.MeetCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/meet_count")
public class MeetController {

    private final MeetCount tabledata;

    @Autowired
    public MeetController(MeetCount tabledata) {
        this.tabledata = tabledata;
    }

    @GetMapping("")
    public String pass_count(Model model) {
     //   System.out.println("start get");
        model.addAttribute("title", "两车相遇次数");
        model.addAttribute("condition", new MeetRecord());
 //       System.out.println("end get");
        return "meet_count";
    }

    @PostMapping("")
    public String post(Model model, MeetRecord record) throws IOException {   //record1表示相遇次数表中的记录
    //    System.out.println("start post");
        List<MeetRecord> recordList = tabledata.findAllByRecord(record);
        model.addAttribute("recordList", recordList);
        model.addAttribute("title", "两车相遇次数");
    //    System.out.println("返回record");
        model.addAttribute("condition", record);
        return "meet_count";
    }
}

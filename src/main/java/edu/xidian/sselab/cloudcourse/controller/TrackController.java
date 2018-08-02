package edu.xidian.sselab.cloudcourse.controller;

import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/track")
public class TrackController {


    private final RecordRepository repository;


    @Autowired
    public TrackController(RecordRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "login.do")
    public @ResponseBody
    Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入请求");
        String msg="";
        System.out.println(request.getParameter("name"));
        //创建map对象key值存储时间，value存储精度和维度
        Record temp = new Record();
        Map<String, Object> map = new HashMap<String, Object>();
        map.clear();
        String key = request.getParameter("name");
        System.out.println(key);
        Record record = new Record();
        record.setEid(key);
        List<Record> recordList = repository.findAllByRecord(record);
        // Map<String,String> map=new
        Iterator<Record> iterator = recordList.iterator();
        while (iterator.hasNext()) {
            temp =iterator.next();
            String  value=String.valueOf(temp.getLongitude())+" "+String.valueOf(temp.getLatitude())+" "+String.valueOf(temp.getAddress());
            map.put(String.valueOf(temp.getTime()),value);
        }
        Object[] key2=map.keySet().toArray();
        //按照key进行排序

        Arrays.sort(key2);

        //String msg=null;
        for (int i = 0; i < key2.length; i++) {
            msg+=" "+key2[i]+" "+map.get(key2[i]);
            System.out.println("Time: "+key2[i]+"longtiudde and altitude"+map.get(key2[i]));
        }
        System.out.println(msg);
        map.put("msg",msg);
        return map;
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("title", "轨迹重现");
        model.addAttribute("condition", new Record());
        return "track";
    }

    @PostMapping("")
    public String post(Model model, Record record) {
        List<Record> recordList = repository.findAllByRecord(record);
        model.addAttribute("recordList", recordList);
        model.addAttribute("title", "轨迹重现");
        model.addAttribute("condition", record);
        return "track";
    }

}

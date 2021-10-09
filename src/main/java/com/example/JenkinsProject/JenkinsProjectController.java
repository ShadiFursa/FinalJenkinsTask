package com.example.JenkinsProject;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@RestController
public class JenkinsProjectController {
    private ArrayList<Item> items = new ArrayList();

    public JenkinsProjectController() {
    }

    @PostConstruct
    private void loadData() {
        GrabData data = new GrabData("http://www.ynet.co.il/Integration/StoryRss2.xml");
        this.items = data.Readitems();
    }

    @GetMapping({"/"})
    public String hello(Model model) {
        model.addAttribute("items", this.items);
        return "table.html";
    }
}

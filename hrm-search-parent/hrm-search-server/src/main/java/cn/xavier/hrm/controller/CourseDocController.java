package cn.xavier.hrm.controller;

import cn.xavier.hrm.doc.CourseDoc;
import cn.xavier.hrm.repository.CourseDocRepository;
import cn.xavier.hrm.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zheng-Wei Shui
 * @date 12/22/2021
 */
@RestController
@RequestMapping("/courseDoc")
public class CourseDocController {

    @Autowired
    private CourseDocRepository repository;

    @PostMapping("/onlineCourse")
    public AjaxResult onlineCourse(@RequestBody List<CourseDoc> courseDocs) {
        repository.saveAll(courseDocs);
        return AjaxResult.me();
    }

    @PostMapping("/offlineCourse")
    public AjaxResult offlineCourse(@RequestBody List<CourseDoc> courseDocs) {
        repository.deleteAll(courseDocs);
        return AjaxResult.me();
    }
}


package cn.himawari.history.controller;

import cn.himawari.history.service.HistoryService;
import cn.himawari.pojo.History;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping("save")
    public R save(@RequestBody History history){
        return historyService.save(history);
    }

    @PostMapping("list")
    public R list(@RequestBody History history){
        return historyService.list(history.getUserId());
    }
    @PostMapping("remove")
    public R remove(@RequestBody History history){
        return historyService.remove(history);
    }

}

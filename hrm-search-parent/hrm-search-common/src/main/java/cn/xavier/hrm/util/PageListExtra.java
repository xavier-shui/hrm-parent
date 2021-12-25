package cn.xavier.hrm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//分页对象：easyui只需两个属性，total(总数),datas（分页数据）就能实现分页
public class PageListExtra<T> {
    private long total;
    private List<T> rows = new ArrayList<>();
    private Map<String, List<Pair<String, Long>>> map = new HashMap<>();

    public Map<String, List<Pair<String, Long>>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Pair<String, Long>>> map) {
        this.map = map;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageListExtra{" +
                "total=" + total +
                ", rows=" + rows +
                ", map=" + map +
                '}';
    }

    //提供有参构造方法，方便测试
    public PageListExtra(long total, List<T> rows, Map<String, List<Pair<String, Long>>> map) {
        this.total = total;
        this.rows = rows;
        this.map = map;
    }

    //除了有参构造方法，还需要提供一个无参构造方法
    public PageListExtra() {
    }
}

package com.cdxt.common.pojo.vo;


import lombok.Data;

import java.util.List;


public class ResponseListVO<T> extends ResponseBaseVO {
    private long pageNum = 1;//当前页
    private long pageSize = 10;// 分页大小
    private long pages;//总页数
    private long total;//总记录数
    /*private long NextPage;//下一页
    private long PrePage;//上一页
    private long firstPage;//第一页
    private long lastPage;//最后页*/
    private List<T> records;

    public ResponseListVO() {
    }

    public ResponseListVO(long pageNum, long pageSize, long total, List<T> records) {
        this.pageNum =pageNum;
        this.pageSize =pageSize;
        this.total = total;
        this.records =records;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}

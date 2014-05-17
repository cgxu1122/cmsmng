package com.ifhz.core.base.page;


public class Pagination implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int DEFAULT_PAGE_SIZE = 10;
    private int DEFAULT_CURRENTPAGE = 1;

    private int pageSize; // 每页默认10条数据
    private int currentPage; // 当前页
    private int totalPages; // 总页数
    private int totalCount; // 总数据数

    public Pagination(int totalCount, int pageSize) {
        this.init(totalCount, pageSize);
    }

    public Pagination() {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.currentPage = DEFAULT_CURRENTPAGE;
    }

    /**
     * 初始化分页参数:需要先设置totalRows
     */

    public void init(int totalCount, int pageSize) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        if ((totalCount % pageSize) == 0) {
            totalPages = totalCount / pageSize;
        } else {
            totalPages = totalCount / pageSize + 1;
        }

    }

    public void init(int totalCount, int pageSize, int currentPage) {
        this.currentPage = currentPage;
        this.init(totalCount, pageSize);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

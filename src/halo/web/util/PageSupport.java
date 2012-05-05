package halo.web.util;

public class PageSupport {

    /**
     * 当前页号
     */
    private int page = 1;

    /**
     * 总记录数
     */
    private int totalCount = 0;

    /**
     * 每页多少条
     */
    private int size = 10;

    /**
     * 数据库开始记录号
     */
    private int begin;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 显示的最小页号
     */
    private int beginPage;

    /**
     * 显示的最大页号
     */
    private int endPage;

    private PageSupport() {
    }

    public static PageSupport getInstance(int page, int size) {
        PageSupport ph = new PageSupport();
        ph.setSize(size);
        ph.setPage(page);
        return ph;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public static PageSupport getInstance(int size) {
        PageSupport ph = new PageSupport();
        ph.setPage(1);
        ph.setSize(size);
        return ph;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 1) {
            this.page = 1;
        }
        else {
            this.page = page;
        }
        this.begin = (page - 1) * this.size;
        if (begin < 0) {
            begin = 0;
        }
    }

    public void makePage(int totalCount) {
        this.totalCount = totalCount;
        pageCount = (totalCount + size - 1) / size;
        if (page > pageCount) {
            page = pageCount;
        }
        if (page < 1) {
            page = 1;
        }
        begin = (page - 1) * size;
    }

    public int getTotalPage() {
        if (totalCount % size == 0)
            return totalCount / size;
        return (totalCount / size) + 1;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.makePage(totalCount);
    }

    public void outPageNo(int showLen) {
        int mid = showLen / 2;
        if (pageCount - page > mid) {
            beginPage = (page > mid) ? page - mid + 1 : 1;
            endPage = beginPage + showLen - 1;
            endPage = (endPage > pageCount) ? pageCount : endPage;
        }
        else {
            beginPage = (pageCount > showLen) ? pageCount - showLen + 1 : 1;
            endPage = pageCount;
        }
    }
}
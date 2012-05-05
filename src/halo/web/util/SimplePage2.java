package halo.web.util;

public class SimplePage2 {

    /**
     * 数据获取的开始记录号
     */
    private int begin;

    /**
     * 每次获取的数据量
     */
    private int size;

    /**
     * 当前页
     */
    private int page;

    /**
     * 页面显示的开始页号数量
     */
    private int beginShowPage;

    /**
     * 页面显示的结束页号数量
     */
    private int endShowPage;

    /**
     * 页面显示的页号数量
     */
    private int showNum;

    /**
     * 是否显示上一页/下一页的链接
     */
    private boolean showBorder;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 数据总量
     */
    private int len;

    /**
     * @param len
     * @param showNum
     * @param showBorder
     */
    public SimplePage2(int len, int page, int size, int showNum,
            boolean showBorder) {
        if (len < 0) {
            throw new IllegalArgumentException("len must bigger than -1");
        }
        if (page <= 0) {
            this.page = 1;
        }
        if (size < 0) {
            throw new IllegalArgumentException("len must bigger than 0");
        }
        if (showNum < 0) {
            throw new IllegalArgumentException("len must bigger than 0");
        }
        this.len = len;
        this.size = size;
        this.page = page;
        this.showNum = showNum;
        this.showBorder = showBorder;
        this.begin = (this.page - 1) * this.size;
        this.pageCount = (this.len + this.size - 1) / this.size;
        if (this.begin < 0) {
            this.begin = 0;
        }
        if (this.pageCount <= this.showNum) {
            this.beginShowPage = 1;
            this.endShowPage = this.pageCount;
        }
        else {
            int tmpEndShowPage = this.page + this.showNum - 1;
            int distance = tmpEndShowPage - this.pageCount;
            if (distance >= 0) {// 超出最大页数
                this.endShowPage = this.pageCount;
            }
            else {
                this.endShowPage = tmpEndShowPage;
            }
            this.beginShowPage = this.endShowPage - this.showNum + 1;
        }
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getBeginShowPage() {
        return beginShowPage;
    }

    public int getEndShowPage() {
        return endShowPage;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public int getBegin() {
        return begin;
    }

    public int getSize() {
        return size;
    }

    public int getPage() {
        return page;
    }

    public int getShowNum() {
        return showNum;
    }

    public static void main(String[] args) {
        SimplePage2 pageEx = new SimplePage2(200, 35, 6, 10, false);
        System.out.println("pageCount : " + pageEx.getPageCount());
        System.out.println("beginShowPage : " + pageEx.getBeginShowPage());
        System.out.println("endShowPage : " + pageEx.getEndShowPage());
    }
}

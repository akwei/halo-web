package halo.web.util;

public class SimplePage {

    private int size;

    private int page;

    private int listSize;

    private int begin;

    private boolean hasNext;

    public SimplePage(int size, int page) {
        this.size = size;
        this.setPage(page);
    }

    public void setPage(int page) {
        this.page = page;
        this.begin = (page - 1) * size;
        if (begin < 0) {
            begin = 0;
        }
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
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

    public int getListSize() {
        return listSize;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
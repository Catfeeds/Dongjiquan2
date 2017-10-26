package com.dongjiquan.dongjiquan.bean;

import java.util.List;

/**
 * Created by wlx on 2017/10/9.
 */

public class BannerItemBean {

    /**
     * extra : {"totalSize":1,"size":10,"totalPage":1,"page":0}
     * imgCastinglist : [{"imgCastingTitle":"12434355466556","imgCastingContentUrl":"http://www.cankaoxiaoxi.com/china/20170410/1867877.shtml","id":1,"imgCastingImgUrl":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1572218599,2033634256&fm=80&w=179&h=119&img.JPEG","imgCastingType":"首页"}]
     */

    private ExtraBean extra;
    private List<ImgCastinglistBean> imgCastinglist;

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public List<ImgCastinglistBean> getImgCastinglist() {
        return imgCastinglist;
    }

    public void setImgCastinglist(List<ImgCastinglistBean> imgCastinglist) {
        this.imgCastinglist = imgCastinglist;
    }

    public static class ExtraBean {
        /**
         * totalSize : 1
         * size : 10
         * totalPage : 1
         * page : 0
         */

        private int totalSize;
        private int size;
        private int totalPage;
        private int page;

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    public static class ImgCastinglistBean {
        /**
         * imgCastingTitle : 12434355466556
         * imgCastingContentUrl : http://www.cankaoxiaoxi.com/china/20170410/1867877.shtml
         * id : 1
         * imgCastingImgUrl : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1572218599,2033634256&fm=80&w=179&h=119&img.JPEG
         * imgCastingType : 首页
         */

        private String imgCastingTitle;
        private String imgCastingContentUrl;
        private int id;
        private String imgCastingImgUrl;
        private String imgCastingType;

        public String getImgCastingTitle() {
            return imgCastingTitle;
        }

        public void setImgCastingTitle(String imgCastingTitle) {
            this.imgCastingTitle = imgCastingTitle;
        }

        public String getImgCastingContentUrl() {
            return imgCastingContentUrl;
        }

        public void setImgCastingContentUrl(String imgCastingContentUrl) {
            this.imgCastingContentUrl = imgCastingContentUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgCastingImgUrl() {
            return imgCastingImgUrl;
        }

        public void setImgCastingImgUrl(String imgCastingImgUrl) {
            this.imgCastingImgUrl = imgCastingImgUrl;
        }

        public String getImgCastingType() {
            return imgCastingType;
        }

        public void setImgCastingType(String imgCastingType) {
            this.imgCastingType = imgCastingType;
        }
    }
}

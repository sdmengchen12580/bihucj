package com.aganyun.acode.http.content.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class GetFlashsBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"count":21,"rows":[{"id":"473656617334734967","appId":"470898763183944521","tTitle":"蔡维德：区块链国际发展趋势与政策解读","author":"","cFrom":"金色财经","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会·区块链行业发展论坛在大会期间\u2026\u2026","tMain":"<p style=\"text-align:justify\">7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会&middot;区块链行业发展论坛在大会期间召开，论坛以&ldquo;区块链创新技术应用&rdquo;为理念，以&ldquo;区块链推动行业发展&rdquo;为核心内容，邀请政府主管部门领导，以及全球区块链行业学者、投资机构、顶级技术人才、领军企业负责人等行业精英，共同探讨区块链全球发展趋势和中国行业布局新机遇，助力区块链赋能实体经济，并实现更大商","processDate":"2018-07-30 15:26:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://www.bihucj.com/uploads/bihucj/files/%E6%9C%AA%E6%A0%87%E9%A2%98-1%20%2822%29.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-30 15:28:24","addTimeStr":"2018-07-30 15:28","updateTime":"2018-07-31 17:21:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617334734967.html"},{"id":"473656617334734930","appId":"470898763183944521","tTitle":"南非财政部呼吁免征加密货币交易增值税","author":"","cFrom":"金色财经","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"对于生活在南非的居民来说，使用加密货币正在变得越来越方便。在过去的一年时间里，南非加密货币普及度呈指数级增长\u2026\u2026","tMain":"<p style=\"text-align:justify\"><span style=\"font-size:14px\"><strong>比特币7月28日讯<\/strong>&nbsp;对于生活在南非的居民来说，使用加密货币正在变得越来越方便。在过去的一年时间里，南非加密货币普及度呈指数级增长。就在最近，针对加密货币征税，南非财政部提出对《税法修正法案》（TLAB）进行一些修订。根据当地媒体透露，如果本次法案修订建议申请被采纳，那么南非的比特币交易将免征增值税。<\/span><\/p>\r\n\r\n<p><span","processDate":"2018-07-30 10:41:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://www.bihucj.com/uploads/bihucj/files/%E6%9C%AA%E6%A0%87%E9%A2%98-2%20%2815%29.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-30 10:43:09","addTimeStr":"2018-07-30 10:43","updateTime":"2018-07-31 17:21:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617334734930.html"}]}}
     */

    private int code;
    private DataBean data;
    private List<MessagesBean> messages;

    public static class MessagesBean {
        private int id;
        private String message;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public static class DataBean {
        /**
         * response : {"count":21,"rows":[{"id":"473656617334734967","appId":"470898763183944521","tTitle":"蔡维德：区块链国际发展趋势与政策解读","author":"","cFrom":"金色财经","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会·区块链行业发展论坛在大会期间\u2026\u2026","tMain":"<p style=\"text-align:justify\">7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会&middot;区块链行业发展论坛在大会期间召开，论坛以&ldquo;区块链创新技术应用&rdquo;为理念，以&ldquo;区块链推动行业发展&rdquo;为核心内容，邀请政府主管部门领导，以及全球区块链行业学者、投资机构、顶级技术人才、领军企业负责人等行业精英，共同探讨区块链全球发展趋势和中国行业布局新机遇，助力区块链赋能实体经济，并实现更大商","processDate":"2018-07-30 15:26:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://www.bihucj.com/uploads/bihucj/files/%E6%9C%AA%E6%A0%87%E9%A2%98-1%20%2822%29.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-30 15:28:24","addTimeStr":"2018-07-30 15:28","updateTime":"2018-07-31 17:21:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617334734967.html"},{"id":"473656617334734930","appId":"470898763183944521","tTitle":"南非财政部呼吁免征加密货币交易增值税","author":"","cFrom":"金色财经","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"对于生活在南非的居民来说，使用加密货币正在变得越来越方便。在过去的一年时间里，南非加密货币普及度呈指数级增长\u2026\u2026","tMain":"<p style=\"text-align:justify\"><span style=\"font-size:14px\"><strong>比特币7月28日讯<\/strong>&nbsp;对于生活在南非的居民来说，使用加密货币正在变得越来越方便。在过去的一年时间里，南非加密货币普及度呈指数级增长。就在最近，针对加密货币征税，南非财政部提出对《税法修正法案》（TLAB）进行一些修订。根据当地媒体透露，如果本次法案修订建议申请被采纳，那么南非的比特币交易将免征增值税。<\/span><\/p>\r\n\r\n<p><span","processDate":"2018-07-30 10:41:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://www.bihucj.com/uploads/bihucj/files/%E6%9C%AA%E6%A0%87%E9%A2%98-2%20%2815%29.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-30 10:43:09","addTimeStr":"2018-07-30 10:43","updateTime":"2018-07-31 17:21:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617334734930.html"}]}
         */

        private ResponseBean response;

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public static class ResponseBean {
            /**
             * count : 21
             * rows : [{"id":"473656617334734967","appId":"470898763183944521","tTitle":"蔡维德：区块链国际发展趋势与政策解读","author":"","cFrom":"金色财经","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会·区块链行业发展论坛在大会期间\u2026\u2026","tMain":"<p style=\"text-align:justify\">7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会&middot;区块链行业发展论坛在大会期间召开，论坛以&ldquo;区块链创新技术应用&rdquo;为理念，以&ldquo;区块链推动行业发展&rdquo;为核心内容，邀请政府主管部门领导，以及全球区块链行业学者、投资机构、顶级技术人才、领军企业负责人等行业精英，共同探讨区块链全球发展趋势和中国行业布局新机遇，助力区块链赋能实体经济，并实现更大商","processDate":"2018-07-30 15:26:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://www.bihucj.com/uploads/bihucj/files/%E6%9C%AA%E6%A0%87%E9%A2%98-1%20%2822%29.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-30 15:28:24","addTimeStr":"2018-07-30 15:28","updateTime":"2018-07-31 17:21:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617334734967.html"},{"id":"473656617334734930","appId":"470898763183944521","tTitle":"南非财政部呼吁免征加密货币交易增值税","author":"","cFrom":"金色财经","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"对于生活在南非的居民来说，使用加密货币正在变得越来越方便。在过去的一年时间里，南非加密货币普及度呈指数级增长\u2026\u2026","tMain":"<p style=\"text-align:justify\"><span style=\"font-size:14px\"><strong>比特币7月28日讯<\/strong>&nbsp;对于生活在南非的居民来说，使用加密货币正在变得越来越方便。在过去的一年时间里，南非加密货币普及度呈指数级增长。就在最近，针对加密货币征税，南非财政部提出对《税法修正法案》（TLAB）进行一些修订。根据当地媒体透露，如果本次法案修订建议申请被采纳，那么南非的比特币交易将免征增值税。<\/span><\/p>\r\n\r\n<p><span","processDate":"2018-07-30 10:41:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://www.bihucj.com/uploads/bihucj/files/%E6%9C%AA%E6%A0%87%E9%A2%98-2%20%2815%29.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-30 10:43:09","addTimeStr":"2018-07-30 10:43","updateTime":"2018-07-31 17:21:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617334734930.html"}]
             */

            private int count;
            private List<RowsBean> rows;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<RowsBean> getRows() {
                return rows;
            }

            public void setRows(List<RowsBean> rows) {
                this.rows = rows;
            }

            public static class RowsBean {
                /**
                 * id : 473656617334734967
                 * appId : 470898763183944521
                 * tTitle : 蔡维德：区块链国际发展趋势与政策解读
                 * author :
                 * cFrom : 金色财经
                 * tLjdz :
                 * tOrder : 10000
                 * tDjcs : 0
                 * briefIntro : 7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会·区块链行业发展论坛在大会期间……
                 * tMain : <p style="text-align:justify">7月11日，由中国互联网协会主办，金色财经独家承办，创奇社交电商研究中心协办的2018中国互联网大会&middot;区块链行业发展论坛在大会期间召开，论坛以&ldquo;区块链创新技术应用&rdquo;为理念，以&ldquo;区块链推动行业发展&rdquo;为核心内容，邀请政府主管部门领导，以及全球区块链行业学者、投资机构、顶级技术人才、领军企业负责人等行业精英，共同探讨区块链全球发展趋势和中国行业布局新机遇，助力区块链赋能实体经济，并实现更大商
                 * processDate : 2018-07-30 15:26:00
                 * dirtreeId : 470898763183944532
                 * keywords :
                 * imgUrl : http://www.bihucj.com/uploads/bihucj/files/%E6%9C%AA%E6%A0%87%E9%A2%98-1%20%2822%29.jpg
                 * ctrl0 :
                 * ctrl1 :
                 * ctrl2 :
                 * ctrl3 :
                 * delFlag : 0
                 * addTime : 2018-07-30 15:28:24
                 * addTimeStr : 2018-07-30 15:28
                 * updateTime : 2018-07-31 17:21:23
                 * time :
                 * dateFormat :
                 * typeName : 快讯
                 * detailH5 : http://otenda.project.njagan.org/m/article-473656617334734967.html
                 */

                private String id;
                private String appId;
                private String tTitle;
                private String author;
                private String cFrom;
                private String tLjdz;
                private int tOrder;
                private int tDjcs;
                private String briefIntro;
                private String tMain;
                private String processDate;
                private String dirtreeId;
                private String keywords;
                private String imgUrl;
                private String ctrl0;
                private String ctrl1;
                private String ctrl2;
                private String ctrl3;
                private int delFlag;
                private String addTime;
                private String addTimeStr;
                private String updateTime;
                private String time;
                private String dateFormat;
                private String typeName;
                private String detailH5;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getAppId() {
                    return appId;
                }

                public void setAppId(String appId) {
                    this.appId = appId;
                }

                public String getTTitle() {
                    return tTitle;
                }

                public void setTTitle(String tTitle) {
                    this.tTitle = tTitle;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public String getCFrom() {
                    return cFrom;
                }

                public void setCFrom(String cFrom) {
                    this.cFrom = cFrom;
                }

                public String getTLjdz() {
                    return tLjdz;
                }

                public void setTLjdz(String tLjdz) {
                    this.tLjdz = tLjdz;
                }

                public int getTOrder() {
                    return tOrder;
                }

                public void setTOrder(int tOrder) {
                    this.tOrder = tOrder;
                }

                public int getTDjcs() {
                    return tDjcs;
                }

                public void setTDjcs(int tDjcs) {
                    this.tDjcs = tDjcs;
                }

                public String getBriefIntro() {
                    return briefIntro;
                }

                public void setBriefIntro(String briefIntro) {
                    this.briefIntro = briefIntro;
                }

                public String getTMain() {
                    return tMain;
                }

                public void setTMain(String tMain) {
                    this.tMain = tMain;
                }

                public String getProcessDate() {
                    return processDate;
                }

                public void setProcessDate(String processDate) {
                    this.processDate = processDate;
                }

                public String getDirtreeId() {
                    return dirtreeId;
                }

                public void setDirtreeId(String dirtreeId) {
                    this.dirtreeId = dirtreeId;
                }

                public String getKeywords() {
                    return keywords;
                }

                public void setKeywords(String keywords) {
                    this.keywords = keywords;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }

                public String getCtrl0() {
                    return ctrl0;
                }

                public void setCtrl0(String ctrl0) {
                    this.ctrl0 = ctrl0;
                }

                public String getCtrl1() {
                    return ctrl1;
                }

                public void setCtrl1(String ctrl1) {
                    this.ctrl1 = ctrl1;
                }

                public String getCtrl2() {
                    return ctrl2;
                }

                public void setCtrl2(String ctrl2) {
                    this.ctrl2 = ctrl2;
                }

                public String getCtrl3() {
                    return ctrl3;
                }

                public void setCtrl3(String ctrl3) {
                    this.ctrl3 = ctrl3;
                }

                public int getDelFlag() {
                    return delFlag;
                }

                public void setDelFlag(int delFlag) {
                    this.delFlag = delFlag;
                }

                public String getAddTime() {
                    return addTime;
                }

                public void setAddTime(String addTime) {
                    this.addTime = addTime;
                }

                public String getAddTimeStr() {
                    return addTimeStr;
                }

                public void setAddTimeStr(String addTimeStr) {
                    this.addTimeStr = addTimeStr;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getDateFormat() {
                    return dateFormat;
                }

                public void setDateFormat(String dateFormat) {
                    this.dateFormat = dateFormat;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public String getDetailH5() {
                    return detailH5;
                }

                public void setDetailH5(String detailH5) {
                    this.detailH5 = detailH5;
                }
            }
        }
    }
}

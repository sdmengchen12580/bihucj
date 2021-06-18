package com.aganyun.acode.http.content.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/29.
 */


public class GetNewsBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"advRows":[{"id":"473857410113470444",
     * "dirtreeId":"473903936722436096",
     * "dirtreeName":"",
     * "advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg",
     * "linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11",
     * "updateTime":"2018-07-27 17:50:11"},
     * {"id":"473857410113470455","dirtreeId":"473903936722436096","dirtreeName":"","advWord1":"","advWor
     * d2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b035
     * 7c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27
     * 17:50:11","updateTime":"2018-07-27 17:50:11"},{"id":"473857410113470461","dirtreeId":"47390393672
     * 2436096","dirtreeName":"","advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11","updateTime":"2018-07-27 17:50:11"}],"count":74,"rows":[{"id":"473644369384046592","tTitle":"金色盘面 | 7月30日 夜评 BTC整理接近尾声 随时面临突破","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 20:48:56","dirtreeId":"473449222432096256","keywords":"","author":"金色盘面","cFrom":"壹码","imgUrl":"https://img.jinse.com/781684_image1.png","briefIntro":"BTC依然没有明显的趋向性，短线维持4浪整理的判断，目标位依然设定在7400附近，当然，这个位置出现横盘整理，时间换空间也有可能，但我们，不赌！","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/bitcoin/220349.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369384046592.html"},{"id":"473644369237245952","tTitle":"路印开发光锥中继，要做去中心化交易所生态","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 19:21:15","dirtreeId":"473449222432096256","keywords":"","author":"赵飞（破局）","cFrom":"壹码","imgUrl":"https://img.jinse.com/999651_image1.png","briefIntro":"不少用户把路印理解为去中心化交易所，路印不是去中心化交易所，它是一个协议，一个可以将项目连接成生态的协议。","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/blockchain/220292.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369237245952.html"},{"id":"473644369094639616","tTitle":"区块链赋能体育:结合实体经济 \u201c重启\u201d中国足球","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 18:11:51","dirtreeId":"473449222432096256","keywords":"","author":"Carol L","cFrom":"壹码","imgUrl":"https://img.jinse.com/999427_image1.png","briefIntro":"从经济上来看，体育行业先天赚钱慢、赚钱少等特点往往不被资本追逐，而其高壁垒特性也不易被取代和突破，由此，资本与体育事业发展之间并没有形成良性的循环。","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/blockchain/220269.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369094639616.html"}]}}
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
         * response : {"advRows":[{"id":"473857410113470444","dirtreeId":"473903936722436096","dirtreeName":"","advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11","updateTime":"2018-07-27 17:50:11"},{"id":"473857410113470455","dirtreeId":"473903936722436096","dirtreeName":"","advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11","updateTime":"2018-07-27 17:50:11"},{"id":"473857410113470461","dirtreeId":"473903936722436096","dirtreeName":"","advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11","updateTime":"2018-07-27 17:50:11"}],"count":74,"rows":[{"id":"473644369384046592","tTitle":"金色盘面 | 7月30日 夜评 BTC整理接近尾声 随时面临突破","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 20:48:56","dirtreeId":"473449222432096256","keywords":"","author":"金色盘面","cFrom":"壹码","imgUrl":"https://img.jinse.com/781684_image1.png","briefIntro":"BTC依然没有明显的趋向性，短线维持4浪整理的判断，目标位依然设定在7400附近，当然，这个位置出现横盘整理，时间换空间也有可能，但我们，不赌！","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/bitcoin/220349.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369384046592.html"},{"id":"473644369237245952","tTitle":"路印开发光锥中继，要做去中心化交易所生态","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 19:21:15","dirtreeId":"473449222432096256","keywords":"","author":"赵飞（破局）","cFrom":"壹码","imgUrl":"https://img.jinse.com/999651_image1.png","briefIntro":"不少用户把路印理解为去中心化交易所，路印不是去中心化交易所，它是一个协议，一个可以将项目连接成生态的协议。","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/blockchain/220292.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369237245952.html"},{"id":"473644369094639616","tTitle":"区块链赋能体育:结合实体经济 \u201c重启\u201d中国足球","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 18:11:51","dirtreeId":"473449222432096256","keywords":"","author":"Carol L","cFrom":"壹码","imgUrl":"https://img.jinse.com/999427_image1.png","briefIntro":"从经济上来看，体育行业先天赚钱慢、赚钱少等特点往往不被资本追逐，而其高壁垒特性也不易被取代和突破，由此，资本与体育事业发展之间并没有形成良性的循环。","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/blockchain/220269.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369094639616.html"}]}
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
             * advRows : [{"id":"473857410113470444","dirtreeId":"473903936722436096","dirtreeName":"","advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11","updateTime":"2018-07-27 17:50:11"},{"id":"473857410113470455","dirtreeId":"473903936722436096","dirtreeName":"","advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11","updateTime":"2018-07-27 17:50:11"},{"id":"473857410113470461","dirtreeId":"473903936722436096","dirtreeName":"","advWord1":"","advWord2":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg","linkUrl":"","displayOrder":0,"ctrl0":"","ctrl1":"","ctrl2":"","addTime":"2018-07-27 17:50:11","updateTime":"2018-07-27 17:50:11"}]
             * count : 74
             * rows : [{"id":"473644369384046592","tTitle":"金色盘面 | 7月30日 夜评 BTC整理接近尾声 随时面临突破","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 20:48:56","dirtreeId":"473449222432096256","keywords":"","author":"金色盘面","cFrom":"壹码","imgUrl":"https://img.jinse.com/781684_image1.png","briefIntro":"BTC依然没有明显的趋向性，短线维持4浪整理的判断，目标位依然设定在7400附近，当然，这个位置出现横盘整理，时间换空间也有可能，但我们，不赌！","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/bitcoin/220349.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369384046592.html"},{"id":"473644369237245952","tTitle":"路印开发光锥中继，要做去中心化交易所生态","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 19:21:15","dirtreeId":"473449222432096256","keywords":"","author":"赵飞（破局）","cFrom":"壹码","imgUrl":"https://img.jinse.com/999651_image1.png","briefIntro":"不少用户把路印理解为去中心化交易所，路印不是去中心化交易所，它是一个协议，一个可以将项目连接成生态的协议。","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/blockchain/220292.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369237245952.html"},{"id":"473644369094639616","tTitle":"区块链赋能体育:结合实体经济 \u201c重启\u201d中国足球","tLjdz":"","tOrder":0,"tDjcs":0,"processDate":"2018-07-30 18:11:51","dirtreeId":"473449222432096256","keywords":"","author":"Carol L","cFrom":"壹码","imgUrl":"https://img.jinse.com/999427_image1.png","briefIntro":"从经济上来看，体育行业先天赚钱慢、赚钱少等特点往往不被资本追逐，而其高壁垒特性也不易被取代和突破，由此，资本与体育事业发展之间并没有形成良性的循环。","tMain":"<p>来源：<a href=\"https://www.jinse.com/news/blockchain/220269.html\" target=\"_blank\">金色财经<\/a><\/p>","addTime":"2018-07-31 00:13:37","addTimeStr":"2018-07-31 00:13","updateTime":"2018-07-31 00:13:37","ctrl0":"","ctrl1":"","ctrl2":"","time":"","dateFormat":"","detailH5":"http://otenda.project.njagan.org/m/article-473644369094639616.html"}]
             */

            private int count;
            private List<AdvRowsBean> advRows;
            private List<RowsBean> rows;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<AdvRowsBean> getAdvRows() {
                return advRows;
            }

            public void setAdvRows(List<AdvRowsBean> advRows) {
                this.advRows = advRows;
            }

            public List<RowsBean> getRows() {
                return rows;
            }

            public void setRows(List<RowsBean> rows) {
                this.rows = rows;
            }

            public static class AdvRowsBean {
                /**
                 * id : 473857410113470444
                 * dirtreeId : 473903936722436096
                 * dirtreeName :
                 * advWord1 :
                 * advWord2 :
                 * imgUrl : http://otenda.project.njagan.org/oss/otenda/20180729/c233f43a5bdb78dbcca1a62b0357c8df.jpg
                 * linkUrl :
                 * displayOrder : 0
                 * ctrl0 :
                 * ctrl1 :
                 * ctrl2 :
                 * addTime : 2018-07-27 17:50:11
                 * updateTime : 2018-07-27 17:50:11
                 */

                private String id;
                private String dirtreeId;
                private String dirtreeName;
                private String advWord1;
                private String advWord2;
                private String imgUrl;
                private String linkUrl;
                private int displayOrder;
                private String ctrl0;
                private String ctrl1;
                private String ctrl2;
                private String addTime;
                private String updateTime;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getDirtreeId() {
                    return dirtreeId;
                }

                public void setDirtreeId(String dirtreeId) {
                    this.dirtreeId = dirtreeId;
                }

                public String getDirtreeName() {
                    return dirtreeName;
                }

                public void setDirtreeName(String dirtreeName) {
                    this.dirtreeName = dirtreeName;
                }

                public String getAdvWord1() {
                    return advWord1;
                }

                public void setAdvWord1(String advWord1) {
                    this.advWord1 = advWord1;
                }

                public String getAdvWord2() {
                    return advWord2;
                }

                public void setAdvWord2(String advWord2) {
                    this.advWord2 = advWord2;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }

                public String getLinkUrl() {
                    return linkUrl;
                }

                public void setLinkUrl(String linkUrl) {
                    this.linkUrl = linkUrl;
                }

                public int getDisplayOrder() {
                    return displayOrder;
                }

                public void setDisplayOrder(int displayOrder) {
                    this.displayOrder = displayOrder;
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

                public String getAddTime() {
                    return addTime;
                }

                public void setAddTime(String addTime) {
                    this.addTime = addTime;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
            }

            public static class RowsBean {
                /**
                 * id : 473644369384046592
                 * tTitle : 金色盘面 | 7月30日 夜评 BTC整理接近尾声 随时面临突破
                 * tLjdz :
                 * tOrder : 0
                 * tDjcs : 0
                 * processDate : 2018-07-30 20:48:56
                 * dirtreeId : 473449222432096256
                 * keywords :
                 * author : 金色盘面
                 * cFrom : 壹码
                 * imgUrl : https://img.jinse.com/781684_image1.png
                 * briefIntro : BTC依然没有明显的趋向性，短线维持4浪整理的判断，目标位依然设定在7400附近，当然，这个位置出现横盘整理，时间换空间也有可能，但我们，不赌！
                 * tMain : <p>来源：<a href="https://www.jinse.com/news/bitcoin/220349.html" target="_blank">金色财经</a></p>
                 * addTime : 2018-07-31 00:13:37
                 * addTimeStr : 2018-07-31 00:13
                 * updateTime : 2018-07-31 00:13:37
                 * ctrl0 :
                 * ctrl1 :
                 * ctrl2 :
                 * time :
                 * dateFormat :
                 * detailH5 : http://otenda.project.njagan.org/m/article-473644369384046592.html
                 */

                private String id;
                private String tTitle;
                private String tLjdz;
                private int tOrder;
                private int tDjcs;
                private String processDate;
                private String dirtreeId;
                private String keywords;
                private String author;
                private String cFrom;
                private String imgUrl;
                private String briefIntro;
                private String tMain;
                private String addTime;
                private String addTimeStr;
                private String updateTime;
                private String ctrl0;
                private String ctrl1;
                private String ctrl2;
                private String time;
                private String dateFormat;
                private String detailH5;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTTitle() {
                    return tTitle;
                }

                public void setTTitle(String tTitle) {
                    this.tTitle = tTitle;
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

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
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

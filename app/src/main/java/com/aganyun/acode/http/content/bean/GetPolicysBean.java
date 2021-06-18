package com.aganyun.acode.http.content.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class GetPolicysBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"count":21,"rows":[{"id":"9","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":5,"briefIntro":"","tMain":"","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 15:22:56","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-9.html"},{"id":"10","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":1,"briefIntro":"","tMain":"","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 15:22:57","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-10.html"},{"id":"11","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":1,"briefIntro":"","tMain":"\r\n\t\t<p style=\"text-align: justify;\">另外，路印在技术上也不断更新换代，最值得一提的就是路印的中继网络技术。中继是路印协议生态非常重要的一部分，它将各个交易者订单进行撮合配对，然后将配对成功的订单广播到区块链中。交易要流畅，很大一部分就是靠路印的中继网络，路印团队也在上面付出了相当大的功夫。目前，路印基金会与UpBlockchain共同出资研发了第二代中继系统，被称为光锥中继（中继2.0）。<\/p>","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 23:24:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-11.html"},{"id":"473656617280208901","appId":"470898763183944521","tTitle":"青岛市将建设\u201c青岛量子安全区块链创新业务示范网\u201d","author":"","cFrom":"壹码","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"青岛市市北区将发挥区块链资源整合优势，依托\u201c青岛量子保密通信城域网\u201d和青岛链湾研究院的区块链业务平台","tMain":"<p><span style=\"font-family:helvetica neue,helvetica,arial,microsoft yahei,微软雅黑,pingfang sc,hiragino sans gb,stheiti,simsun,sans-serif; font-size:14px\">据半岛都市报消息，作为今年亮点，青岛市市北区将发挥区块链资源整合优势，依托&ldquo;青岛量子保密通信城域网&rdquo;和青岛链湾研究院的区块链业务平台，建设&ldquo;青岛量子安全区块链创新业务示范","processDate":"2018-06-24 20:24:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"/uploads/bihucj/files/1529896024700.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-06-24 20:25:33","addTimeStr":"2018-06-24 20:25","updateTime":"2018-07-31 01:18:59","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617280208901.html"}]}}
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
         * response : {"count":21,"rows":[{"id":"9","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":5,"briefIntro":"","tMain":"","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 15:22:56","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-9.html"},{"id":"10","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":1,"briefIntro":"","tMain":"","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 15:22:57","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-10.html"},{"id":"11","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":1,"briefIntro":"","tMain":"\r\n\t\t<p style=\"text-align: justify;\">另外，路印在技术上也不断更新换代，最值得一提的就是路印的中继网络技术。中继是路印协议生态非常重要的一部分，它将各个交易者订单进行撮合配对，然后将配对成功的订单广播到区块链中。交易要流畅，很大一部分就是靠路印的中继网络，路印团队也在上面付出了相当大的功夫。目前，路印基金会与UpBlockchain共同出资研发了第二代中继系统，被称为光锥中继（中继2.0）。<\/p>","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 23:24:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-11.html"},{"id":"473656617280208901","appId":"470898763183944521","tTitle":"青岛市将建设\u201c青岛量子安全区块链创新业务示范网\u201d","author":"","cFrom":"壹码","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"青岛市市北区将发挥区块链资源整合优势，依托\u201c青岛量子保密通信城域网\u201d和青岛链湾研究院的区块链业务平台","tMain":"<p><span style=\"font-family:helvetica neue,helvetica,arial,microsoft yahei,微软雅黑,pingfang sc,hiragino sans gb,stheiti,simsun,sans-serif; font-size:14px\">据半岛都市报消息，作为今年亮点，青岛市市北区将发挥区块链资源整合优势，依托&ldquo;青岛量子保密通信城域网&rdquo;和青岛链湾研究院的区块链业务平台，建设&ldquo;青岛量子安全区块链创新业务示范","processDate":"2018-06-24 20:24:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"/uploads/bihucj/files/1529896024700.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-06-24 20:25:33","addTimeStr":"2018-06-24 20:25","updateTime":"2018-07-31 01:18:59","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617280208901.html"}]}
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
             * rows : [{"id":"9","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":5,"briefIntro":"","tMain":"","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 15:22:56","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-9.html"},{"id":"10","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":1,"briefIntro":"","tMain":"","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 15:22:57","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-10.html"},{"id":"11","appId":"470898763183944521","tTitle":"人民币收盘由升转跌美指反弹提振客盘购汇意愿","author":"","cFrom":"壹码","tLjdz":"https://m.baidu.com/?from=1012852s","tOrder":0,"tDjcs":1,"briefIntro":"","tMain":"\r\n\t\t<p style=\"text-align: justify;\">另外，路印在技术上也不断更新换代，最值得一提的就是路印的中继网络技术。中继是路印协议生态非常重要的一部分，它将各个交易者订单进行撮合配对，然后将配对成功的订单广播到区块链中。交易要流畅，很大一部分就是靠路印的中继网络，路印团队也在上面付出了相当大的功夫。目前，路印基金会与UpBlockchain共同出资研发了第二代中继系统，被称为光锥中继（中继2.0）。<\/p>","processDate":"2018-06-25 00:00:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-07-27 16:26:32","addTimeStr":"2018-07-27 16:26","updateTime":"2018-07-30 23:24:23","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-11.html"},{"id":"473656617280208901","appId":"470898763183944521","tTitle":"青岛市将建设\u201c青岛量子安全区块链创新业务示范网\u201d","author":"","cFrom":"壹码","tLjdz":"","tOrder":10000,"tDjcs":0,"briefIntro":"青岛市市北区将发挥区块链资源整合优势，依托\u201c青岛量子保密通信城域网\u201d和青岛链湾研究院的区块链业务平台","tMain":"<p><span style=\"font-family:helvetica neue,helvetica,arial,microsoft yahei,微软雅黑,pingfang sc,hiragino sans gb,stheiti,simsun,sans-serif; font-size:14px\">据半岛都市报消息，作为今年亮点，青岛市市北区将发挥区块链资源整合优势，依托&ldquo;青岛量子保密通信城域网&rdquo;和青岛链湾研究院的区块链业务平台，建设&ldquo;青岛量子安全区块链创新业务示范","processDate":"2018-06-24 20:24:00","dirtreeId":"470898763183944532","keywords":"","imgUrl":"/uploads/bihucj/files/1529896024700.jpg","ctrl0":"","ctrl1":"","ctrl2":"","ctrl3":"","delFlag":0,"addTime":"2018-06-24 20:25:33","addTimeStr":"2018-06-24 20:25","updateTime":"2018-07-31 01:18:59","time":"","dateFormat":"","typeName":"快讯","detailH5":"http://otenda.project.njagan.org/m/article-473656617280208901.html"}]
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
                 * id : 9
                 * appId : 470898763183944521
                 * tTitle : 人民币收盘由升转跌美指反弹提振客盘购汇意愿
                 * author :
                 * cFrom : 壹码
                 * tLjdz : https://m.baidu.com/?from=1012852s
                 * tOrder : 0
                 * tDjcs : 5
                 * briefIntro :
                 * tMain :
                 * processDate : 2018-06-25 00:00:00
                 * dirtreeId : 470898763183944532
                 * keywords :
                 * imgUrl : http://otenda.project.njagan.org/oss/otenda/20180729/f5847bf6fdf225f91afe29156c93fa28.png
                 * ctrl0 :
                 * ctrl1 :
                 * ctrl2 :
                 * ctrl3 :
                 * delFlag : 0
                 * addTime : 2018-07-27 16:26:32
                 * addTimeStr : 2018-07-27 16:26
                 * updateTime : 2018-07-30 15:22:56
                 * time :
                 * dateFormat :
                 * typeName : 快讯
                 * detailH5 : http://otenda.project.njagan.org/m/article-9.html
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

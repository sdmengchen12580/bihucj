package com.aganyun.acode.http.suger.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/30.
 */

public class GetPearlsBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"count":3,"rows":[{"id":"487363497684934656","appId":"624766110637531136","userId":"485438545255768064","aganPearl":1,"pearlType":"","remark":"阅读","delFlag":0,"addTime":"2018-09-06 20:48:32","updateTime":"2018-09-06 20:48:32"},{"id":"487363463773986816","appId":"624766110637531136","userId":"485438545255768064","aganPearl":5,"pearlType":"","remark":"分享文章","delFlag":0,"addTime":"2018-09-06 20:48:24","updateTime":"2018-09-06 20:48:24"},{"id":"487289266364260352","appId":"624766110637531136","userId":"485438545255768064","aganPearl":30,"pearlType":"","remark":"签到","delFlag":0,"addTime":"2018-09-06 15:53:34","updateTime":"2018-09-06 15:53:34"}]},"time":"1536239610381"}
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
         * response : {"count":3,"rows":[{"id":"487363497684934656","appId":"624766110637531136","userId":"485438545255768064","aganPearl":1,"pearlType":"","remark":"阅读","delFlag":0,"addTime":"2018-09-06 20:48:32","updateTime":"2018-09-06 20:48:32"},{"id":"487363463773986816","appId":"624766110637531136","userId":"485438545255768064","aganPearl":5,"pearlType":"","remark":"分享文章","delFlag":0,"addTime":"2018-09-06 20:48:24","updateTime":"2018-09-06 20:48:24"},{"id":"487289266364260352","appId":"624766110637531136","userId":"485438545255768064","aganPearl":30,"pearlType":"","remark":"签到","delFlag":0,"addTime":"2018-09-06 15:53:34","updateTime":"2018-09-06 15:53:34"}]}
         * time : 1536239610381
         */

        private ResponseBean response;
        private String time;

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public static class ResponseBean {
            /**
             * count : 3
             * rows : [{"id":"487363497684934656","appId":"624766110637531136","userId":"485438545255768064","aganPearl":1,"pearlType":"","remark":"阅读","delFlag":0,"addTime":"2018-09-06 20:48:32","updateTime":"2018-09-06 20:48:32"},{"id":"487363463773986816","appId":"624766110637531136","userId":"485438545255768064","aganPearl":5,"pearlType":"","remark":"分享文章","delFlag":0,"addTime":"2018-09-06 20:48:24","updateTime":"2018-09-06 20:48:24"},{"id":"487289266364260352","appId":"624766110637531136","userId":"485438545255768064","aganPearl":30,"pearlType":"","remark":"签到","delFlag":0,"addTime":"2018-09-06 15:53:34","updateTime":"2018-09-06 15:53:34"}]
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
                 * id : 487363497684934656
                 * appId : 624766110637531136
                 * userId : 485438545255768064
                 * aganPearl : 1.0
                 * pearlType :
                 * remark : 阅读
                 * delFlag : 0
                 * addTime : 2018-09-06 20:48:32
                 * updateTime : 2018-09-06 20:48:32
                 */

                private String id;
                private String appId;
                private String userId;
                private double aganPearl;
                private String pearlType;
                private String remark;
                private int delFlag;
                private String addTime;
                private String updateTime;

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

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public double getAganPearl() {
                    return aganPearl;
                }

                public void setAganPearl(double aganPearl) {
                    this.aganPearl = aganPearl;
                }

                public String getPearlType() {
                    return pearlType;
                }

                public void setPearlType(String pearlType) {
                    this.pearlType = pearlType;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
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

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
            }
        }
    }
}

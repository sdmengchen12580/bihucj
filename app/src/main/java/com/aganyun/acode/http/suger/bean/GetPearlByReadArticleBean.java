package com.aganyun.acode.http.suger.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/30.
 */

public class GetPearlByReadArticleBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"readArticle":"1","pearl":"2","readBool":"true"},"time":"1535637893046"}
     */

    public static class MessagesBean{
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

    private int code;
    private DataBean data;
    private List<MessagesBean> messages;

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
         * response : {"readArticle":"1","pearl":"2","readBool":"true"}
         * time : 1535637893046
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
             * readArticle : 1
             * pearl : 2
             * readBool : true
             */

            private String readArticle;
            private String pearl;
            private String readBool;

            public String getReadArticle() {
                return readArticle;
            }

            public void setReadArticle(String readArticle) {
                this.readArticle = readArticle;
            }

            public String getPearl() {
                return pearl;
            }

            public void setPearl(String pearl) {
                this.pearl = pearl;
            }

            public String getReadBool() {
                return readBool;
            }

            public void setReadBool(String readBool) {
                this.readBool = readBool;
            }
        }
    }
}

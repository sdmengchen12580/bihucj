package com.aganyun.acode.http.suger.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/30.
 */

public class GetPearlByShareFlashBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"shareFlash":"分享次数达到上限","pearl":"0","flashBool":"false"},"time":"1535635211188"}
     */

    private int code;
    private DataBean data;
    private List<MessagesBean> messages;

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
         * response : {"shareFlash":"分享次数达到上限","pearl":"0","flashBool":"false"}
         * time : 1535635211188
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
             * shareFlash : 分享次数达到上限
             * pearl : 0
             * flashBool : false
             */

            private String shareFlash;
            private String pearl;
            private String flashBool;

            public String getShareFlash() {
                return shareFlash;
            }

            public void setShareFlash(String shareFlash) {
                this.shareFlash = shareFlash;
            }

            public String getPearl() {
                return pearl;
            }

            public void setPearl(String pearl) {
                this.pearl = pearl;
            }

            public String getFlashBool() {
                return flashBool;
            }

            public void setFlashBool(String flashBool) {
                this.flashBool = flashBool;
            }
        }
    }
}

package com.aganyun.acode.http.person.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class LogoutBean {
    /**
     * code : 200
     * messages : []
     * data : {"response":{"flag":true},"time":1526980941006}
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
         * response : {"flag":true}
         * time : 1526980941006
         */

        private ResponseBean response;
        private long time;

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public static class ResponseBean {
            /**
             * flag : true
             */

            private boolean flag;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }
        }
    }
}

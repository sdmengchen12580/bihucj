package com.aganyun.acode.http.suger.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/29.
 */

public class GetSignedInNumBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"sginNum":0,"todaySignIn":false,"lastSignDate":""},"time":"1535535669003"}
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
         * response : {"sginNum":0,"todaySignIn":false,"lastSignDate":""}
         * time : 1535535669003
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
             * sginNum : 0
             * todaySignIn : false
             * lastSignDate :
             */

            private int sginNum;
            private boolean todaySignIn;
            private String lastSignDate;

            public int getSginNum() {
                return sginNum;
            }

            public void setSginNum(int sginNum) {
                this.sginNum = sginNum;
            }

            public boolean isTodaySignIn() {
                return todaySignIn;
            }

            public void setTodaySignIn(boolean todaySignIn) {
                this.todaySignIn = todaySignIn;
            }

            public String getLastSignDate() {
                return lastSignDate;
            }

            public void setLastSignDate(String lastSignDate) {
                this.lastSignDate = lastSignDate;
            }
        }
    }
}

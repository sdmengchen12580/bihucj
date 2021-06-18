package com.aganyun.acode.http.suger.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/29.
 */

public class GetSignInBaseBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":[{"pearlNum":"5","day":"1"},{"pearlNum":"10","day":"2"},{"pearlNum":"15","day":"3"},{"pearlNum":"30","day":"4"},{"pearlNum":"35","day":"5"},{"pearlNum":"40","day":"6"},{"pearlNum":"188","day":"7"}],"time":"1535534061277"}
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
         * response : [{"pearlNum":"5","day":"1"},{"pearlNum":"10","day":"2"},{"pearlNum":"15","day":"3"},{"pearlNum":"30","day":"4"},{"pearlNum":"35","day":"5"},{"pearlNum":"40","day":"6"},{"pearlNum":"188","day":"7"}]
         * time : 1535534061277
         */

        private String time;
        private List<ResponseBean> response;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<ResponseBean> getResponse() {
            return response;
        }

        public void setResponse(List<ResponseBean> response) {
            this.response = response;
        }

        public static class ResponseBean {
            /**
             * pearlNum : 5
             * day : 1
             */

            private String pearlNum;
            private String day;

            public String getPearlNum() {
                return pearlNum;
            }

            public void setPearlNum(String pearlNum) {
                this.pearlNum = pearlNum;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }
        }
    }
}

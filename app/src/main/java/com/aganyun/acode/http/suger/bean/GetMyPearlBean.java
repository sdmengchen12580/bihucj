package com.aganyun.acode.http.suger.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/30.
 */

public class GetMyPearlBean {

    /**
     * code : 200
     * messages : []
     * data : {"response":{"total":"603","todayTotal":"603"},"time":"1535654918491"}
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
         * response : {"total":"603","todayTotal":"603"}
         * time : 1535654918491
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
             * total : 603
             * todayTotal : 603
             */

            private String total;
            private String todayTotal;

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getTodayTotal() {
                return todayTotal;
            }

            public void setTodayTotal(String todayTotal) {
                this.todayTotal = todayTotal;
            }
        }
    }
}

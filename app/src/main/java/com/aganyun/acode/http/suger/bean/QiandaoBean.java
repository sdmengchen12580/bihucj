package com.aganyun.acode.http.suger.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/29.
 */

public class QiandaoBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"flag":true,"signIn":false},"time":"1535593237092"}
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
         * response : {"flag":true,"signIn":false}
         * time : 1535593237092
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
             * flag : true
             * signIn : false
             */

            private boolean flag;
            private boolean signIn;
            private String pearlNum;

            public String getPearl() {
                return pearlNum;
            }

            public void setPearl(String pearlNum) {
                this.pearlNum = pearlNum;
            }

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public boolean isSignIn() {
                return signIn;
            }

            public void setSignIn(boolean signIn) {
                this.signIn = signIn;
            }
        }
    }
}

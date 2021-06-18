package com.aganyun.acode.http.other.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/31.
 */

public class InvateUserBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"flag":true,"invateUrl":"http://otenda.project.njagan.org/m/invite-userxxxxxxxxxxx.html?user=D620ouETNPzX29IOkgGOkyo1y+g5we3u&referralCode=256782"},"time":"1535681699173"}
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
         * response : {"flag":true,"invateUrl":"http://otenda.project.njagan.org/m/invite-userxxxxxxxxxxx.html?user=D620ouETNPzX29IOkgGOkyo1y+g5we3u&referralCode=256782"}
         * time : 1535681699173
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
             * invateUrl : http://otenda.project.njagan.org/m/invite-userxxxxxxxxxxx.html?user=D620ouETNPzX29IOkgGOkyo1y+g5we3u&referralCode=256782
             */

            private boolean flag;
            private String invateUrl;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getInvateUrl() {
                return invateUrl;
            }

            public void setInvateUrl(String invateUrl) {
                this.invateUrl = invateUrl;
            }
        }
    }
}

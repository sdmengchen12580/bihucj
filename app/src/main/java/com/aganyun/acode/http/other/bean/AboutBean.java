package com.aganyun.acode.http.other.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class AboutBean {

    /**
     * code : 200
     * messages : []
     * data : {"response":{"weIconUrl":"http://www-dev.lcotenda.com/oss/otenda/20180729/70d171cf7998f237018b8ce1ae823a3d.png","verson":"币虎 v1.0","busineCooperate":"123456@qq.com","feedback":"123456@qq.com","contribute":"123456@qq.com","suppertWeChat":"123456@qq.com"},"time":"1533113525957"}
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
         * response : {"weIconUrl":"http://www-dev.lcotenda.com/oss/otenda/20180729/70d171cf7998f237018b8ce1ae823a3d.png","verson":"币虎 v1.0","busineCooperate":"123456@qq.com","feedback":"123456@qq.com","contribute":"123456@qq.com","suppertWeChat":"123456@qq.com"}
         * time : 1533113525957
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
             * weIconUrl :
             *
             * verson : 币虎 v1.0
             * busineCooperate : 123456@qq.com
             * feedback : 123456@qq.com
             * contribute : 123456@qq.com
             * suppertWeChat : 123456@qq.com
             */

            private String weIconUrl;
            private String verson;
            private String busineCooperate;
            private String feedback;
            private String contribute;
            private String suppertWeChat;

            public String getWeIconUrl() {
                return weIconUrl;
            }

            public void setWeIconUrl(String weIconUrl) {
                this.weIconUrl = weIconUrl;
            }

            public String getVerson() {
                return verson;
            }

            public void setVerson(String verson) {
                this.verson = verson;
            }

            public String getBusineCooperate() {
                return busineCooperate;
            }

            public void setBusineCooperate(String busineCooperate) {
                this.busineCooperate = busineCooperate;
            }

            public String getFeedback() {
                return feedback;
            }

            public void setFeedback(String feedback) {
                this.feedback = feedback;
            }

            public String getContribute() {
                return contribute;
            }

            public void setContribute(String contribute) {
                this.contribute = contribute;
            }

            public String getSuppertWeChat() {
                return suppertWeChat;
            }

            public void setSuppertWeChat(String suppertWeChat) {
                this.suppertWeChat = suppertWeChat;
            }
        }
    }
}

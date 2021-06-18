package com.aganyun.acode.http.other.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/1.
 */

public class GetShareUrlBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"flag":true,"shareUrl":"http://www.baidu.com?user=Y5LH8fYAzAZ9aTzjSKFkunG/8SR8nqvi",
     * "qrCode":"http://localhost:8080/fz.otenda/api/comm/code/qrCode?code=http://www.baidu.com?user=Y5LH8fYAzAZ9aTzjSKFkunG/8SR8nqvi"},"time":"1533117355077"}
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
         * response : {"flag":true,"shareUrl":"http://www.baidu.com?user=Y5LH8fYAzAZ9aTzjSKFkunG/8SR8nqvi","qrCode":"http://localhost:8080/fz.otenda/api/comm/code/qrCode?code=http://www.baidu.com?user=Y5LH8fYAzAZ9aTzjSKFkunG/8SR8nqvi"}
         * time : 1533117355077
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
             * shareUrl : http://www.baidu.com?user=Y5LH8fYAzAZ9aTzjSKFkunG/8SR8nqvi
             * qrCode : http://localhost:8080/fz.otenda/api/comm/code/qrCode?code=http://www.baidu.com?user=Y5LH8fYAzAZ9aTzjSKFkunG/8SR8nqvi
             */

            private boolean flag;
            private String shareUrl;
            private String qrCode;
            private String invateCode;

            public String getInvateCode() {
                return invateCode;
            }

            public void setInvateCode(String invateCode) {
                this.invateCode = invateCode;
            }

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }
        }
    }
}

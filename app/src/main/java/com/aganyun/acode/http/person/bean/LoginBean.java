package com.aganyun.acode.http.person.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class LoginBean {
    /**
     * code : 200
     * messages : []
     * data : {"response":{"flag":true,
     * "sessionKey":"d84736684c9a59daadebd71b5b3fd7ff","
     * nickName":"15995712345",
     * "hasShop":false,
     * "avatar":"",
     * "imId":"0","token":""},"time":1531288199818}
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
         * response : {"flag":true,
         * "sessionKey":"d84736684c9a59daadebd71b5b3fd7ff",
         * "nickName":"15995712345",
         * "hasShop":false,
         * "avatar":"",
         * "imId":"0",
         * "token":""
         * }
         * time : 1531288199818
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
             * sessionKey : d84736684c9a59daadebd71b5b3fd7ff
             * nickName : 15995712345
             * hasShop : false
             * avatar :
             * imId : 0
             * token :
             */

            private boolean flag;
            private String sessionKey;
            private String nickName;
            private String avatar;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getSessionKey() {
                return sessionKey;
            }

            public void setSessionKey(String sessionKey) {
                this.sessionKey = sessionKey;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}

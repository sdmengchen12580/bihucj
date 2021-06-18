package com.aganyun.acode.http.details.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/9/13.
 */

public class InitBean {

    /**
     * code : 200
     * messages : []
     * data : {"response":["BTC","ETH","BCD","ELF","INK","TRX","AIDOC","PST","DDD","EKO","PRA","UC","TTT","BCH","BCX","ETC","LTC","SBTC","QTUM","NEO","NEM","HSR","XRP","DASH","XMR","EOS","OMG","IOTA","ZEC","WAVES","BTG","BTS","XLM","LSK","TNB","PNT","IHT","DRCT","ADA","VSC","FT","TAC","ORS","LST"],"time":"1536803094085"}
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
         * response : ["BTC","ETH","BCD","ELF","INK","TRX","AIDOC","PST","DDD","EKO","PRA","UC","TTT","BCH","BCX","ETC","LTC","SBTC","QTUM","NEO","NEM","HSR","XRP","DASH","XMR","EOS","OMG","IOTA","ZEC","WAVES","BTG","BTS","XLM","LSK","TNB","PNT","IHT","DRCT","ADA","VSC","FT","TAC","ORS","LST"]
         * time : 1536803094085
         */

        private String time;
        private List<String> response;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<String> getResponse() {
            return response;
        }

        public void setResponse(List<String> response) {
            this.response = response;
        }
    }
}

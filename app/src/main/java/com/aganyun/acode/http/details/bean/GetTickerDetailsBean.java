package com.aganyun.acode.http.details.bean;

import java.util.List;

/**
 * Created by 孟晨 on 2018/9/13.
 */

public class GetTickerDetailsBean {


    /**
     * code : 200
     * messages : []
     * data : {"response":{"count":230,"rows":[{"id":"488035723459432448","appId":"624766110637531136","symbol":"BTCUSDT","dateTime":"1536398376000","quantityRatio":"1.41926295","exchangeName":"ZB网","exchangeImgUrl":"","turnoverRate":"0.00054427","currency":"USDT","base":"BTC","close":"44117.000159000000","closeArrow":"0","open":"43608.346647000000","commissionRatio":"0.05490118","vol":"9378.90970000","ticker":"ZB:BTCUSDT","degree":"1.16641300","value":"412787863.014037731300","changValue":"508.653424395520","high":"44364.345933000000","low":"43323.153000000000","delFlag":0,"addTime":"2018-09-08 17:19:43","updateTime":"2018-09-08 17:19:43"},{"id":"489732118991470592","appId":"624766110637531136","symbol":"BTCUSD","dateTime":"1536802756000","quantityRatio":"0.01299289","exchangeName":"YoBit","exchangeImgUrl":"","turnoverRate":"0.00000109","currency":"USD","base":"BTC","close":"45001.807040000000","closeArrow":"0","open":"45007.990400000000","commissionRatio":"0.13528835","vol":"30.45050928","ticker":"YOBIT:BTCUSD","degree":"-0.01373800","value":"1377960.375224236352","changValue":"-6.183197721152","high":"45688.160000000000","low":"44972.943996923616","delFlag":0,"addTime":"2018-09-13 10:40:35","updateTime":"2018-09-13 10:40:35"},{"id":"489732095801163776","appId":"624766110637531136","symbol":"BTCUSDT","dateTime":"1536802790000","quantityRatio":"1.02709973","exchangeName":"YEX","exchangeImgUrl":"","turnoverRate":"0.00002728","currency":"USDT","base":"BTC","close":"43936.345408000000","closeArrow":"-1","open":"43101.110880000000","commissionRatio":"0.00892857","vol":"468.39992000","ticker":"YEX:BTCUSDT","degree":"1.93784900","value":"20228887.171565847040","changValue":"835.234446173536","high":"44193.985408000000","low":"42635.503872000000","delFlag":0,"addTime":"2018-09-13 10:40:30","updateTime":"2018-09-13 10:40:30"},{"id":"488035723211968512","appId":"624766110637531136","symbol":"BTCQC","dateTime":"1536398370000","quantityRatio":"0.83461672","exchangeName":"ZB网","exchangeImgUrl":"","turnoverRate":"0.00052107","currency":"QC","base":"BTC","close":"45145.599999946719","closeArrow":"0","open":"44620.879999949536","commissionRatio":"0.48646967","vol":"8843.41040000","ticker":"ZB:BTCQC","degree":"1.17595200","value":"397270024.037283981179","changValue":"524.720130719493","high":"45319.999999988304","low":"44304.139999960072","delFlag":0,"addTime":"2018-09-08 17:19:43","updateTime":"2018-09-08 17:19:43"},{"id":"489732204022595584","appId":"624766110637531136","symbol":"BTCJPY","dateTime":"1536802834000","quantityRatio":"0.90887529","exchangeName":"Zaif","exchangeImgUrl":"","turnoverRate":"0.00040857","currency":"JPY","base":"BTC","close":"43869.881390550816","closeArrow":"-1","open":"43033.974096768672","commissionRatio":"0.92492269","vol":"7052.27140000","ticker":"ZAIF:BTCJPY","degree":"1.94243600","value":"304355275.847503706944","changValue":"835.907405082624","high":"44031.023858176672","low":"42654.996591628608","delFlag":0,"addTime":"2018-09-13 10:40:56","updateTime":"2018-09-13 10:40:56"},{"id":"489732085084717056","appId":"624766110637531136","symbol":"BTCUDC","dateTime":"1536802740000","quantityRatio":"10.20601740","exchangeName":"WinMax","exchangeImgUrl":"","turnoverRate":"0.00041188","currency":"UDC","base":"BTC","close":"43273.011549653696","closeArrow":"-1","open":"42619.077584338432","commissionRatio":"-0.40419250","vol":"7063.91470000","ticker":"WINMAX:BTCUDC","degree":"1.53436900","value":"301500901.213130969696","changValue":"653.933914543008","high":"43540.009104119552","low":"42234.840255885888","delFlag":0,"addTime":"2018-09-13 10:40:27","updateTime":"2018-09-13 10:40:27"}]},"time":"1536803845074"}
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
         * response : {"count":230,"rows":[{"id":"488035723459432448","appId":"624766110637531136","symbol":"BTCUSDT","dateTime":"1536398376000","quantityRatio":"1.41926295","exchangeName":"ZB网","exchangeImgUrl":"","turnoverRate":"0.00054427","currency":"USDT","base":"BTC","close":"44117.000159000000","closeArrow":"0","open":"43608.346647000000","commissionRatio":"0.05490118","vol":"9378.90970000","ticker":"ZB:BTCUSDT","degree":"1.16641300","value":"412787863.014037731300","changValue":"508.653424395520","high":"44364.345933000000","low":"43323.153000000000","delFlag":0,"addTime":"2018-09-08 17:19:43","updateTime":"2018-09-08 17:19:43"},{"id":"489732118991470592","appId":"624766110637531136","symbol":"BTCUSD","dateTime":"1536802756000","quantityRatio":"0.01299289","exchangeName":"YoBit","exchangeImgUrl":"","turnoverRate":"0.00000109","currency":"USD","base":"BTC","close":"45001.807040000000","closeArrow":"0","open":"45007.990400000000","commissionRatio":"0.13528835","vol":"30.45050928","ticker":"YOBIT:BTCUSD","degree":"-0.01373800","value":"1377960.375224236352","changValue":"-6.183197721152","high":"45688.160000000000","low":"44972.943996923616","delFlag":0,"addTime":"2018-09-13 10:40:35","updateTime":"2018-09-13 10:40:35"},{"id":"489732095801163776","appId":"624766110637531136","symbol":"BTCUSDT","dateTime":"1536802790000","quantityRatio":"1.02709973","exchangeName":"YEX","exchangeImgUrl":"","turnoverRate":"0.00002728","currency":"USDT","base":"BTC","close":"43936.345408000000","closeArrow":"-1","open":"43101.110880000000","commissionRatio":"0.00892857","vol":"468.39992000","ticker":"YEX:BTCUSDT","degree":"1.93784900","value":"20228887.171565847040","changValue":"835.234446173536","high":"44193.985408000000","low":"42635.503872000000","delFlag":0,"addTime":"2018-09-13 10:40:30","updateTime":"2018-09-13 10:40:30"},{"id":"488035723211968512","appId":"624766110637531136","symbol":"BTCQC","dateTime":"1536398370000","quantityRatio":"0.83461672","exchangeName":"ZB网","exchangeImgUrl":"","turnoverRate":"0.00052107","currency":"QC","base":"BTC","close":"45145.599999946719","closeArrow":"0","open":"44620.879999949536","commissionRatio":"0.48646967","vol":"8843.41040000","ticker":"ZB:BTCQC","degree":"1.17595200","value":"397270024.037283981179","changValue":"524.720130719493","high":"45319.999999988304","low":"44304.139999960072","delFlag":0,"addTime":"2018-09-08 17:19:43","updateTime":"2018-09-08 17:19:43"},{"id":"489732204022595584","appId":"624766110637531136","symbol":"BTCJPY","dateTime":"1536802834000","quantityRatio":"0.90887529","exchangeName":"Zaif","exchangeImgUrl":"","turnoverRate":"0.00040857","currency":"JPY","base":"BTC","close":"43869.881390550816","closeArrow":"-1","open":"43033.974096768672","commissionRatio":"0.92492269","vol":"7052.27140000","ticker":"ZAIF:BTCJPY","degree":"1.94243600","value":"304355275.847503706944","changValue":"835.907405082624","high":"44031.023858176672","low":"42654.996591628608","delFlag":0,"addTime":"2018-09-13 10:40:56","updateTime":"2018-09-13 10:40:56"},{"id":"489732085084717056","appId":"624766110637531136","symbol":"BTCUDC","dateTime":"1536802740000","quantityRatio":"10.20601740","exchangeName":"WinMax","exchangeImgUrl":"","turnoverRate":"0.00041188","currency":"UDC","base":"BTC","close":"43273.011549653696","closeArrow":"-1","open":"42619.077584338432","commissionRatio":"-0.40419250","vol":"7063.91470000","ticker":"WINMAX:BTCUDC","degree":"1.53436900","value":"301500901.213130969696","changValue":"653.933914543008","high":"43540.009104119552","low":"42234.840255885888","delFlag":0,"addTime":"2018-09-13 10:40:27","updateTime":"2018-09-13 10:40:27"}]}
         * time : 1536803845074
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
             * count : 230
             * rows : [{"id":"488035723459432448","appId":"624766110637531136","symbol":"BTCUSDT","dateTime":"1536398376000","quantityRatio":"1.41926295","exchangeName":"ZB网","exchangeImgUrl":"","turnoverRate":"0.00054427","currency":"USDT","base":"BTC","close":"44117.000159000000","closeArrow":"0","open":"43608.346647000000","commissionRatio":"0.05490118","vol":"9378.90970000","ticker":"ZB:BTCUSDT","degree":"1.16641300","value":"412787863.014037731300","changValue":"508.653424395520","high":"44364.345933000000","low":"43323.153000000000","delFlag":0,"addTime":"2018-09-08 17:19:43","updateTime":"2018-09-08 17:19:43"},{"id":"489732118991470592","appId":"624766110637531136","symbol":"BTCUSD","dateTime":"1536802756000","quantityRatio":"0.01299289","exchangeName":"YoBit","exchangeImgUrl":"","turnoverRate":"0.00000109","currency":"USD","base":"BTC","close":"45001.807040000000","closeArrow":"0","open":"45007.990400000000","commissionRatio":"0.13528835","vol":"30.45050928","ticker":"YOBIT:BTCUSD","degree":"-0.01373800","value":"1377960.375224236352","changValue":"-6.183197721152","high":"45688.160000000000","low":"44972.943996923616","delFlag":0,"addTime":"2018-09-13 10:40:35","updateTime":"2018-09-13 10:40:35"},{"id":"489732095801163776","appId":"624766110637531136","symbol":"BTCUSDT","dateTime":"1536802790000","quantityRatio":"1.02709973","exchangeName":"YEX","exchangeImgUrl":"","turnoverRate":"0.00002728","currency":"USDT","base":"BTC","close":"43936.345408000000","closeArrow":"-1","open":"43101.110880000000","commissionRatio":"0.00892857","vol":"468.39992000","ticker":"YEX:BTCUSDT","degree":"1.93784900","value":"20228887.171565847040","changValue":"835.234446173536","high":"44193.985408000000","low":"42635.503872000000","delFlag":0,"addTime":"2018-09-13 10:40:30","updateTime":"2018-09-13 10:40:30"},{"id":"488035723211968512","appId":"624766110637531136","symbol":"BTCQC","dateTime":"1536398370000","quantityRatio":"0.83461672","exchangeName":"ZB网","exchangeImgUrl":"","turnoverRate":"0.00052107","currency":"QC","base":"BTC","close":"45145.599999946719","closeArrow":"0","open":"44620.879999949536","commissionRatio":"0.48646967","vol":"8843.41040000","ticker":"ZB:BTCQC","degree":"1.17595200","value":"397270024.037283981179","changValue":"524.720130719493","high":"45319.999999988304","low":"44304.139999960072","delFlag":0,"addTime":"2018-09-08 17:19:43","updateTime":"2018-09-08 17:19:43"},{"id":"489732204022595584","appId":"624766110637531136","symbol":"BTCJPY","dateTime":"1536802834000","quantityRatio":"0.90887529","exchangeName":"Zaif","exchangeImgUrl":"","turnoverRate":"0.00040857","currency":"JPY","base":"BTC","close":"43869.881390550816","closeArrow":"-1","open":"43033.974096768672","commissionRatio":"0.92492269","vol":"7052.27140000","ticker":"ZAIF:BTCJPY","degree":"1.94243600","value":"304355275.847503706944","changValue":"835.907405082624","high":"44031.023858176672","low":"42654.996591628608","delFlag":0,"addTime":"2018-09-13 10:40:56","updateTime":"2018-09-13 10:40:56"},{"id":"489732085084717056","appId":"624766110637531136","symbol":"BTCUDC","dateTime":"1536802740000","quantityRatio":"10.20601740","exchangeName":"WinMax","exchangeImgUrl":"","turnoverRate":"0.00041188","currency":"UDC","base":"BTC","close":"43273.011549653696","closeArrow":"-1","open":"42619.077584338432","commissionRatio":"-0.40419250","vol":"7063.91470000","ticker":"WINMAX:BTCUDC","degree":"1.53436900","value":"301500901.213130969696","changValue":"653.933914543008","high":"43540.009104119552","low":"42234.840255885888","delFlag":0,"addTime":"2018-09-13 10:40:27","updateTime":"2018-09-13 10:40:27"}]
             */

            private int count;
            private List<RowsBean> rows;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<RowsBean> getRows() {
                return rows;
            }

            public void setRows(List<RowsBean> rows) {
                this.rows = rows;
            }

            public static class RowsBean {
                /**
                 * id : 488035723459432448
                 * appId : 624766110637531136
                 * symbol : BTCUSDT
                 * dateTime : 1536398376000
                 * quantityRatio : 1.41926295
                 * exchangeName : ZB网
                 * exchangeImgUrl :
                 * turnoverRate : 0.00054427
                 * currency : USDT
                 * base : BTC
                 * close : 44117.000159000000
                 * closeArrow : 0
                 * open : 43608.346647000000
                 * commissionRatio : 0.05490118
                 * vol : 9378.90970000
                 * ticker : ZB:BTCUSDT
                 * degree : 1.16641300
                 * value : 412787863.014037731300
                 * changValue : 508.653424395520
                 * high : 44364.345933000000
                 * low : 43323.153000000000
                 * delFlag : 0
                 * addTime : 2018-09-08 17:19:43
                 * updateTime : 2018-09-08 17:19:43
                 */

                private String id;
                private String appId;
                private String symbol;
                private String dateTime;
                private String quantityRatio;
                private String exchangeName;
                private String exchangeImgUrl;
                private String turnoverRate;
                private String currency;
                private String base;
                private String close;
                private String closeArrow;
                private String open;
                private String commissionRatio;
                private String vol;
                private String ticker;
                private String degree;
                private String value;
                private String changValue;
                private String high;
                private String low;
                private int delFlag;
                private String addTime;
                private String updateTime;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getAppId() {
                    return appId;
                }

                public void setAppId(String appId) {
                    this.appId = appId;
                }

                public String getSymbol() {
                    return symbol;
                }

                public void setSymbol(String symbol) {
                    this.symbol = symbol;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public String getQuantityRatio() {
                    return quantityRatio;
                }

                public void setQuantityRatio(String quantityRatio) {
                    this.quantityRatio = quantityRatio;
                }

                public String getExchangeName() {
                    return exchangeName;
                }

                public void setExchangeName(String exchangeName) {
                    this.exchangeName = exchangeName;
                }

                public String getExchangeImgUrl() {
                    return exchangeImgUrl;
                }

                public void setExchangeImgUrl(String exchangeImgUrl) {
                    this.exchangeImgUrl = exchangeImgUrl;
                }

                public String getTurnoverRate() {
                    return turnoverRate;
                }

                public void setTurnoverRate(String turnoverRate) {
                    this.turnoverRate = turnoverRate;
                }

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }

                public String getBase() {
                    return base;
                }

                public void setBase(String base) {
                    this.base = base;
                }

                public String getClose() {
                    return close;
                }

                public void setClose(String close) {
                    this.close = close;
                }

                public String getCloseArrow() {
                    return closeArrow;
                }

                public void setCloseArrow(String closeArrow) {
                    this.closeArrow = closeArrow;
                }

                public String getOpen() {
                    return open;
                }

                public void setOpen(String open) {
                    this.open = open;
                }

                public String getCommissionRatio() {
                    return commissionRatio;
                }

                public void setCommissionRatio(String commissionRatio) {
                    this.commissionRatio = commissionRatio;
                }

                public String getVol() {
                    return vol;
                }

                public void setVol(String vol) {
                    this.vol = vol;
                }

                public String getTicker() {
                    return ticker;
                }

                public void setTicker(String ticker) {
                    this.ticker = ticker;
                }

                public String getDegree() {
                    return degree;
                }

                public void setDegree(String degree) {
                    this.degree = degree;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getChangValue() {
                    return changValue;
                }

                public void setChangValue(String changValue) {
                    this.changValue = changValue;
                }

                public String getHigh() {
                    return high;
                }

                public void setHigh(String high) {
                    this.high = high;
                }

                public String getLow() {
                    return low;
                }

                public void setLow(String low) {
                    this.low = low;
                }

                public int getDelFlag() {
                    return delFlag;
                }

                public void setDelFlag(int delFlag) {
                    this.delFlag = delFlag;
                }

                public String getAddTime() {
                    return addTime;
                }

                public void setAddTime(String addTime) {
                    this.addTime = addTime;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
            }
        }
    }
}

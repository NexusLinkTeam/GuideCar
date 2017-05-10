package com.nexuslink.guidecar.model.bean;

import java.util.List;

/**
 * 用于解析天气的javabean
 * Created by Rye on 2017/5/10.
 */

public class ForecastBean {

    private List<HeWeather5Bean> HeWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean {
        /**
         * basic : {"city":"重庆","cnty":"中国","id":"CN101040100","lat":"29.291965","lon":"108.170255","update":{"loc":"2017-05-10 19:52","utc":"2017-05-10 11:52"}}
         * daily_forecast : [{"astro":{"mr":"18:56","ms":"05:42","sr":"06:04","ss":"19:36"},"cond":{"code_d":"305","code_n":"307","txt_d":"小雨","txt_n":"大雨"},"date":"2017-05-10","hum":"81","pcpn":"12.2","pop":"100","pres":"1012","tmp":{"max":"24","min":"19"},"uv":"8","vis":"14","wind":{"deg":"147","dir":"无持续风向","sc":"微风","spd":"7"}},{"astro":{"mr":"19:49","ms":"06:18","sr":"06:03","ss":"19:36"},"cond":{"code_d":"300","code_n":"101","txt_d":"阵雨","txt_n":"多云"},"date":"2017-05-11","hum":"86","pcpn":"36.9","pop":"100","pres":"1013","tmp":{"max":"21","min":"17"},"uv":"8","vis":"13","wind":{"deg":"241","dir":"无持续风向","sc":"微风","spd":"5"}},{"astro":{"mr":"20:40","ms":"06:57","sr":"06:02","ss":"19:37"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-05-12","hum":"67","pcpn":"0.0","pop":"0","pres":"1013","tmp":{"max":"30","min":"20"},"uv":"10","vis":"20","wind":{"deg":"281","dir":"无持续风向","sc":"微风","spd":"7"}}]
         * status : ok
         */

        private BasicBean basic;
        private String status;
        private List<DailyForecastBean> daily_forecast;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public static class BasicBean {
            /**
             * city : 重庆
             * cnty : 中国
             * id : CN101040100
             * lat : 29.291965
             * lon : 108.170255
             * update : {"loc":"2017-05-10 19:52","utc":"2017-05-10 11:52"}
             */

            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private UpdateBean update;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public static class UpdateBean {
                /**
                 * loc : 2017-05-10 19:52
                 * utc : 2017-05-10 11:52
                 */

                private String loc;
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public static class DailyForecastBean {
            /**
             * astro : {"mr":"18:56","ms":"05:42","sr":"06:04","ss":"19:36"}
             * cond : {"code_d":"305","code_n":"307","txt_d":"小雨","txt_n":"大雨"}
             * date : 2017-05-10
             * hum : 81
             * pcpn : 12.2
             * pop : 100
             * pres : 1012
             * tmp : {"max":"24","min":"19"}
             * uv : 8
             * vis : 14
             * wind : {"deg":"147","dir":"无持续风向","sc":"微风","spd":"7"}
             */

            private AstroBean astro;
            private CondBean cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            private TmpBean tmp;
            private String uv;
            private String vis;
            private WindBean wind;

            public AstroBean getAstro() {
                return astro;
            }

            public void setAstro(AstroBean astro) {
                this.astro = astro;
            }

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public TmpBean getTmp() {
                return tmp;
            }

            public void setTmp(TmpBean tmp) {
                this.tmp = tmp;
            }

            public String getUv() {
                return uv;
            }

            public void setUv(String uv) {
                this.uv = uv;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class AstroBean {
                /**
                 * mr : 18:56
                 * ms : 05:42
                 * sr : 06:04
                 * ss : 19:36
                 */

                private String mr;
                private String ms;
                private String sr;
                private String ss;

                public String getMr() {
                    return mr;
                }

                public void setMr(String mr) {
                    this.mr = mr;
                }

                public String getMs() {
                    return ms;
                }

                public void setMs(String ms) {
                    this.ms = ms;
                }

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }
            }

            public static class CondBean {
                /**
                 * code_d : 305
                 * code_n : 307
                 * txt_d : 小雨
                 * txt_n : 大雨
                 */

                private String code_d;
                private String code_n;
                private String txt_d;
                private String txt_n;

                public String getCode_d() {
                    return code_d;
                }

                public void setCode_d(String code_d) {
                    this.code_d = code_d;
                }

                public String getCode_n() {
                    return code_n;
                }

                public void setCode_n(String code_n) {
                    this.code_n = code_n;
                }

                public String getTxt_d() {
                    return txt_d;
                }

                public void setTxt_d(String txt_d) {
                    this.txt_d = txt_d;
                }

                public String getTxt_n() {
                    return txt_n;
                }

                public void setTxt_n(String txt_n) {
                    this.txt_n = txt_n;
                }
            }

            public static class TmpBean {
                /**
                 * max : 24
                 * min : 19
                 */

                private String max;
                private String min;

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public static class WindBean {
                /**
                 * deg : 147
                 * dir : 无持续风向
                 * sc : 微风
                 * spd : 7
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }
    }
}

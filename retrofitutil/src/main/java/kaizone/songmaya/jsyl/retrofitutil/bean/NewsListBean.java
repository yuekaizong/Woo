package kaizone.songmaya.jsyl.retrofitutil.bean;

import java.io.Serializable;
import java.util.List;

/**
 */
public class NewsListBean {

    /**
     * date : 20161222
     * stories : [{"title":"教你自制浓郁香辣的老干妈","ga_prefix":"122215","images":["http://pic4.zhimg.com/89d042f7a127e347bd6590e75b05a1f3.jpg"],"multipic":true,"type":0,"id":9086207},{"images":["http://pic4.zhimg.com/1c37f796d58f022e84a06082becf5157.jpg"],"type":0,"id":9084013,"ga_prefix":"122214","title":"得知孩子在学校欺凌别人，暴怒指责可不行"},{"images":["http://pic1.zhimg.com/c6f01a2c42de34967127a15e609a2ce8.jpg"],"type":0,"id":9081837,"ga_prefix":"122213","title":"非科班出身如何走上导演道路？"},{"images":["http://pic3.zhimg.com/0d56c1ec93e9e8cb1750536054d27c16.jpg"],"type":0,"id":9087018,"ga_prefix":"122212","title":"大误 · 教练我想学\u2026\u2026"},{"images":["http://pic1.zhimg.com/25147fea8856c8ccd32e1511119e6ab8.jpg"],"type":0,"id":9085561,"ga_prefix":"122211","title":"冬天里的冰激凌，也可以是奢侈的享受"},{"images":["http://pic1.zhimg.com/fe278497e83b7b1c5569802a44bf7efc.jpg"],"type":0,"id":9084650,"ga_prefix":"122210","title":"好好的书被翻译过来，总是容易变味"},{"images":["http://pic4.zhimg.com/29a5fc41fe9703240fb58bf211253c23.jpg"],"type":0,"id":9081640,"ga_prefix":"122209","title":"应届生进了银行，不说别的了，先干柜员吧"},{"images":["http://pic3.zhimg.com/37be4928feeb1fa2da184135880970fa.jpg"],"type":0,"id":9085604,"ga_prefix":"122208","title":"任天堂：「我不是针对谁，各位做的跑酷游戏都是\u2026\u2026」"},{"images":["http://pic2.zhimg.com/0c2181383e80c8d0eb14d97c0ab0bda5.jpg"],"type":0,"id":9084762,"ga_prefix":"122207","title":"把螃蟹活活蒸死，是「虐杀」吗？"},{"images":["http://pic4.zhimg.com/4a18d1185db94a3edb67eff3c03900f3.jpg"],"type":0,"id":9086224,"ga_prefix":"122207","title":"没看错，最近撒哈拉沙漠下雪了"},{"title":"2016 年度盘点 · 我心目中的十大佳片","ga_prefix":"122207","images":["http://pic3.zhimg.com/02c7b1ee30728f46d42e8a2d36bc36a2.jpg"],"multipic":true,"type":0,"id":9079926},{"images":["http://pic4.zhimg.com/98461aa7145e6015222eb0414a7bebc3.jpg"],"type":0,"id":9086145,"ga_prefix":"122207","title":"读读日报 24 小时热门 TOP 5 · 我去「北京雾霾源头」呆了三天"},{"images":["http://pic4.zhimg.com/ee5996d0508145fca36f05f24dfbd923.jpg"],"type":0,"id":9084342,"ga_prefix":"122206","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/76078ef666dc76a0aeb83b86d162076d.jpg","type":0,"id":9086224,"ga_prefix":"122207","title":"没看错，最近撒哈拉沙漠下雪了"},{"image":"http://pic3.zhimg.com/8b753c5bb49bcff1a9741389b6eceac6.jpg","type":0,"id":9085604,"ga_prefix":"122208","title":"任天堂：「我不是针对谁，各位做的跑酷游戏都是\u2026\u2026」"},{"image":"http://pic4.zhimg.com/46e04c5574df76e9d8d28223d3a6fe87.jpg","type":0,"id":9086145,"ga_prefix":"122207","title":"读读日报 24 小时热门 TOP 5 · 我去「北京雾霾源头」呆了三天"},{"image":"http://pic2.zhimg.com/5046e253d103f803533cbf1ac10b5af9.jpg","type":0,"id":9079926,"ga_prefix":"122207","title":"2016 年度盘点 · 我心目中的十大佳片"},{"image":"http://pic4.zhimg.com/73465bdcd85a7e5ac93b158a95c780bb.jpg","type":0,"id":9085140,"ga_prefix":"122117","title":"知乎好问题 · 用芝士能做出哪些特别好吃的食物？"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean implements Serializable{
        /**
         * title : 教你自制浓郁香辣的老干妈
         * ga_prefix : 122215
         * images : ["http://pic4.zhimg.com/89d042f7a127e347bd6590e75b05a1f3.jpg"]
         * multipic : true
         * type : 0
         * id : 9086207
         */

        private String title;
        private String ga_prefix;
        private boolean multipic;
        private int type;
        private int id;
        private List<String> images;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : http://pic2.zhimg.com/76078ef666dc76a0aeb83b86d162076d.jpg
         * type : 0
         * id : 9086224
         * ga_prefix : 122207
         * title : 没看错，最近撒哈拉沙漠下雪了
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

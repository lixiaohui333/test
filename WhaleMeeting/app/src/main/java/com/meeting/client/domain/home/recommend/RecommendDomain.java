package com.meeting.client.domain.home.recommend;

import com.meeting.client.domain.base.BaseDataList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class RecommendDomain extends BaseDataList<RecommendDomain.NewsListBean> implements Serializable{

    public List<String> adList;


    public class NewsListBean  implements Serializable{
        public String imgUrl;
        public String content;
        public long time;
        public String watchNum;

        @Override
        public String toString() {
            return "NewsListBean{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", content='" + content + '\'' +
                    ", time=" + time +
                    ", watchNum='" + watchNum + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RecommendDomain{" +
                "list=" + list +
                ", pageIndex=" + pageIndex +
                ", totalPage=" + totalPage +
                ", adList=" + adList +
                '}';
    }
}

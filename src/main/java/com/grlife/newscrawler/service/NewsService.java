package com.grlife.newscrawler.service;

import com.grlife.newscrawler.repository.NewsRepository;

import java.util.*;

public class NewsService {

    private final NewsRepository newsRepository;

    private List<Map<String, Object>> dataObj;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        this.dataObj = setData();
    }

    private List<Map<String, Object>> setData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("base", "뉴시스경제");
        map1.put("url", "https://newsis.com/RSS/economy.xml");
        map1.put("tag", "item");
        map1.put("title", "title");
        map1.put("time", "pubDate");
        map1.put("cont", "description");
        map1.put("link", "link");
        map1.put("uId", "id");

        list.add(map1);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("base", "뉴시스정치");
        map.put("url", "https://newsis.com/RSS/politics.xml");
        map.put("tag", "item");
        map.put("title", "title");
        map.put("time", "pubDate");
        map.put("cont", "description");
        map.put("link", "link");
        map.put("uId", "id");

        list.add(map);



        return list;
    }

    public List<Map<String, Object>> getRssData() {

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        for(Map<String, Object> reqMap : dataObj) {

            returnList.addAll(newsRepository.getRssData(reqMap));

        }


        Collections.sort(returnList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer time1 = Integer.parseInt((String) o1.get("timeST"));
                Integer time2 = Integer.parseInt((String) o2.get("timeST"));
                return time2.compareTo(time1);
            }
        });

        return returnList;

    }

}

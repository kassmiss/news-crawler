package com.grlife.newscrawler.repository;

import java.util.List;
import java.util.Map;

public interface NewsRepository {

    List<Map<String, Object>> getRssData(Map<String, Object> reqMap);

}

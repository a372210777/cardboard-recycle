package com.br.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcHelper {

	private static final Logger logger = LoggerFactory.getLogger(JdbcHelper.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public int update(String sql, Map<String, Object> param) {
		logger.debug("update sql: {}; param:{}", sql, param);

		int ret = namedParameterJdbcTemplate.update(sql, param);

		logger.debug("rows affected: {}", ret);
		return ret;
	}

	@Transactional
	public int[] batchUpdate(String sql, List<? extends Map<String, Object>> params) {
		logger.debug("update sql: {}; params:{}", sql, params);

		@SuppressWarnings("unchecked")
		int[] ret = namedParameterJdbcTemplate.batchUpdate(sql, params.toArray(new Map[0]));

		logger.debug("rows affected[]:{}", Arrays.toString(ret));
		return ret;
	}

	public long count(String sql, Map<String, Object> param) {
		logger.debug("count sql: {}; param:{}", sql, param);

		Long count = namedParameterJdbcTemplate.queryForObject(sql, param,
				SingleColumnRowMapper.newInstance(Long.class));

		logger.debug("count:{}", count);
		return count;
	}

	public <T> List<T> queryLang(String sql, Map<String, Object> param, Class<T> retClass) {
		logger.debug("query sql: {}; param:{}", sql, param);

		List<T> results = namedParameterJdbcTemplate.query(sql, param, SingleColumnRowMapper.newInstance(retClass));

		logger.debug("results size:{}", results.size());
		return results;
	}

	public <T> T queryLangFirst(String sql, Map<String, Object> param, Class<T> retClass) {
		logger.debug("query sql: {}; param:{}", sql, param);

		List<T> results = namedParameterJdbcTemplate.query(sql, param, SingleColumnRowMapper.newInstance(retClass));

		logger.debug("results size:{}", results.size());
		if (results.isEmpty()) {
			return null;
		}
		T first = results.get(0);
		logger.debug("first ret:{}", first);
		return first;
	}

	public <T> List<T> query(String sql, Map<String, Object> param, Class<T> retClass) {
		logger.debug("query sql: {}; param:{}", sql, param);

		List<T> results = namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(retClass));

		logger.debug("results size:{}", results.size());
		return results;
	}

	public <T> T queryFirst(String sql, Map<String, Object> param, Class<T> retClass) {
		logger.debug("query sql: {}; param:{}", sql, param);

		List<T> results = namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(retClass));

		logger.debug("results size:{}", results.size());
		if (results.isEmpty()) {
			return null;
		}
		T first = results.get(0);
		logger.debug("first ret:{}", first);
		return first;
	}

	public List<Map<String, Object>> queryMap(String sql, Map<String, Object> param) {
		logger.info("sql:{} ; param:{}", sql, param);

		List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(sql, param);

		logger.debug("results size:{}", results.size());
		return results;
	}
}
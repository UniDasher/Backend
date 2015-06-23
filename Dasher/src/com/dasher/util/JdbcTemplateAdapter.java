package com.dasher.util;

/*
 * @copyright (c) Qeeka 2011 
 * @author chenpeng    2011-11-15
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcTemplateAdapter extends JdbcTemplate {

	public JdbcTemplateAdapter() {
		super();
	}

	public JdbcTemplateAdapter(DataSource ds) {
		super(ds);
	}

	/**
	 * ���Ӳ��һ�ȡ����
	 * @param sql
	 *            sql���
	 * @param params
	 *            �����б�
	 * @return ����
	 */
	public int insertAndGetKey(final String sql, final Object... params) {
		logger.debug("Executing SQL update and returning generated keys");
		final KeyHolder key = new GeneratedKeyHolder();
		int count = this.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
			throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				PreparedStatementSetter pss = newArgPreparedStatementSetter(params);
				try {
					if (pss != null) {
						pss.setValues(ps);
					}
				} finally {
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
				return ps;
			}
		}, key);
		return count > 0 ? key.getKey().intValue() : 0;
	}

}

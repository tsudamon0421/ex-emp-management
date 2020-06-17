package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {

	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mailAddress"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 管理者情報を挿入する.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		String sql = "INSERT INTO administrators (name, mail_address, password) VALUES(:name, :mailAddress, :password);";
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスとパスワードから管理者情報を取得する(1件も存在しない場合はnullを返す).
	 * @param mailAddress
	 * @param password
	 * @return Administrator
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress,String password) {
		String sql = "SELECT id,name,mail_address,password FROM administrators WHERE id = :id && password =:password;";
		
		//引数をつめたAdministratorを生成
		Administrator administrator = new Administrator();
		administrator.setMailAddress(mailAddress);
		administrator.setPassword(password);
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		
		//1件も存在しない場合にnullを返し、存在した場合に1件のオブジェクトを返す
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		if(administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}

}

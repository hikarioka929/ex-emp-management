package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ(Dao).
 * 
 * @author okahikari
 *
 */
@Repository
public class AdministratorRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER
	= new BeanPropertyRowMapper<>(Administrator.class);
	
	/**
	 * 管理者情報を挿入する.
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		
		String sql = "INSERT INTO administrators (name, mail_address, password) VALUES (:name, :mailAddress, :password);";
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * (1件も存在しない場合はnullを返す)。
	 * @param mailAddress メールアドレス
	 * @param password 	　 パスワード
	 * @return 管理者情報
	 */
	public Administrator findByMailAddressAndPassWord(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress AND password = :password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password", password);
		
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		
		if(administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}

}

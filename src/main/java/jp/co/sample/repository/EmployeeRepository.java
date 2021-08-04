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

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ(Dao).
 * 
 * @author okahikari
 *
 */

@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER
	= new BeanPropertyRowMapper<>(Employee.class);
	
	/**
	 * 従業員一覧情報を入社日順(降順)で取得する.
	 * (従業員が存在しない場合はサイズ0件の従業員一覧を返す)。
	 * @return 従業員一覧情報
	 */
	public List<Employee> findAll(){
		String sql = "SELECT id, name, image, gender, hireDate, mailAddress, zipCode, address, telephone, salary, characteristics, dependentsCount"
				   + " FROM employees;";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
	/**
	 * 主キーから従業員情報を取得する.
	 * @param id ID
	 * @return 従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id, name, image, gender, hireDate, mailAddress, zipCode, address, telephone, salary, characteristics, dependentsCount"
				    + " FROM employees WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}
	
	/**
	 * 従業員情報を変更する.
	 * (idカラムを除いた従業員情報の全てのカラムを更新できるようなSQLを発行する)。
	 * @param 更新する従業員情報
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET name = :name, image = :image, gender = :gender, hire_date = :hireDate, mail_address = :mailAddress, "
				   + "zip_code = :zipCode, address = :address, telephone = :telephone, salary = :salary, characteristics = :characteristics,"
				   + " dependents_count = dependentsCount WHERE id = :id;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);
	}
}

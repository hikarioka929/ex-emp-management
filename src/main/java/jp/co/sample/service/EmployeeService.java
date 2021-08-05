package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員関連機能の業務処理を行うサービス.
 * 
 * @author okahikari
 *
 */
@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得する.
	 * @return 全従業員情報
	 */
	public List<Employee> showList(){
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}
	
	/**
	 * 従業員情報を取得する.
	 * @param id ID
	 * @return 取得した従業員情報
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

}

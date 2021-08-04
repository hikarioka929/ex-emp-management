package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者登録画面を表示する処理.
 * 
 * @author okahikari
 *
 */

@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	/**
	 * @return 入力された従業員情報
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm form = new InsertAdministratorForm();
		return form;
	}
	
	/**
	 * @return 入力されたログイン情報
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm form = new LoginForm();
		return form;
	}
	
	/**
	 * administrator/insert.htmlにフォワードする.
	 * @return 表示するhtmlファイル
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者情報を登録する.
	 * @param form 入力された値が詰まったオブジェクト
	 * @return ログイン画面へのリダイレクトパス
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
	/**
	 * administrator/login.htmlにフォワードする.
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login.html";
	}

}

package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm form = new InsertAdministratorForm();
		return form;
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm form = new LoginForm();
		return form;
	}
	
	/**
	 * 管理者登録画面にフォワードする.
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
	 * 管理者ログイン画面にフォワードする.
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login.html";
	}
	
	/**
	 * ログイン処理をする.
	 * @param form 入力された値が詰まったオブジェクト
	 * @param model requestスコープ
	 * @return 従業員情報一覧ページ
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		String mailAddress = administrator.getMailAddress();
		String password = administrator.getPassword();
		administrator = administratorService.login(mailAddress, password);
		
		if( administrator == null ) {
			model.addAttribute("message", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}
		
		String administratorName = administrator.getName();
		session.setAttribute("administratorName", administratorName);
		
		return "forward:/employee/showList";
	}
	
	/**
	 * ログアウト処理をする.
	 * @return ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
}

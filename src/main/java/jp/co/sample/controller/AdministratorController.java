package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HttpSession session;// ログインした管理者の名前をセッションスコープに入れてログイン後のページに表示するため

	/**
	 * 管理者登録情報を受け取るInsertAdministratorFormをインスタンス化しそのまま返す.
	 * 
	 * @return InsertAdministratorForm
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * ログイン時に使用するフォームをインスタンス化しそのまま返す.
	 * 
	 * @return LoginForm
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * 管理者情報登録画面にフォワードする.
	 * 
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form 管理者情報登録フォーム
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form, BindingResult result, Model model) {
		
		//もしエラーが1つでもあった場合は入力画面に遷移
		if(result.hasErrors()) {
			return toInsert();
		}
		
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);

		return "redirect:/";
	}

	/**
	 * 管理者ログイン画面にフォワード処理.
	 * 
	 * @return administrator/login.html
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login.html";
	}
	
	/**
	 * ログイン処理メソッド.
	 * 
	 * @param form ログイン処理用フォーム
	 * @param model　ログインエラー文用のリクエストスコープ
	 * @return　エラー時はログイン画面、成功時は従業員情報画面にフォワード
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			model.addAttribute("message", "メールアドレスまたはパスワードが不正です");
			return "administrator/login.html";
		} else {
			session.setAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}
	}
	
	/**
	 * ログアウト処理メソッド.
	 * 
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}
package jp.co.sample.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author hikaru.tsuda
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	HttpSession session;

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員一覧をリクエストスコープに取得して出力する.
	 * 
	 * @param model リクエストスコープ
	 * @return 従業員一覧
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		
		//ログインしていなければログイン画面に遷移
		if(session.getAttribute("administratorName") == null) {
			return "redirect:/";
		}
		
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);

		return "employee/list";
	}

	/**
	 * 従業員IDから従業員情報を受け取り、従業員詳細ページにフォワードする.
	 * 
	 * @param id    従業員ID (Stringで受け取りint型に変換)
	 * @param model リクエストスコープを使用して従業員情報を格納する
	 * @return 従業員詳細ページにフォワード
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		
		//ログインしていなければログイン画面に遷移
		if(session.getAttribute("administratorName") == null) {
			return "redirect:/";
		}
		
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/**
	 * 従業員情報（ここでは扶養人数のみ）を更新する.
	 * 
	 * @param form 従業員情報のidを使用
	 * @return 従業員情報一覧画面にリダイレクトする
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		//ログインしていなければログイン画面に遷移
		if(session.getAttribute("administratorName") == null) {
			return "redirect:/";
		}
		
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
	
//	@RequestMapping("/update2")
//	public String update2() {
//	}

}

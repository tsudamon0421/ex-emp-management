package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	
	/**
	 * 従業員一覧をリクエストスコープに取得して出力する.
	 * 
	 * @param model リクエストスコープ
	 * @return　従業員一覧
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		
		return "employee/list";
	}
	
	
	/**
	 * 従業員IDから従業員情報を受け取り、従業員詳細ページにフォワードする.
	 * 
	 * @param id 従業員ID (Stringで受け取りint型に変換)
	 * @param model リクエストスコープを使用して従業員情報を格納する
	 * @return　従業員詳細ページにフォワード
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail.html";
	}

}

package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理者登録情報を受け取るフォームクラス.
 * 
 * @author hikaru.tsuda
 *
 */
public class InsertAdministratorForm {

	/** 名前 */
	@NotBlank(message = "氏名の入力は必須です")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "Eメールの入力は必須です")
	@Email(message = "Eメールの形式が不正です")
	private String mailAddress;
	/** パスワード */
	@Size(min = 8, max = 127, message = "パスワードは8文字以上で入力してください")
	private String password;

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

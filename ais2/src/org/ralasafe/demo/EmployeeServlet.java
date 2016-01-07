/**
 * Copyright (c) 2010 Wang Jinbao, http://www.ralasafe.com
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.ralasafe.demo;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ralasafe.WebRalasafe;
import org.ralasafe.util.StringUtil;

public class EmployeeServlet extends HttpServlet {
	private EmployeeManager employeeManager = new EmployeeManager();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String op = req.getParameter("op");

		// Get data from request
		String strId = req.getParameter("id");
		String loginName = req.getParameter("loginName");
		String name = req.getParameter("name");
		String password = "password";
		String strDeptId = req.getParameter("departmentId");
		String strCompanyId = req.getParameter("companyId");
		String strIsManager = req.getParameter("isManager");

		// Construct a Employee POJO
		Employee employee = new Employee();
		if (!StringUtil.isEmpty(strId)) {
			employee.setId(Integer.parseInt(strId));
		}
		employee.setLoginName(loginName);
		employee.setName(name);
		employee.setPassword(password);
		if (!StringUtil.isEmpty(strDeptId)) {
			employee.setDepartmentId(Integer.parseInt(strDeptId));
		}
		if (!StringUtil.isEmpty(strCompanyId)) {
			employee.setCompanyId(Integer.parseInt(strCompanyId));
		}
		if (!StringUtil.isEmpty(strIsManager)) {
			if (Boolean.getBoolean(strIsManager)) {
				employee.setIsManager(1);
			} else {
				employee.setIsManager(0);
			}
		}

		// Execute business operation through EmployeeManager API
		if ("add".equalsIgnoreCase(op)) {
			if (WebRalasafe.permit(req, Privilege.CREATE_EMPLOYEE,
					employee)) {
				employeeManager.addEmployee(employee);
			}
		} else if ("delete".equalsIgnoreCase(op)) {
			int id = Integer.parseInt(strId);
			employee = employeeManager.getEmployee(id);
			if (WebRalasafe.permit(req, Privilege.DELETE_EMPLOYEE,
					employee)) {
				employeeManager.deleteEmployee(id);
			}
		} else if ("update".equalsIgnoreCase(op)) {
			int id = Integer.parseInt(strId);
			//employeeManager.getEmployee(id);
			if (WebRalasafe.permit(req, Privilege.UPDATE_EMPLOYEE,
					employee)) {
				employeeManager.updateEmployee(employee);
			}
		} else if ("prepareUpdate".equalsIgnoreCase(op)) {
			Employee prepareUpdateEmployee = employeeManager
					.getEmployee(employee.getId());
			req.setAttribute("employee", prepareUpdateEmployee);
		}

		// Get employees that current user is permitted to view.
		Collection employees = WebRalasafe.query(req, Privilege.QUERY_EMPLOYEE);
		req.setAttribute("employees", employees);

		RequestDispatcher rd = req.getRequestDispatcher("employee.jsp");
		rd.forward(req, resp);
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}

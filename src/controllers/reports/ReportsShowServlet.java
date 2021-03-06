package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));

        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("follow_employee_id")));

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        long my_reports_goods_count = (long)em.createNamedQuery("getMyReportsGoodsCount", Long.class)
                .setParameter("report", r)
                .setParameter("employee", login_employee)
                .getSingleResult();

        long check_follows_employees = (long)em.createNamedQuery("checkFollowsEmployees", Long.class)
                .setParameter("employee", login_employee)
                .setParameter("follow_employee", e)
                .getSingleResult();

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("my_reports_goods_count", my_reports_goods_count);
        request.setAttribute("check_follows_employees", check_follows_employees);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}

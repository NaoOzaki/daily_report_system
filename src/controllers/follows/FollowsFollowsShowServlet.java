package controllers.follows;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsFollowsShow
 */
@WebServlet("/follows/follows_show")
public class FollowsFollowsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsFollowsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(NumberFormatException e){}
        List<Follow> my_follows_employees = em.createNamedQuery("getMyFollowsEmployees", Follow.class)
                                     .setParameter("employee", login_employee)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        long my_follows_employees_count = (long)em.createNamedQuery("getMyFollowsEmployeesCount", Long.class)
                                          .setParameter("employee", login_employee)
                                          .getSingleResult();

        em.close();

        request.setAttribute("my_follows_employees", my_follows_employees);
        request.setAttribute("my_follows_employees_count", my_follows_employees_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/follows_show.jsp");
        rd.forward(request, response);
    }

}

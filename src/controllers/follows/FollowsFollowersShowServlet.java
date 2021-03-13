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
 * Servlet implementation class FollowsFollowersShow
 */
@WebServlet("/follows/followers_show")
public class FollowsFollowersShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsFollowersShowServlet() {
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

        List<Follow> my_followers_employees = em.createNamedQuery("getMyFollowersEmployees", Follow.class)
                                                   .setParameter("employee", login_employee)
                                                   .setFirstResult(15 * (page - 1))
                                                   .setMaxResults(15)
                                                   .getResultList();

        long followCheck[] = new long[my_followers_employees.size()];
        int i = 0;
        for (Follow follower_employee : my_followers_employees) {
            long checkCnt = (long)em.createNamedQuery("checkMyFollowersEmployees", Long.class)
                              .setParameter("employee", login_employee)
                              .setParameter("follow_employee", follower_employee.getEmployee())
                              .getSingleResult();
            followCheck[i] = checkCnt;
            i++;
        }

        long my_followers_employees_count = (long)em.createNamedQuery("getMyFollowersEmployeesCount", Long.class)
                                                          .setParameter("employee", login_employee)
                                                          .getSingleResult();

        em.close();

        request.setAttribute("my_followers_employees", my_followers_employees);
        request.setAttribute("followCheck", followCheck);
        request.setAttribute("my_followers_employees_count", my_followers_employees_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/followers_show.jsp");
        rd.forward(request, response);
    }

}

package controllers.goods;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Good;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class GoodCreateServlet
 */
@WebServlet("/goods/create")
public class GoodsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Good g = new Good();

        g.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        g.setReport(em.find(Report.class, Integer.parseInt(request.getParameter("id"))));

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        g.setCreated_at(currentTime);
        g.setUpdated_at(currentTime);

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        r.setGood_count(r.getGood_count() + 1);

        em.getTransaction().begin();
        em.persist(g);
        em.persist(r);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "いいねしました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");
    }

}

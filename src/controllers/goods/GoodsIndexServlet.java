package controllers.goods;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Good;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class GoodsIndexServlet
 */
@WebServlet("/goods/index")
public class GoodsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        List<Good> reports_goods = em.createNamedQuery("getReportsGoods", Good.class)
                                        .setParameter("report", r)
                                        .setFirstResult(15 * (page - 1))
                                        .setMaxResults(15)
                                        .getResultList();

        long reports_goods_count = (long)em.createNamedQuery("getReportsGoodsCount", Long.class)
                                                .setParameter("report", r)
                                                .getSingleResult();

        em.close();

        request.setAttribute("reports_goods", reports_goods);
        request.setAttribute("reports_goods_count", reports_goods_count);
        request.setAttribute("report", r);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/goods/index.jsp");
        rd.forward(request, response);
    }
}

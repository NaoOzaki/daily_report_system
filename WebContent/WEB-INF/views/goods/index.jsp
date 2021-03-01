<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>いいねした人一覧</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <th>いいねした日時</th>
                </tr>
                <c:forEach var="good" items="${reports_goods }" varStatus="status">
                    <tr>
                        <td><c:out value="${good.employee.name }" /></td>
                        <td><fmt:formatDate value="${good.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${reports_goods_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((reports_goods_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/goods/index?id=${report.id }&page=${i }' />"><c:out value="${i }" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>
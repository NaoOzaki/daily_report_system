<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>フォロワー 一覧</h2>
        <table>
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="follower" items="${my_followers_employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${follower.employee.code }" /></td>
                        <td><c:out value="${follower.employee.name }" /></td>
                        <c:choose>
                            <c:when test="${followCheck[status.index] == 0}">
                                <td><a href="<c:url value="/follows/create?id=${follower.employee.id}" />">フォローする</a></td>
                            </c:when>
                            <c:otherwise>
                                <td>&nbsp;</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${my_followers_employees_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((my_followers_employees_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/follows/followers_show?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>
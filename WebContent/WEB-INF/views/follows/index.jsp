<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null }">
            <div id="flush_success">
                <c:out value="${flush }"></c:out>
            </div>
        </c:if>
        <h2>タイムライン</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_good_count">いいね</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="report" items="${my_follows_employees_reports }" varStatus="status">
                    <tr class="row${status.count % 2 }">
                        <td class="report_name"><c:out value="${report.employee.name }" /></td>
                        <td class="report_date"><fmt:formatDate value='${report.report_date }' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${report.title }</td>
                        <c:choose>
                            <c:when test="${report.good_count == 0 }">
                                <td class="report_good_count">${report.good_count}</td>
                            </c:when>
                            <c:otherwise>
                                <td class="report_good_count"><a href="<c:url value='/goods/index?id=${report.id }' />">${report.good_count}</a></td>
                            </c:otherwise>
                        </c:choose>
                        <td class="report_action"><a href="<c:url value='/reports/show?report_id=${report.id }&follow_employee_id=${report.employee.id }' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${my_follows_employees_reports_count } 件）<br />
            <c:forEach var="i" begin="1" end="${((my_follows_employees_reports_count - 1) / 15) + 1 }" step="1">
                <c:choose>
                    <c:when test="${i == page }">
                        <c:out value="${i }" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/follows/index?page=${i }' />"><c:out value="${i }" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul>
<c:forEach items="${roleAssignmentList}" var="roleAssignment">
    <li> ${roleAssignment.role} - ${roleAssignment.uri} </li>
</c:forEach>
</ul>

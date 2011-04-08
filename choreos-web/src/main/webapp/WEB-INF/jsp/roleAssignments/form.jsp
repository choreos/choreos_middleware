<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="<c:url value='/roleAssignments/add'/>">
    Role:             <input type="text" name="roleAssignment.role" /><br/>
    URI:	  <input type="text" name="roleAssignment.uri" /><br/>
    <input type="submit" value="Save" />
</form>

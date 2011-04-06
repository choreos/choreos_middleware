<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


Olá macacada!

<form action="<c:url value='/roleAssignments/add'/>">
    Name:             <input type="text" name="roleAssignment.role" /><br/>
    URI:	  <input type="text" name="roleAssignment.uri" /><br/>
    <input type="submit" value="Save" />
</form>

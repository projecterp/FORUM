<%@ page import="java.io.IOException"
import ="java.io.PrintWriter"
import ="java.util.ArrayList"
import ="java.util.Iterator"
import ="com.google.appengine.api.datastore.Entity"
import ="java.util.Calendar"
import ="javax.servlet.RequestDispatcher"
import ="javax.servlet.ServletException"
import ="javax.servlet.http.*"
import ="com.google.appengine.api.datastore.DatastoreServiceFactory"
import ="java.text.SimpleDateFormat"
import ="com.google.appengine.api.datastore.PreparedQuery"
import ="com.google.appengine.api.datastore.Query"
import ="com.google.apphosting.api.DatastorePb.DatastoreService"
import ="com.sun.xml.internal.bind.v2.schemagen.xmlschema.List"
import ="com.google.appengine.api.datastore.Query.SortDirection"
import ="com.google.appengine.api.datastore.Query.Filter"
import ="com.google.appengine.api.datastore.Query.FilterPredicate"
import ="com.google.appengine.api.datastore.Query.FilterOperator"
import ="com.google.appengine.api.datastore.Query.CompositeFilter"
import ="com.google.appengine.api.datastore.Query.CompositeFilterOperator"
%>
<html>
<head>
<title>The Posts are...</title>
<style>
body { 
    background:  url("6918865-black-background-wood.jpg") no-repeat fixed center; 
}
div {
    background-color: lightgrey;
    width: 300px;
    padding: 15px;
    border: 5px solid navy;
    margin: 15px;
}
</style>
</head>
<body>
<strong>
<font color="red" size="20">
<p>TOPICS</p>
<p>Your posts are:-</p>
</font>
<font color="blue" size="10">
<form action="forum_v1" method="get">
<input type="hidden" name="sem" value="<%=request.getParameter("sem")%>">
<input type="hidden" name="sub" value="<%=request.getParameter("sub")%>">
<%
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    Filter semFilter =
      new FilterPredicate("sem",
                      FilterOperator.EQUAL,
                      request.getParameter("sem"));
    Filter subFilter =
      new FilterPredicate("sub",
                      FilterOperator.EQUAL,
                      request.getParameter("sub"));

    Filter validFilter = CompositeFilterOperator.and(semFilter,subFilter);
    com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();  
    Query q = new Query("Topics").setFilter(validFilter).addSort("time", SortDirection.DESCENDING);
		PreparedQuery pq = ds.prepare(q);

		for (Entity result : pq.asIterable()) {
		  ArrayList<String> temp=new ArrayList();
		  temp=(ArrayList<String>)result.getProperty("topics");
		  for(String str:temp)
		  {
%>
<p><input type="Submit" name="btn" height="10" value="<%=str%>"></p>
<%
}
} 
%>
<p>NEW TOPIC:<input type="text" name="topic"><p>
<input type="Submit" name="btn" value="ADD NEW TOPIC">
</font>
</form>
</body>
</html>
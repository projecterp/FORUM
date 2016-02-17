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
<font color="red">
<p>Hey welcome</p>
<p>Your posts are:-</p>
</font>
</strong>
<%
   Calendar cal = Calendar.getInstance();
   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();  
    Query q = new Query("Post").addSort("time", SortDirection.DESCENDING);
		q.setFilter(
		new Query.FilterPredicate(
		"time",
		Query.FilterOperator.LESS_THAN_OR_EQUAL,sdf.format(cal.getTime())));
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
%>			
<div id="my_div">
<%
  ArrayList<String> tag=new ArrayList();
  tag=(ArrayList)result.getProperty("tag");
  for(String str:tag)
  {
%> 
<form action="tag" method="get"><input type="Submit" name="submit" value="<%=str%>">
</form>
<%
}
%>
<p><%=result.getProperty("str")%></p>
<p><%=result.getProperty("comment_count")%>comments</a></p>
   <form action="COMMENT" method="get">
   <br><textarea  name="comment"></textarea><br>
   <input type="hidden" name="time" value="<%=result.getProperty("time")%>"> 
   <input type="Submit" name="Comment" value="Post Comment">
   </form> 
</div>
<%
}
%>
</body>
</html>
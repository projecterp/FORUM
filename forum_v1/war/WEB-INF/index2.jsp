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
p{
   height:21px;
}
div {
    background-color: lightgrey;
    width: 300px;
    padding: 15px;
    border: 5px solid navy;
}
#header {
    background-color:black;
    color:white;
    text-align:center;
    width:auto;
    height:30px;
}
#nav {
    height:300px;
    width:100px;
    float:left;	      
}
#section {
    width:852px;
    height:490px;
    content-align:center;
    float:left;	 	 
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    width:auto;
    height:20px;
    text-align:center;
    padding:0px;	 	 
}
</style>
</head>
<body>
<div id="header">
    WELCOME TO FORUM
</div>
<div id="nav" style="width:400px;height:490px;overflow:auto;">
<strong>
<font color="red">
<p>Hey welcome</p>
<p>Your posts are:-</p>
</font>
</strong>
<%
     String topic;
     if((request.getParameter("btn")).equals("ADD POST"))
     {
        topic=request.getParameter("topic");
     }
     else if((request.getParameter("btn")).equals("PostComment"))
     {
        topic=request.getParameter("topic");
     }
     else
     {
        topic=request.getParameter("btn");
     }
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

     Filter topicFilter =
      new FilterPredicate("topic",
                      FilterOperator.EQUAL,
                      topic);

    Filter validFilter = CompositeFilterOperator.and(semFilter,subFilter,topicFilter);
    com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();  
    Query q = new Query("Post").setFilter(validFilter).addSort("time", SortDirection.DESCENDING);
    PreparedQuery pq = ds.prepare(q);
    for (Entity result : pq.asIterable()) {
	   if(result!=null){	
%>			
<div id="my_div">
<%
  ArrayList<String> tag=new ArrayList();
  tag=(ArrayList)result.getProperty("tag");
  for(String str:tag)
  {
%> 
<form action="tag" method="get"><input type="Submit" name="btn" value="<%=str%>">
<!-- </form> -->
<%
}
%>
<p><%=result.getProperty("str")%></p>
<p><%=result.getProperty("comment_count")%>comments</p>
<!-- <form action="COMMENT" method="get"> -->
   <textarea  name="comment"></textarea><br>
   <input type="hidden" name="time" height="0px" value="<%=result.getProperty("time")%>">
   <input type="hidden" name="sem"  height="opx" value="<%=request.getParameter("sem")%>">
   <input type="hidden" name="sub"  height="opx" value="<%=request.getParameter("sub")%>">
   <input type="hidden" name="topic"  height="opx" value="<%=topic%>"> 
   <input type="Submit" name="btn" value="PostComment">
</form>  
</div>
<%
}
}
%>
<br>    
</div>
<div id="section">
<form action="forum_v1" method="get">
<input type="hidden" name="sem" value="<%=request.getParameter("sem")%>">
<input type="hidden" name="sub" value="<%=request.getParameter("sub")%>">
<input type="hidden" name="topic" value="<%=topic%>">
<p>NEW POST:-</p>
<p><textarea name="post"></textarea></p>
<p>tags:<input type="text" name="tag"></p>
<p><input type="Submit" name="btn" value="ADD POST"></p>
</form>
</div>
<div id="footer">
Copyright © ProjectERP
</div>
</body>
</html>
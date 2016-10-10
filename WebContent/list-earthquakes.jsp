<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>List Earthquakes</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Earthquakes Data</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			
			<table>
			
				<tr>
					<th>Date</th>
					<th>Time</th>
					<th>Magnitude</th>
					<th>Location</th>
				</tr>
				
				<c:forEach var="tempEarthquake" items="${EARTHQUAKE_LIST}">
					
					<tr>
						<td> ${tempEarthquake.event_date} </td>
						<td> ${tempEarthquake.event_time} </td>
						<td> ${tempEarthquake.mag} </td>
				
						<td> ${tempEarthquake.place} </td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>









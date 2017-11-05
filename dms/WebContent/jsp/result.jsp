<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Pregled Korisnika</title>
   </head>

   <body>
      <h2>Uneseni podaci o korisniku</h2>
      <table>
         <tr>
            <td>Ime</td>
            <td>${ime}</td>
         </tr>
         <tr>
            <td>Prezime</td>
            <td>${prezime}</td>
         </tr>         
         <tr>
            <td>Uloga</td>
            <td>${uloga}</td>
         </tr>
         <tr>
            <td>ID</td>
            <td>${id}</td>
         </tr>
      </table>  
   </body>
   
</html>
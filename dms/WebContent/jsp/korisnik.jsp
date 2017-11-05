<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Unos Korisnika</title>
   </head>

   <body>
      <h2>Podaci o korisniku</h2>
      <form:form method = "POST" action = "/DMS/addKorisnik">
         <table>
            <tr>
               <td><form:label path = "ime">Ime</form:label></td>
               <td><form:input path = "ime" /></td>
            </tr>
            <tr>
               <td><form:label path = "prezime">Prezime</form:label></td>
               <td><form:input path = "prezime" /></td>
            </tr>
            <tr>
               <td><form:label path = "uloga">Uloga</form:label></td>
               <td><form:input path = "uloga" /></td>
            </tr>
            <tr>
               <td><form:label path = "id">id</form:label></td>
               <td><form:input path = "id" /></td>
            </tr>
            <tr>
               <td colspan = "3">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
   
</html>
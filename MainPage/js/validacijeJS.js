var emailRegex = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/;
var userRegex =/^[a-zA-Z0-9]+$/;
var passRegex = /^[a-zA-Z0-9]+.{8,20}$/;


function validacijaR()
{
  var formaR = document.getElementById('RegistracijaForma');
	var 	korisnickiErr, emailErr, passErr, ppassErr;
	korisnickiErr = document.getElementById('korisnickiErr');
	emailErr = document.getElementById('emailErr');
	passErr = document.getElementById('passErr');
	ppassErr = document.getElementById('ppassErr');

	var fields = ["userR", "emailR", "passR", "passPR"];
	var errori =["korisnickiErr", "emailErr", "passErr", "ppassErr"];
	var i, n = fields.length;
	var fieldname;
	for (i = 0; i < n; i++) {
		fieldname = fields[i];
		if (formaR[fieldname].value == null || formaR[fieldname].value == "" ) {
					document.getElementById(errori[i]).innerHTML="Obavezan podatak!";
					document.getElementById(fieldname).focus();
	      return false;
		}
		document.getElementById(errori[i]).innerHTML="";

	}

			var validno = document.getElementById(fields[0]).value.match(userRegex);
			if(validno == null){
				 document.getElementById(errori[0]).innerHTML="Možete koristiti samo slova i brojeve!";
				 document.getElementById(fields[0]).focus();
				 return false;
			 }

		 	var validnoEM=document.getElementById('emailR').value.match(emailRegex);
		 	if(validnoEM== null)
	  	{
	    	document.getElementById(errori[1]).innerHTML = "Pogrešno unesen email!";
	    	document.getElementById(fields[1]).focus();
				document.getElementById(errori[1]).innerHTML+="";

	    	return false;
	  	}

			var validnoPass=document.getElementById('passR').value.match(passRegex);
			if(validnoPass==null)
			{
		  	document.getElementById(errori[2]).innerHTML = "Šifra mora sadržavati samo slova i brojeve, minimalno 8 znakova!";
		  	document.getElementById(fields[2]).focus();
				document.getElementById(errori[2]).innerHTML+="";

		  	return false;
			}

	  	if(formaR[fields[2]].value  != formaR[fields[3]].value)
	  	{
	    	document.getElementById(errori[3]).innerHTML = "Šifre nisu iste!";
				document.getElementById(fields[3]).focus();
				document.getElementById(errori[3]).innerHTML+="";

	    	return false;
	  	}

return true;
}

function validacijaP()
{
  var formaP = document.getElementById('LogInForma');
	var 	korisnickiErrP, passErr, ppassErr;
	korisnickiErrP = document.getElementById('korisnickiErrP');
	passErrP = document.getElementById('passErrP');

	var fields = ["userP", "passP"];
	var errori =["korisnickiErrP", "passErrP"];
	var i, n = fields.length;
	var fieldname;
	for (i = 0; i < n; i++) {
		fieldname = fields[i];
		if (formaP[fieldname].value == null || formaP[fieldname].value == "" ) {
					document.getElementById(errori[i]).innerHTML="Obavezan podatak!";
					document.getElementById(fieldname).focus();
	      return false;
		}
		document.getElementById(errori[i]).innerHTML="";

	}
			var validno = document.getElementById(fields[0]).value.match(userRegex);
			if(validno == null){
				 document.getElementById(errori[0]).innerHTML="Nepostojeći username!";
				 document.getElementById(fields[0]).focus();
				 return false;
			 }

			var validnoPass=document.getElementById(fields[1]).value.match(passRegex);
			if(validnoPass==null)//Dodati uslov da se provjeri lozinka za neki
			{
		  	document.getElementById(errori[1]).innerHTML = "Ne valja šifra!";
		  	document.getElementById(fields[1]).focus();
		  	return false;
			}

			//ZA CHECKBOX CHEXKED!!!!!!!!!!!!!!!!!!!!!!!!!

return true;
}

function validacijaV()
{
  var formaV = document.getElementById('VratiForma');
	var 	emailErrV;
	emailErrV= document.getElementById('emailErrV');
	var validnoEMV=document.getElementById('useremail').value.match(emailRegex);

	if (formaV['useremail'].value == null || formaV['useremail'].value == "" || validnoEMV==null ) {
			document.getElementById('emailErrV').innerHTML="Nepostojeći e-mail!";
			document.getElementById('useremail').focus();
			return false;
	}

	document.getElementById('emailErrV').innerHTML="";

	return true;
}

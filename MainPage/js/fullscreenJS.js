//Fullscreen

function showImage(smSrc , lmSrc) {
	document.getElementById('largeImg').src = lmSrc;
  showLargeImagePanel();
	unselectAll();
  setTimeout(function() {
  	document.getElementById('largeImg').src = lmSrc;
  }, 1);
}

function showLargeImagePanel() {
	document.getElementById('largeImgPanel').style.display = 'block';
}
function unselectAll() {
	if(document.selection){
    document.selection.empty();
	}
  if(window.getSelection){
  	window.getSelection().removeAllRanges();
	}
}

document.addEventListener("keydown", function(e) {
  		if (e.keyCode==27) {
    			zatvoriFullScreen(document.getElementById('largeImgPanel'));
  		}
		}, false);

function zatvoriFullScreen(obj) {
    obj.style.display= 'none';
}

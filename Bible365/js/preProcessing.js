function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

var chapterNumber = getParameterByName('chapterNumber'); 
var m = getParameterByName('m');

//handle the parameter passed in -chapterNumber
if (chapterNumber == null){
    chapterNumber=1;
}

//handle the parameter passed in - version
if (m == null || m == 'c'){
    version="hhb";
} else if (m= "e") {
    version= "niv"
} 

// 			var bookPathUrl = "https://s3.amazonaws.com/bible365/json/" + bookNumber + ".json"; 

// 			var book ;
// 			$( document ).ready(function() {
// 				$.getJSON(bookPathUrl, function(data){
// 					book = data;
// 					reload()

// 				});


// 			});

book = chapterData;



reload();

function reload() {

}


// 			$.getJSON("json/Book.json", function(data){
// 				alert(data["BookName"]);

// 			});

// 			$.getJSON("https://lmncw4mlel.execute-api.us-east-1.amazonaws.com/prod/bible-init-2?username=livingwater&lastname=hu3", function(data){
// 				alert(data["statusCode"]);

// 			});
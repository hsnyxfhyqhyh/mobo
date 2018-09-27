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
var page = getParameterByName('p'); 

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

var titleName;

var chapterCount = parseInt(book["ChapterCount"]);

if (page == "last"){
    chapterNumber = chapterCount;
}
//alert (chapterCount);

function reload() {
    var chapter = book.Chapters[chapterNumber-1];
    var verseVersion ;

    for (i in chapter.Versions) {
        v = chapter.Versions[i];

        if (version == v.VersionName)  {
            verseVersion = v;
        }
    }

    if (version == 'hhb') { titleName = chapter.BookName }
    else if (version = 'niv') {titleName = chapter.BookName_En}

    //set header content
    document.getElementById("header1").innerHTML = 
        "<h1 class='ui-title' role='heading' aria-level='1'><font style='white-space:normal;font-family:SimHei;font-size: large' >" + titleName 
        + " - " + chapter.ChapterNumber + "</font></h1>";

    //set the content section
    c = "<ol data-role='listview' class='ui-listview'>"
    for (j in verseVersion.Verses) {
        c += "<li class='ui-li-static ui-body-inherit ui-first-child'><font style='white-space:normal;font-family:SimSun;  font-size: medium' >" 
                + verseVersion.Verses[j].VerseContent 
              + "</font></li>"
    }

    c += "</ol>"

    document.getElementById("content").innerHTML = c;
}

function navPrevious(){
    if (chapterNumber > 1){
       chapterNumber = parseInt(chapterNumber) -1;
       reload();
    } else if (chapterNumber ==1) {
        if (bookNumber != "01") {
            var b;
            if (bookNumber.startsWith("0")){
                b = parseInt(bookNumber.substring(1, 2));
            } else {
                b=parseInt(bookNumber);
            }
            
            b = b-1;
            
            if (b<10) {
                bookNumber = "0" + b;
            } else {
                bookNumber = "" + b;
            }
            
            //alert(bookNumber);
            window.location = "book" + bookNumber +".html?p=last&m=" + m;
        }
    }
    
}


function navNext(){
    if (chapterNumber < chapterCount){
       chapterNumber = parseInt(chapterNumber) + 1;
       reload();
    } else {
        if (bookNumber != "66") {
            var b;
            if (bookNumber.startsWith("0")){
                b = parseInt(bookNumber.substring(1, 2));
            } else {
                b=parseInt(bookNumber);
            }
            b = b+1;

            if (b<10) {
                bookNumber = "0" + b;
            } else {
                bookNumber = "" + b;
            }

            //alert(bookNumber);
            window.location = "book" + bookNumber +".html?m=" + m;
        }
    }
}

// 			$.getJSON("json/Book.json", function(data){
// 				alert(data["BookName"]);

// 			});

// 			$.getJSON("https://lmncw4mlel.execute-api.us-east-1.amazonaws.com/prod/bible-init-2?username=livingwater&lastname=hu3", function(data){
// 				alert(data["statusCode"]);

// 			});
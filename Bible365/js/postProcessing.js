var chapter = book.Chapters[chapterNumber-1];
var verseVersion ;

for (i in chapter.Versions) {
    v = chapter.Versions[i];

    if (version == v.VersionName)  {
        verseVersion = v;
    }
}

//set header content
document.getElementById("header").innerHTML = "<h1><font style='white-space:normal;font-family:SimHei;' >" + chapter.BookName + " - " + chapter.BookName_En + " - " + chapter.ChapterNumber + "</font></h1>";

//set the content section
c = "<ol data-role='listview' >"
for (j in verseVersion.Verses) {
    c += "<li><font style='white-space:normal;font-family:SimSun;  font-size: small' >" + verseVersion.Verses[j].VerseContent + "</font></li>"
}

c += "</ol>"

document.getElementById("content").innerHTML = c;
/* 
 * Walnut Javascript page (contains all needed functions, included in structure, see template "top")
 * 
 */

// Transform:
//     &lt; to <
//     &gt; to >
//     &amp; to &
function unescapeHTML(escapedHTML) {
    // From XSLT: unescapeHTML should produce the output string in the html code:
    // This is complicated because of all this espace ampersand and lt and gt thingies...
    //  return escapedHTML.replace(/]]><xsl:text disable-output-escaping="yes"><![CDATA[&]]></xsl:text><![CDATA[lt;/g,']]><xsl:text disable-output-escaping="yes"><![CDATA[<]]></xsl:text><![CDATA[').replace(/]]><xsl:text disable-output-escaping="yes"><![CDATA[&]]></xsl:text><![CDATA[gt;/g,']]><xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text><![CDATA[').replace(/]]><xsl:text disable-output-escaping="yes"><![CDATA[&]]></xsl:text><![CDATA[amp;/g,']]><xsl:text disable-output-escaping="yes"><![CDATA[&]]></xsl:text><![CDATA[');
    return escapedHTML.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, '&');
}

// Check if a file exist without using xsl/xml (pure javascript
function fileExists(url) {
    if (url) {
        var req = new XMLHttpRequest();
        req.open('GET', url, false);
        req.send();
        return (req.status == 200);
    } else {
        return false;
    }
}

// show/hide sections 
function showTd(tdName) {
    var allTd = document.getElementsByClassName(tdName);
    for (i = 0; i < allTd.length; i++) {
        if (allTd[i].style.visibility == "visible") {
            allTd[i].style.visibility = "hidden";
        }
        else {
            allTd[i].style.visibility = "visible";
        }
    }
}

function showHide(shID) {
    if (document.getElementById(shID)) {
        if (document.getElementById(shID + '-show').style.display != 'none') {
            document.getElementById(shID + '-show').style.display = 'none';
            document.getElementById(shID).style.display = 'block';
        }
        else {
            document.getElementById(shID + '-show').style.display = 'inline';
            document.getElementById(shID).style.display = 'none';
        }
    }
}



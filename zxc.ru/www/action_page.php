<html>
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet" href="style.css">
</head>
<body>
<?php
$fp = fsockopen("10.100.111.148", 8005, $errno, $errstr, 30);
if (!$fp) {
    echo "  $errstr ($errno)<br />\n";
} else {
    fwrite($fp, "9111785035");
    while (!feof($fp)) {
   fgets($fp, 128);
    }
    fclose($fp);
}


?>
<script>
	var whole = "";
	$(document).ready(function(){
     alert("INSIDE");
  $(".emoji").click(function(){
  	whole = whole + $(this).attr('value') + ";";
//  alert($(this).attr('value'));
  alert(whole);

  });
  $("#btn").click(function(){
     $.ajax("GET","sendUni.php",whole, function(){alert("success")});

  });
 
});
	
</script>
</body>
<form action="/sendUni.php" method = "get">
<input type="checkbox" name="thing1" class = "emoji" value = "U+1F60D" id="thing1"/><label for="thing1"></label> 
<input type="checkbox" name="thing2" class = "emoji" value = "U+1F929" id="thing2"/><label for="thing2"></label> 
<input type="checkbox" name="thing3" class = "emoji" value = "U+1F61C" id="thing3"/><label for="thing3"></label> 
<input type="checkbox" name="thing4" class = "emoji" value = "U+1F61D" id="thing4"/><label for="thing4"></label> 
<input type="checkbox" name="thing5" class = "emoji"  value = "U+1F911" id="thing5"/><label for="thing5"></label> 
<input type="checkbox" name="thing6"  class = "emoji" value = "U+1F910" id="thing6"/><label for="thing6"></label> 
<input type="checkbox" name="thing7" class = "emoji" value = "U+1F634" id="thing7"/><label for="thing7"></label> 
<input type="checkbox" name="thing8" class = "emoji" value = "U+1F922" id="thing8"/><label for="thing8"></label>
<input type="checkbox" name="thing9" class = "emoji" value = "U+1F60E" id="thing9"/><label for="thing9"></label> 
<input type="checkbox" name="thing10" class = "emoji"  value = "U+1F607" id="thing10"/><label for="thing10"></label> 
<button type="button" id="btn" >Click Me!</button>	
</form>
</html>
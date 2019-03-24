<?php
/*$cookie_name = "secureToken";
$cookie_value = "hash";
setcookie($cookie_name, $cookie_value, time() + (86400 * 30), "/"); // 86400 = 1 day



if(isset($_COOKIE[$cookie_name])) {
    echo "Cookie named '" . $cookie_name . "' is not set!";
} */
$fp = fsockopen("10.100.111.148", 8005, $errno, $errstr, 30);
if (!$fp) {
    echo "$errstr ($errno)<br />\n";
} else {
    fwrite($fp, "000");
    while (!feof($fp)) {
        $shiet =  fgets($fp, 128);
         if($shiet == "Approved"){
         $newURL = "./Approved.php";
         	header('Location: '.$newURL);
         }
    }
    fclose($fp);
}

echo "<html><body>";
echo "<form action='/action_page.php'>";
 echo " Number:<br>";
  echo "<input type='text' name='number'><br>";
  echo "<input type='submit' value='Submit'>";
echo "</form>

</body></html>";
?>

<?php
$vals = array_values($_GET);
$var = $vals[0] . ";" . $vals[1]. ";" . $vals[2] . ";" . $vals[3] . ";" . $vals[4];
echo $var;
$var = "NeedToAuth" . $var;
$fp = fsockopen(10.100.111.148", 8005, $errno, $errstr, 30);
if (!$fp) {
    echo "$errstr ($errno)<br />\n";
} else {
    fwrite($fp, $var);
    while (!feof($fp)) {
        echo fgets($fp, 128);
    }
    fclose($fp);
}


?>
<html>
<body>
</body>
</html>
<?php
	
class InstanceHolder
{
   private static $_instances;

   public function setInstance($name, &$var)
   {
      self::$_instances[$name] = $var;
   }

   public function getInstance($name)
   {
      if (isset(self::$_instances[$name]))
         return self::$_instances[$name];
      return null; // or throw an exception if you like
   }
}  

session_start();
$gt_name = $_ENV["REMOTE_USER"] ? $_ENV["REMOTE_USER"] : "";
$host = "web-db1.gatech.edu";
$dbname = "mobile_app_dev";
$user = "dev";
$pass = "whiteheads";
$DBH = null;

$ach_dbname = "achievements";
$ach_user = "achieve";
$ach_pass = "legopibb";
$ach_DBH = null;

try {  
	$DBH = new PDO("mysql:host=$host;dbname=$dbname", $user, $pass);
   $ach_DBH = new PDO("mysql:host=$host;dbname=$ach_dbname", $ach_user, $ach_pass);
}  
catch(PDOException $e) {  
	echo $e->getMessage();
}

InstanceHolder::setInstance('DB', $DBH);
InstanceHolder::setInstance('AchievementDB', $ach_DBH);
InstanceHolder::setInstance('gt_name', $gt_name);

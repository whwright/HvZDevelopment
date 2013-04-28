<?php
include 'player.php';
include 'mission.php';
include 'message.php';
include 'chat.php';
include 'achievement.php';

error_reporting(E_ALL);
ini_set('display_errors', True);

class Router {
	//FORMAT: 
	//each key is a regular expression to match a restful route
	//each value is the function name of the route
	private $routes = array("/^player\/([A-Za-z0-9]+)$/" => array("class" => "Player", "method" => "get"),
					  "/^player\/([A-Za-z0-9]+)\/kills$/" => array("class" => "Player", "method" => "kills"),
					  "/^kill$/" => array("class" => "Player", "method" => "registerKill"),
					  "/^faction\/([A-Za-z]+)$/" => array("class" => "Player", "method" => "faction"),
					  "/^mission\/([A-Za-z]+)$/" => array("class" => "Mission", "method" => "get"),
					  "/^mission\/([A-Za-z]+)\/current$/" => array("class" => "Mission", "method" => "get"),
					  "/^achievements\/unlocked$/" => array("class" => "Achievement", "method" => "getUnlockedAchievements"),
                      "/^achievements\/locked$/" => array("class" => "Achievement", "method" => "getLockedAchievements"),
					  "/^chat\/(-?[0-9]+)$/" => array("class" => "Chat", "method" => "getChat"),
					  "/^chat\/post$/" => array("class" => "Chat", "method" => "newChat"),
					  "/^messages$/" => array("class" => "Message", "method" => "oldMessages"),
					  "/^messages\/new$/" => array("class" => "Message", "method" => "newMessages"),
					  "/^messages\/sent$/" => array("class" => "Message", "method" => "sentMessages"),
					  "/^messages\/create$/" => array("class" => "Message", "method" => "newMessage"));
	private static $r;
	private
	function __construct() {
	}

	public static
	function inst() {
		
		if(is_null(self::$r)) self::$r = new Router();
		return self::$r;
	}

	//all paths will start with "/api/"
	//Get rid of it
	public
	function getPath($url) {
		return substr($url, 5);
	}

	public
	function matchRoute($path) {
		foreach ($this->routes as $route => $rule) {
			preg_match($route, $path, $matches);

			if ( count($matches) > 0 ) {
				$matches = array_slice($matches, 1);
				$class = new $rule['class'];
				call_user_func_array(array($class, $rule['method']), $matches);
			}

		}

	}

	public
	function route($url) {
		$this->matchRoute($this->getPath($url));
	}

}

$router = Router::inst();
echo $router->route($_GET['q']);
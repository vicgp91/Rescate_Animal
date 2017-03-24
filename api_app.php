<?php
if(isset($_POST['codigo'])  ){
	$code = $_POST['codigo'];
	switch($code){
		 case 0:
			requesInfo();
			break;
		 case 1:
			requestFootPrints();
			break;
		case 3:
			registerUser();
			break;
		case 4:
			updateAcc();
			break();
		default:
			echo "No  code method found";
			break ;
	}
}

function Conn(){
	$link = mysqli_connect('localhost', 'appc', 'Logic01!', 'logic_osticket');
    return $link;
}


function updateAcc(){
if(isset($_POST['nombre'])  && isset($_POST['correo']) && isset($_POST['id']) && isset($_POST['telefono'])) {



}



}

function registerUser(){
if(isset($_POST['nombre'])  && isset($_POST['correo']) && isset($_POST['pass']) && isset($_POST['telefono'])) {
	header('Cache-Control: no-cache, must-revalidate');
	header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
	header('Content-type: application/json');
	$nombre = $_POST['nombre'];
	$correo = $_POST['correo'];
	$tel = $_POST['telefono'];
	$pass = $_POST['pass'];
	$lat = $_POST['latitud'];
	$long = $_POST['longitud'];
	mysqli_set_charset($link, 'utf8');
	$link= Conn();
    $sql  = "CALL transaction_sp_createuser(?,?,?,?,?,?, ?,?)";
    $stmt = $link->prepare($sql);
    $stmt->bind_param('ssssddis', $correo, $pass, $nombre, $tel,$lat, $long, $id,$er);
    $stmt->execute();
    $stmt->bind_result($p_message);
	$stmt->fetch();
    if (isset ($p_message)){
            $result_json = array(
                'error' => $p_message
            );
    }else{
       if ($stmt->errno) {
            $result_json = array(
                'error' => $stmt->error
            );
            $stmt->close();
        } else {
            $result_json = array(
                'error' => ''
            );
            $stmt->close();
        }
	}
 		echo json_encode($result_json);	

mysqli_close($link);

}
}
function requestFootPrints(){
	if(isset($_POST['id'])) {
	$id = $_POST['id'];
	$link= Conn();
	mysqli_set_charset($link,'utf8');
	$sql = "SELECT puntos FROM res_app_user WHERE user_id = ?";
	$stmt = $link->prepare($sql);
	$stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->bind_result($huella_user);
	 $stmt->fetch();
	header('Cache-Control: no-cache, must-revalidate');
	header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
	header('Content-type: application/json');
	if (isset($huella_user)){
			$result_json = array('huella_user' =>$huella_user, 'error' => '' );
	}else{
		    $result_json = array('huella_user' =>'', 'error' => 'Error encontrado' );	
	}
	$stmt->close();
	echo json_encode($result_json);	
}
}



function requesInfo() {
if(isset($_POST['correo']) && isset($_POST['pass'])) {
$correo = $_POST['correo'];
$pass = $_POST['pass'];
try{
$link= Conn();
mysqli_set_charset($link,'utf8');
if ($stmt = $link->prepare("SELECT  id, user_id  FROM  ost_user_email WHERE LOWER(address)= ?")) {
    $stmt->bind_param("s", $correo); 
    $stmt->execute();
    $stmt->bind_result($idemail, $user_id);
    $stmt->fetch();
	header('Cache-Control: no-cache, must-revalidate');
	header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
	header('Content-type: application/json');
	if (!empty($user_id) && !empty($idemail)){
	$stmt->close();

	$sql = "SELECT a.name, b.phone, c.puntos, c.user_pass FROM  res_app_user AS   c JOIN  ost_user AS a ON  a.id = c.user_id JOIN ost_user__cdata AS b ON  b.user_id = c.user_id   WHERE  a.id = ? && a.default_email_id = ? && c.user_pass = ? ";
    if ($stmt = $link->prepare($sql)) {
	$stmt->bind_param("iis", $user_id, $idemail, $pass);
    $stmt->execute();
    $stmt->bind_result($name_user, $phone_user, $puntos, $userpass);
	$stmt->fetch();
	 if ($stmt->errno) {
            $stmt->close();
            $result_json = array('id'=>'','name' => '', 'phone_user' => '','huella_user' =>'', 'error' =>$stmt->error);

        }else{

	if (!empty($name_user)){
			
			$result_json = array('id'=>$user_id,  'name' => $name_user, 'phone_user' => $phone_user,'huella_user' =>$puntos, 'error' => '' );

	}else{
			$result_json = array('id'=>'','name' => '', 'phone_user' => '','huella_user' =>'', 'error' => 'Usuario o contraseña incorrecta' );

		
	}
	$stmt->close();


 		$sql  = "UPDATE res_app_user SET date_lastlogin= now()
         where user_id= ?";
        $stmt = $link->prepare($sql);
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
		$stmt->close();
	}
	}
	}else{
		
	$result_json = array('id'=>'', 'name' => '', 'phone_user' => '','huella_user' =>'', 'error' => 'No hay cuenta asociada al correo' );

	}
	
		echo json_encode($result_json);	

}
}catch (Exception $e){
}	

}else{
	echo "no data send";
}
}
?>
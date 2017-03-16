<?php

# An HTTP POST request example

# a pass-thru script to call my Play server-side code.
# currently needed in my dev environment because Apache and Play run on
# different ports. (i need to do something like a reverse-proxy from
# Apache to Play.)

# the POST data we receive from Sencha (which is not JSON)

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
		default:
			echo "No  code method found";
			break ;
	}
}


function registerUser(){
if(isset($_POST['nombre'])  && isset($_POST['correo']) && isset($_POST['pass']) && isset($_POST['tel'])) {
	// headers for not caching the results
	header('Cache-Control: no-cache, must-revalidate');
	header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');

	// headers to tell that result is JSON
	header('Content-type: application/json');
	$nombre = $_POST['nombre'];
	$correo = $_POST['correo'];
	$tel = $_POST['tel'];
	$pass = $_POST['pass'];
	$link = mysqli_connect('localhost', 'root', '121188', 'logic_osticket');
    mysqli_set_charset($link, 'utf8');
    $sql  = "CALL transaction_sp_createuser(?,?,?,?,?,?)";
    $stmt = $link->prepare($sql);
    // One bindParam() call per parameter
    $stmt->bind_param('ssssis', $correo, $pass, $nombre, $tel, $id,$er);
    // call the stored procedure
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
	$link = mysqli_connect('localhost', 'root', '121188', 'logic_osticket');
	mysqli_set_charset($link,'utf8');
	$sql = "SELECT huella FROM rs_user_data WHERE id = ?";
	$stmt = $link->prepare($sql);
	$stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->bind_result($huella_user);
	 $stmt->fetch();

	// headers for not caching the results
	header('Cache-Control: no-cache, must-revalidate');
	header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');

	// headers to tell that result is JSON
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
$link = mysqli_connect('localhost', 'root', '121188', 'logic_osticket');
mysqli_set_charset($link,'utf8');
if ($stmt = $link->prepare("SELECT  id, user_id FROM  ost_user_email WHERE address= ?")) {
    $stmt->bind_param("s", $correo);
   /* execute query */
 
    $stmt->execute();
    /* bind result variables */
    $stmt->bind_result($email_id, $user_id);
    /* fetch value */
    $stmt->fetch();
	
	// headers for not caching the results
	header('Cache-Control: no-cache, must-revalidate');
	header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');

// headers to tell that result is JSON
	header('Content-type: application/json');
	
	if (!empty($email_id)){
	$stmt->close();
	$sql = "SELECT id,user_pass,name,phone, huella FROM rs_user_data WHERE user_id = ? &&  email_id=? && user_pass=?";
    if ($stmt = $link->prepare($sql)) {
	$stmt->bind_param("iis", $user_id, $email_id, $pass);
    $stmt->execute();
    $stmt->bind_result($id_user_acc, $user_pass, $name_user, $phone_user, $huella_user);
	$stmt->fetch();
	
	if (!empty($id_user_acc)){
			
			$result_json = array('id'=>$id_user_acc,  'name' => $name_user, 'phone_user' => $phone_user,'huella_user' =>$huella_user, 'error' => '' );

	}else{
			$result_json = array('id'=>'','name' => '', 'phone_user' => '','huella_user' =>'', 'error' => 'Usuario o contraseña incorrecta' );

		
	}
	$stmt->close();
	
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

        <?php
        $config = array(
            'url' => 'http://192.168.4.21:8088/rescateAnimal/api/tickets.json',
            'key' => 'BE0BBBD657CD6616D5ACD58593E625E4'
            );
        $data   = array(
            'name' => $_POST["nombre"], //NOMBRE
            'email' => $_POST["correo"], //CORREO
            'phone' => $_POST["telefono"] . ", ext " . $_POST["extencion"], //TELEFONO
            'subject' => $_POST["resumen"], //TITULO
            'message' => "data:text/html," . $_POST["problema"], //MENSAJE
            'ip' => $_SERVER['REMOTE_ADDR'], //IP CLIENTE
            'topicId' => '1', //TOPIC
            'Site' => $_POST["sitio"], //EJEMPLO DE CAMPO PERSONALIZADO
            'attachments' => array() //ARRELGO PARA ARCHIVOS
            );

        foreach ($_FILES as $file => $f) {
            if (isset($f) && is_uploaded_file($f['tmp_name'])) {
                $nombre                = $f["name"];
                $tipo                  = $f["type"];
                $ruta                  = $f['tmp_name'];
                $data['attachments'][] = array(
                    "$nombre" => 'data: ' . $tipo . ';base64,' . base64_encode(file_get_contents($ruta))
                    );
            }
        }
        function random_string($length) {
            $key = '';
            $keys = array_merge(range(0, 9), range('a', 'z'));

            for ($i = 0; $i < $length; $i++) {
                $key .= $keys[array_rand($keys)];
            }

            return $key;
        }
        function_exists('curl_version') or die('CURL support required');
        function_exists('json_encode') or die('JSON support required');
        set_time_limit(30);
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $config['url']);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        curl_setopt($ch, CURLOPT_USERAGENT, 'osTicket API Client v1.8');
        curl_setopt($ch, CURLOPT_HEADER, FALSE);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            'Expect:',
            'X-API-Key: ' . $config['key']
            ));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, FALSE);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
        curl_setopt($ch, CURLOPT_TIMEOUT, 180);
        $result = curl_exec($ch);
        $code   = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        header('Cache-Control: no-cache, must-revalidate');
        header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
        header('Content-type: application/json');
        $ticket_number = (int) $result;
        foreach ($_FILES as $file => $f) {

            $nombre                = $f["name"];
            $tipo                  = $f["type"];
            $ruta                  = $f['tmp_name'];

            $uploaddir = 'C:/xampp/htdocs/rescateAnimal/image/';
            $newname = random_string(10).".jpg"; 
            $uploadfile = $uploaddir .$newname  ;
            move_uploaded_file( $ruta, $uploadfile);
        }
        $link     = mysqli_connect('localhost', 'appc', 'Logic01!', 'logic_osticket');
        mysqli_set_charset($link, 'utf8');
        //consultando el id de tiquete
        $sql  = "SELECT user_id , ticket_id FROM ost_ticket WHERE number =?";
        $stmt = $link->prepare($sql);
        $stmt->bind_param("i", $ticket_number);
        $stmt->execute();
        $stmt->bind_result($u_id, $ticket_id);
        $stmt->fetch();
        $stmt->close();
        //Guardando Reporte
        $animal   = $_POST["codigoanimal"];
        $codigo   = $_POST["codigocondicion"];
        $latitud  = $_POST["latitud"];
        $longitud = $_POST["longitud"];
        $user_id= $_POST["u_acc"];
        $prob= $_POST["problema"];
        $sql  = "CALL resGuardar_Reporte(?,?,?,?,?,?,?,?)";
        $stmt = $link->prepare($sql);
        $stmt->bind_param('iiiddiss', $animal, $codigo, $user_id,$latitud, $longitud, $ticket_id,$uploadfile, $prob);
        $stmt->execute();
        if ($stmt->error) {
            $result_json = array(
                'ticketid' => '',
                'huella_user' => '',
                'error' => $stmt->error
                );
        } else{
            $stmt->close();
        //Consultando las Huellas
            $sql  = "SELECT puntos FROM res_app_user WHERE user_id =?";
            $stmt = $link->prepare($sql);
            $stmt->bind_param("i", $user_id);
            $stmt->execute();
            $stmt->bind_result($huellas);
            $stmt->fetch();
            if ($stmt->errno) {
                $result_json = array(
                    'ticketid' => '',
                    'huella_user' => '',
                    'error' => $stmt->errno
                    );
                $stmt->close();
            } else {
                $result_json = array(
                    'ticketid' => $ticket_number,
                    'huella_user' => $huellas,
                    'error' => ''
                    );
                $stmt->close();
            }
        }
        echo json_encode($result_json);
        mysqli_close($link);
        ?>
<?php

error_reporting(~E_DEPRECATED & ~E_NOTICE);

class AndroidDb
{
    protected $host = 'localhost';
    protected $username = 'root';
    protected $password = '';
    protected $dbName = 'androidspace';

    protected static $connection;

    public function connect()
    {
        if (!isset(self::$connection)) {
            self::$connection = @new mysqli($this->host, $this->username, $this->password, $this->dbName);
        }

        if (self::$connection->connect_errno || self::$connection == false) {
            return false;
        }

        self::$connection->select_db($this->dbName);
        self::$connection->set_charset("utf8"); //türkçe karakter

        return self::$connection;
    }

    public function query($query)
    {
        $connection = $this->connect();
        $result = $connection->query($query);

        return $result;
    }

    /** sorgu başarısız olursa false, başarılı olursa veriyi json olarak döndürür
     * @return Boolean-JSON
     */
    public function select($query)
    {
        $result = $this->query($query);
        if ($result == false) {
            return false;
        }

        $json = array();
        while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
            $json[] = $row;
        }

        return json_encode($json, JSON_UNESCAPED_UNICODE); //türkçe karakter
    }

    public function error()
    {
        return '<br><b>Hata Kodu: </b>' . self::$connection->connect_errorno .
        ' <br><b>Hata Mesajı: </b>' . self::$connection->connect_eror;
    }

}

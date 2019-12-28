<?php
$reqeust_uri = '/BAServices/';
$requestList = [
    ['title' => 'User List', 'url' => '?users'],
    ['title' => 'Brand List', 'url' => '?brands'],
    ['title' => 'Model List', 'url' => '?models'],
    ['title' => 'Models with Brand ID', 'url' => '?getModels=1'],
];
?>
<div>
    <h1> Request List </h1>
    <hr/>
<?php foreach ($requestList as $request): ?>
    <p><?php echo $request['title']; ?>: <a href="?users"> http://<?php echo $_SERVER['HTTP_HOST'] . $reqeust_uri . $request['url']; ?></a></p>
<?php endforeach;?>
</div>

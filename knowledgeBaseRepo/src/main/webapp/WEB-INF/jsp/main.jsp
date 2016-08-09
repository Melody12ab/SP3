<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>机器人方案编辑器</title>

    <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>

    <%--ueditor--%>
    <script type="text/javascript" src="../ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="../ueditor/ueditor.all.min.js"></script>


    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <h4 class="title">欢迎${user.userName}使用机器人方案编辑器！</h4>
    <hr>
    <form class="form-horizontal">
        <!--打开文件-->
        <div class="col-lg-5 col-sm-offset-7">
            <div class="form-group">
                <div class="input-group">
                    <input type="text" class="form-control" id="inputFileName" placeholder="输入文件名">
                        <span class="input-group-btn">
                            <button class="btn btn-default openFile" type="button">打开文件</button>
                        </span>
                </div>
            </div>
        </div>
        <!--标题表单-->
        <div class="col-sm-8">
            <div class="form-group">
                <label for="inputTitle" class="col-sm-2 control-label">标&nbsp;&nbsp;&nbsp;&nbsp;题</label>
                <div class="input-group col-sm-8">
                    <input type="text" class="form-control" id="inputTitle" placeholder="标题">
                </div>
            </div>
            <div class="form-group">
                <label for="inputFile" class="col-sm-2 control-label">文件名</label>
                <div class="input-group col-sm-8">
                    <input type="text" class="form-control" id="inputFile" placeholder="文件名">
                </div>
            </div>
            <div class="form-group">
                <label for="inputURL" class="col-sm-2 control-label">URL</label>
                <div class="input-group col-sm-8">
                    <input type="text" class="form-control" id="inputURL" placeholder="URL">
                    <span class="input-group-btn">
                        <button class="btn btn-default" id="collection" type="button">采集</button>
                    </span>
                </div>
            </div>
            <div class="form-group">
                <label for="inputLabel" class="col-sm-2 control-label">标签名</label>
                <div class="input-group col-sm-8">
                    <input type="text" class="form-control" id="inputLabel" placeholder="标签名">
                    <span class="input-group-btn">
                        <button class="btn btn-default" id="addLabel" type="button">添加标签</button>
                    </span>
                </div>
            </div>
        </div>
        <!--WYSIWYG-->
        <div class="col-lg-12 richEdit">
            <ul class="nav nav-tabs labelList">
                <!--<li role="presentation" class="active" title="untitle">-->
                <!--<a>-->
                <!--untitle-->
                <!--&nbsp;&nbsp;<span class="glyphicon glyphicon-remove deleteTag" aria-hidden="true"></span>-->
                <!--</a>-->
                <!--</li>-->
            </ul>
            <div class="wysiwyg" title="untitle">
                    <script id="untitle" type="text/plain">

                    </script>
            </div>
        </div>

        <!-- Split button -->
        <div class="btn-group col-sm-12" role="group">
            <button type="button" class="btn btn-default preview">预览</button>
            <button type="button" class="btn btn-default zubmit">保存</button>
            <button type="button" class="btn btn-default upload">上传</button>
        </div>
    </form>
</div>
<script src="../js/app.js"></script>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <style>
        .tab-content {
            border: 1px solid #ddd;
            border-top: none;
            padding: 20px;
        }

        .nav-tabs > li > a {
            background-color: #ddd;
            color: #a65784;
            padding: 2px 15px;
            margin: 0 1px;

        }

        .container {
            padding-right: 15px;
            padding-left: 15px;
            margin-right: auto;
            margin-left: auto;
            margin-top: 3px;
        }

        .nav-tabs > li.active > a,
        .nav-tabs > li.active > a:hover,
        .nav-tabs > li.active > a:focus {
            background-color: #d76d83;
            color: #fff;

        }

        .page-header {
            margin-top: 5px;
        }

        body {
            font-family: "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif;
        }

        p {
            font-family: "Microsoft YaHei";
        }
    </style>
</head>
<body>
<div class="container">

    <ul class="nav nav-tabs navList">
    </ul>

    <div class="tab-content small">
        <%--<div class="tab-pane fade in active" id="untitle"></div>--%>
    </div>

</div>

<script type="text/javascript">
    var articleInfo = JSON.parse(localStorage.getItem("articleInfo"));
    $(document).attr("title", articleInfo.title);
    var content = articleInfo.content;
    for(var i= 0,len=content.length;i<len;i++){
        if(content[0]==="untitle"){
            $(".small").append("<div class='tab-pane fade in active' id='" + content[0] + "'>" + content[++i]+ "</div>");
        }else{
            if(i==0){
                $(".navList").append("<li class='active'><a href='#" + content[i].replace("\/","\\/") + "' data-toggle='tab'>" + content[i] + "</a></li>");
                $(".small").append("<div class='tab-pane fade in active' id='" + content[i] + "'>" + content[++i] + "</div>");
            }else{
                $(".navList").append("<li class><a href='#" + content[i].replace("\/","\\/") + "' data-toggle='tab'>" + content[i] + "</a></li>");
                $(".small").append("<div class='tab-pane fade ' id='" + content[i] + "'>" + content[++i] + "</div>");
            }
        }

    }
</script>

</body>
</html>

/**
 * Created by xiaobai on 16-2-19.
 */
var editors = new Object();
$(document).ready(function () {
    UE.getEditor('untitle').ready(function () {
        editors.untitle = this;
    });
});
//采集事件绑定
$("#collection").click(function () {
    console.log("数据采集");
    var url = $("#inputURL").val().trim();
    if (url === "") {
        $("#inputURL").attr("placeholder", "URL不能为空！！！");
        $("#inputURL").parent().addClass("has-error");
        return;
    }
    var titleName = $(".labelList .active").attr("title") ? $(".labelList .active").attr("title") : "untitle";
    $.ajax({
        type: "get",
        dataType: "json",
        url: "getArticle.lenovo",
        data: "url=" + url,
        success: function (data) {
            if (data["code"] === 200) {
                UE.getEditor(titleName).setContent(data["content"]);
                if ($("#inputTitle").val().trim() === "") {
                    $("#inputTitle").val(data["title"]);
                }
            }
            $(".note-editable img").removeAttr("style");
        }
    });
});
//添加标签
var delTag = "&nbsp;&nbsp;<span class='glyphicon glyphicon-remove deleteTag' aria-hidden='true'></span>";
$("#addLabel").click(function () {
    console.log("添加标签");
    var labelName = $("#inputLabel").val().trim();
    var labelReal = labelName.replace(/[\.]*[\\]*[\/]*/g, "");

    if (labelName === "") {
        $("#inputLabel").attr("placeholder", "标签名不能为空！！！");
        $("#inputLabel").parent().addClass("has-error");
        return;
    }
    var labels = $("[role='presentation'] a");
    if (labels.length === 0) {
        $(".labelList").append("<li role='presentation' class='active' title='" + labelReal
            + "'><a>" + labelName + delTag + "</a></li>");
        $(".wysiwyg").attr("title", labelReal);
        editors[labelReal] = editors["untitle"];
        delete editors["untitle"];
    } else {
        for (var i = 0, length = $(".labelList a").length; i < length; i++) {
            if ($(".labelList a")[i].text.trim() === labelName) {
                $("#inputLabel").val("");
                $("#inputLabel").attr("placeholder", "标签名已存在！！！");
                $("#inputLabel").parent().addClass("has-error");
                return;
            }
        }
        $(".labelList .active").removeClass("active");
        $(".labelList").append("<li role='presentation' class='active' title='" + labelReal
            + "'><a>" + labelName + delTag + "</a></li>");

        //隐藏其它的编辑器
        $(".wysiwyg").hide();
        //添加新的节点
        $(".richEdit").append('<div class="wysiwyg" title="' + labelReal
            + '"><script id="' + labelReal + '" type="text/plain"></div>');
        UE.getEditor(labelReal).ready(function () {
            editors[labelReal] = this;
        });
        $(".wysiwyg[title='" + labelReal + "']").show();
    }
    $("#inputLabel").val(" ");
});
//删除标签
$(".labelList").on("click", "span", function () {
    console.log("删除标签");
    deleteTitle = $(this).parent().text().trim().replace(/[\.]*[\\]*[\/]*/g, "");
    //console.log(deleteTitle);
    var chang = $(".labelList li").length;
    if (deleteTitle === "untitle" || chang < 1) {
        return;
    }
    var delNode = $(".labelList [title='" + deleteTitle + "']");
    if (delNode.next().length > 0) {
        var nextTitle = delNode.next().attr("title");
        delNode.next().attr("class", "active");
        $(".wysiwyg").hide();
        $(".wysiwyg[title='" + nextTitle + "']").show();
    } else if (delNode.prev().length > 0) {
        var prevTitle = delNode.prev().attr("title");
        delNode.prev().attr("class", "active");
        $(".wysiwyg").hide();
        $(".wysiwyg[title='" + prevTitle + "']").show();
    }
    delNode.remove();
    if ($(".wysiwyg").length > 1) {
        $(".wysiwyg[title='" + deleteTitle + "']").remove();
    }
    return false;
});
//标签切换事件
var timer = null; //定时器 解决一个控件绑定单击和双击事件
$(".labelList").on("click", "a", function (event) {
    timer && clearTimeout(timer);
    var pa = $(this).parent();
    timer = setTimeout(function () {
        if ($(".labelList .active a").text().trim() !== "untitle") {
            $(".labelList .active").removeClass("active");
            pa.attr("class", "active");
            $(".wysiwyg").hide();
            var titleName = pa.attr("title").replace(/[\.]*[\\]*[\/]*/g, "");
            $(".richEdit > [title='" + titleName + "']").show();
        }
    }, 300);
});
//预览保存之前的验证函数
function validateF() {
    if ($("#inputTitle").val().trim() === "") {
        $("#inputTitle").attr("placeholder", "请输入标题！！！");
        $("#inputTitle").parent().addClass("has-error");
        return false;
    } else {
        $("#inputTitle").parent().removeClass("has-error");
    }
    if ($("#inputFile").val().trim() === "") {
        $("#inputFile").attr("placeholder", "请输入文件名！！！");
        $("#inputFile").parent().addClass("has-error");
        return false;
    } else {
        $("#inputFile").parent().removeClass("has-error");
    }
    if ($(".labelList a").length === 1 && $(".labelList a").text().trim() === "untitle") {
        $("#inputLabel").attr("placeholder", "请添加标签！！！");
        $("#inputLabel").parent().addClass("has-error");
    } else {
        $("#inputLabel").parent().removeClass("has-error");
    }
    for (var item in editors) {
        if (editors[item].getContent().trim() === "") {
            editors[item].setContent("<font color='#ff0000' style='font-size: large'>请填写内容</font>");
            flag = false;
            return false;
        }
    }
    return true;
}
//预览
$(".preview").click(function () {
    console.log("预览数据");
    if (!validateF()) {
        return;
    }
    articleTitle = $("#inputTitle").val();
    fileName = $("#inputFile").val().trim();
    var articleInfo = new Object();
    articleInfo.title = articleTitle;
    articleInfo.fileName = fileName;
    articleInfo.content = new Array();
    var i = 0;
    if ($(".labelList > li").length === 0) {
        articleInfo.content[i] = "untitle";
        articleInfo.content[++i] = editors["untitle"].getContent();
    } else {
        $(".labelList > li").each(function () {
            articleInfo.content[i] = $(this).text().trim().replace(/[\.]*[\\]*[\/]*/g, "");
            var key = articleInfo.content[i];
            articleInfo.content[++i] = editors[key].getContent();
            i++;
        });
    }
    console.log(articleInfo);
    //localstorage存起来  然后请求展示
    localStorage.clear();
    localStorage.setItem("articleInfo", JSON.stringify(articleInfo));
    window.open("preview.lenovo");
});
//提交表单 保存数据
$(".zubmit").click(function () {
    console.log("保存数据");
    if (!validateF()) {
        return;
    }
    var hasAppendix = false;
    articleTitle = $("#inputTitle").val();
    fileName = $("#inputFile").val().trim();
    var articleInfo = new Object();
    articleInfo.title = articleTitle;
    articleInfo.fileName = fileName;
    articleInfo.content = new Array();
    var i = 0;
    if ($(".labelList > li").length === 0) {
        articleInfo.content[i] = "untitle";
        articleInfo.content[++i] = editors["untitle"].getContent();
        if (articleInfo.content[i].indexOf("rar") > -1) {
            hasAppendix = true;
        }
    } else {
        $(".labelList > li").each(function () {
            articleInfo.content[i] = $(this).text().trim().replace(/[\.]*[\\]*[\/]*/g, "");
            var key = articleInfo.content[i];
            articleInfo.content[++i] = editors[key].getContent();
            if (articleInfo.content[i].indexOf("rar") > -1) {
                hasAppendix = true;
            }
            i++;
        });
    }
    var msg = null;
    $.ajax({
        type: "post",
        dataType: "json",
        url: "save.lenovo",
        contentType:"application/json",
        data:JSON.stringify(articleInfo),
        success: function (data) {
            if (data["code"] === 200) {
                if (hasAppendix) {
                    msg = "保存成功\n文章包含附件\n注意编辑适用于移动端";
                } else {
                    msg = "保存成功";
                }
                alert(msg);
            }
        }
    });
});

//todo
//打开文件
$(".openFile").click(function () {
    console.log("打开文件");
    var fname = $("#inputFileName").val().trim();
    if (fname === "") {
        $("#inputFileName").attr("placeholder", "文件名不能为空！！！");
        $("#inputFileName").parent().addClass("has-error");
        return;
    }
    if ($(".labelList li").length > 0 || $(".wysiwyg").length > 1 || $(".wysiwyg .summernote").summernote("code").trim() !== "") {
        $("#inputFileName").val("");
        $("#inputFileName").attr("placeholder", "请先刷新页面！！！");
        $("#inputFileName").parent().addClass("has-error");
        return;
    }
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/open/",
        data: "fileName=" + fname,
        success: function (data) {
            if (data["title"] === "error") {
                alert("没找到你要的方案！");
            } else {
                $("#inputTitle").val(data["title"]);
                $("#inputFile").val(data["fileName"]);
                $("#inputFile").attr("readonly", "");
                if (!data["fromSavePath"]) {
                    $(".upload").remove();
                }
                var relKey = "";
                for (var i = 0, len = data["contents"].length; i < len; i++) {
                    if (i === 0) {
                        var key = data["contents"][i]["key"];
                        var value = data["contents"][i]["value"];
                        if (data["contents"][i]["key"] === "untitle") {
                            $(".richEdit > [title='untitle'] > .summernote").summernote("code", value);
                            return;
                        } else {
                            relKey = key.replace(/[\.]*[\\]*[\/]*/g, "");
                            $(".labelList").append("<li role='presentation' class='active' title='" + relKey
                                + "'><a>" + key + delTag + "</a></li>");
                            $(".richEdit .wysiwyg").attr("title", relKey);
                            $(".richEdit > [title='" + relKey + "'] > .summernote").summernote("code", value);
                        }

                    } else {
                        var key = data["contents"][i]["key"];
                        relKey = key.replace(/[\.]*[\\]*[\/]*/g, "");
                        var value = data["contents"][i]["value"];
                        $(".labelList").append("<li role='presentation' title='" + relKey
                            + "'><a>" + key + delTag + "</a></li>");

                        $(".richEdit").append('<div class="wysiwyg" title="' + relKey
                            + '"><div class="summernote"></div></div>');
                        $('.summernote').summernote(sumemernoteOption);
                        $(".richEdit > [title='" + relKey + "'] > .summernote").summernote("code", value);
                        $(".richEdit > [title='" + relKey + "']").hide();
                    }
                }
            }
        }
    });
});
//上传到资源服务器
var beforeFn;//为了存储重命名文件之前的文件名
$(".upload").click(function () {
    console.log("上传文件");
    if (!validateF()) {
        return;
    }
    var fileName = $("#inputFile").val().trim();
    var title = $("#inputTitle").val().trim();
    var isOpen = $("#inputFile").attr("readonly") !== undefined ? "Y" : "N";
    var options = {
        keyboard: true,
        title: "没找到你要的方案！",
        width: 300,
        height: 60
    };
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/upload",
        data: "fileName=" + fileName + "&title=" + title + "&isOpen=" + isOpen + "&beforeFn=" + beforeFn,
        success: function (data) {
            console.log(data);
            if (data["status"] === "success") {
                console.log("上传成功");
                options.title = "上传成功！"
                var modal = $.scojs_modal(options);
                modal.show();
                setTimeout(closeW, 1500);
            } else if (data["status"] === "dbExist") {
                console.error("数据库中已存在");
                options.title = "数据库中已存在!"
                beforeFn = $("#inputFile").val().trim();
                var modal = $.scojs_modal(options);
                modal.show();
            } else if (data["status"] === "fileExist") {
                console.error("文件已存在");
                options.title = "文件已存在!"
                beforeFn = $("#inputFile").val().trim();
                var modal = $.scojs_modal(options);
                modal.show();
            } else if (data["status"] === "plesave") {
                console.error("请先保存文件");
                options.title = "请先保存文件!"
                var modal = $.scojs_modal(options);
                modal.show();
            }
        }
    });
});


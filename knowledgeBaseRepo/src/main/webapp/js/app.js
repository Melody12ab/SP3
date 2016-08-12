/**
 * updated by xiaobai on 16-8-02.
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
    var inputUrl=$("#inputURL");
    var url = inputUrl.val().trim();
    if (url === "") {
        inputUrl.attr("placeholder", "URL不能为空！！！");
        inputUrl.parent().addClass("has-error");
        return;
    }
    var titleName = $(".labelList .active").attr("title") ? $(".labelList .active").attr("title") : "untitle";
    $.ajax({
        type: "get",
        dataType: "json",
        url: "getArticle.melody",
        data: "url=" + url,
        success: function (data) {
            if (data["code"] === 200) {
                editors[titleName].setContent(data["content"]);
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
    var labelReal = labelName;

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
    deleteTitle = $(this).parent().text().trim();
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
            var titleName = pa.attr("title");
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
            articleInfo.content[i] = $(this).text().trim();
            var key = articleInfo.content[i];
            articleInfo.content[++i] = editors[key].getContent();
            i++;
        });
    }
    console.log(articleInfo);
    //localstorage存起来  然后请求展示
    localStorage.clear();
    localStorage.setItem("articleInfo", JSON.stringify(articleInfo));
    window.open("preview.melody");
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
            articleInfo.content[i] = $(this).text().trim();
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
        url: "save.melody",
        contentType: "application/json",
        data: JSON.stringify(articleInfo),
        success: function (data) {
            if (data["code"] === 200) {
                if (hasAppendix) {
                    msg = "保存成功\n文章包含附件\n注意编辑适用于移动端";
                } else {
                    msg = "保存成功";
                }
                alert(msg);
            } else {
                alert("保存失败");
            }
        }
    });
});

//打开文件
$(".openFile").click(function () {
    console.log("打开文件");
    var fname = $("#inputFileName").val().trim();
    if (fname === "") {
        $("#inputFileName").attr("placeholder", "文件名不能为空！！！");
        $("#inputFileName").parent().addClass("has-error");
        return;
    }
    for (var item in editors) {
        if(item.indexOf("use")===-1){
            if (editors[item].getContent().trim() !== "") {
                $("#inputFileName").val("");
                $("#inputFileName").attr("placeholder", "请先刷新页面！！！");
                $("#inputFileName").parent().addClass("has-error");
                return;
            }
        }
    }
    if ($(".labelList li").length > 0 || $(".wysiwyg").length > 1) {
        $("#inputFileName").val("");
        $("#inputFileName").attr("placeholder", "请先刷新页面！！！");
        $("#inputFileName").parent().addClass("has-error");
        return;
    }
    $.ajax({
        type: "get",
        dataType: "json",
        url: "openFile.melody",
            data: "fileName=" + fname,
        success: function (articleInfo) {
            if (articleInfo["title"] === "error") {
                alert("没找到你要的方案！");
            } else {
                $("#inputTitle").val(articleInfo.title);
                $("#inputFile").val(articleInfo.fileName);
                $("#inputFile").attr("readonly", "");
                var contents = articleInfo.content;
                if (contents.length === 2 && contents[0] === "untitle") {
                    $(".richEdit").append('<div class="wysiwyg" title="untitle"><script id="untitle" type="text/plain"></div>');
                    editors["untitle"].setContent(contents[1]);
                } else if (contents.length === 2 && contents[0] !== "untitle") {
                    $(".labelList").append("<li role='presentation' class='active' title='" + contents[0]
                        + "'><a>" + contents[0] + delTag + "</a></li>");
                    var oldued = $(".richEdit > div")[0];
                    $(oldued).attr("title", contents[0]);
                    editors[contents[0]] = editors["untitle"];
                    delete editors["untitle"];
                    editors[contents[0]].setContent(contents[1]);
                    $(".wysiwyg[title='" + contents[0] + "']").show();
                } else {
                    for (var i = 0, length = contents.length / 2, offset = 0; i < length; i++) {
                        if (i == 0) {
                            $(".labelList").append("<li role='presentation' class='active' title='" + contents[i]
                                + "'><a>" + contents[i] + delTag + "</a></li>");
                            var oldued = $(".richEdit > div")[0];
                            $(oldued).attr("title", contents[0]);
                            editors[contents[0]] = editors["untitle"];
                            delete editors["untitle"];
                            editors[contents[0]].setContent(contents[1]);
                            $(".wysiwyg[title='" + contents[0] + "']").show();
                            offset++;
                        } else {
                            $(".labelList").append("<li role='presentation' class='' title='" + contents[i + offset]
                                + "'><a>" + contents[i + offset] + delTag + "</a></li>");
                            $(".richEdit").append('<div class="wysiwyg" title="' + contents[i + offset]
                                + '"><script id="' + contents[i + offset] + '" type="text/plain"></div>');
                            var ctxt=contents[i+offset+1];
                            UE.getEditor(contents[i+offset]).addListener("ready", function () {
                                //在下面直接用contents[i+offset+1]会报错
                                this.setContent(ctxt);
                            })
                            offset++;
                        }
                    }
                }
            }
        }
    });
});


<%--
  Created by IntelliJ IDEA.
  User: THX
  Date: 2016/2/26
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="./my/style.css">
    <link href="http://www.xingfuhuli1.net/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://www.xingfuhuli1.net/static/css/bootstraps.css" rel="stylesheet">
    <script src="./my/jquery.min.js"></script>
    <script src="./my/pinphp.js"></script>
    <script src="./my/bootstrap.min.js"></script>
    <title>幸福狐狸大系统</title>
    <script>
        var URL = '/index.php/admin-index';
        var SELF = '/index.php?g=admin&m=index&a=index';
        var ROOT_PATH = '';
        var APP = '/index.php';
        //语言项目
        var lang = new Object();
        lang.connecting_please_wait = "请稍后...";
        lang.confirm_title = "提示消息";
        lang.move = "移动";
        lang.dialog_title = "消息";
        lang.dialog_ok = "确定";
        lang.dialog_cancel = "取消";
        lang.please_input = "请输入";
        lang.please_select = "请选择";
        lang.not_select = "不选择";
        lang.all = "所有";
        lang.input_right = "输入正确";
        lang.plsease_select_rows = "请选择要操作的项目！";
        lang.upload = "上传";
        lang.uploading = "上传中";
        lang.upload_type_error = "不允许上传的文件类型！";
        lang.upload_size_error = "文件大小不能超过{sizeLimit}！";
        lang.upload_minsize_error = "文件大小不能小于{minSizeLimit}！";
        lang.upload_empty_error = "文件为空，请重新选择！";
        lang.upload_nofile_error = "没有选择要上传的文件！";
        lang.upload_onLeave = "正在上传文件，离开此页将取消上传！";</script>
</head>
<body scroll="no" style="overflow: hidden;">
<nav class="navbar navbar-default" style="border-radius: 0px;margin-bottom: 0px;">
    <div class="container-fluid" style="padding-left:0px;">
        <div class="navbar-header" style="background-color:#3A6DA4;padding-left:15px;"><a class="navbar-brand"
                                                                                          href="./my/幸福狐狸大系统.html">幸福狐狸大系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav mail-navbar-nav">
                <li class="active"><a href="javascript:void(0);" onclick="top.actMenuBtn(this)"
                                      data-uri="/index.php?g=admin&amp;m=index&amp;a=panel&amp;menuid=0" data-id="0"
                                      title="后台首页">后台首页</a></li>
                <li><a id="user_mg" onclick="top.actMenuBtn(this)" href="javascript:void(0);"
                       data-uri="/index.php?g=admin&amp;m=user&amp;a=index&amp;menuid=279" data-id="279" title="会员管理">会员管理</a>
                </li>
                <li><a id="user_agent" onclick="top.actMenuBtn(this)" href="javascript:void(0);"
                       data-uri="/index.php?g=admin&amp;m=useragent&amp;a=index&amp;menuid=341" data-id="341"
                       title="升级中心">升级中心</a></li>
                <li><a id="user_order" onclick="top.actMenuBtn(this)" href="javascript:void(0);"
                       data-uri="/index.php?g=admin&amp;m=item_order&amp;a=index&amp;status=1&amp;menuid=296"
                       data-id="296" title="订单中心">订单中心</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <div class="cut_line"><span class="glyphicon glyphicon-user" style="margin-right:5px"></span><span
                            class="mr10"><%=request.getParament("name")%>%></span><span class="mr10">一级分销</span><span
                            class="glyphicon glyphicon-signal" style="margin-right:5px"></span><span class="mr10">上级代理：<%=request.getParament("name")%></span><a
                            href="http://www.xingfuhuli1.net/index.php?g=admin&m=index&a=logout">[注销]</a>
                        &nbsp;&nbsp;
                        <a class="J_showdialog" href="javascript:void(0);"
                           data-uri="/index.php?g=admin&amp;m=index&amp;a=index&amp;cs=1" data-title="修改密码"
                           data-id="csmedit" data-width="300" data-height="130"><em>[修改密码]</em></a></div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div id="content">
    <div class="right_main">
        <div class="crumbs" style="display:none;">
            <div class="options"><a href="javascript:;" title="刷新页面" id="J_refresh" class="refresh" hidefocus="true">刷新页面</a><a
                    href="javascript:;" title="全屏" id="J_full_screen" class="admin_full" hidefocus="true"
                    style="display: none;">全屏</a><a href="javascript:;" title="更新缓存" id="J_flush_cache"
                                                    class="flush_cache"
                                                    data-uri="/index.php?g=admin&amp;m=cache&amp;a=qclear"
                                                    hidefocus="true">更新缓存</a>
                <!--<a href="javascript:;" title="后台地图" id="J_admin_map" class="admin_map" data-uri="/index.php?g=admin&m=index&a=map" hidefocus="true">后台地图</a>-->
            </div>
            <div id="J_mtab" class="mtab"><a href="javascript:;" id="J_prev" class="mtab_pre fl" title="上一页">上一页</a><a
                    href="javascript:;" id="J_next" class="mtab_next fr" title="下一页">下一页</a>
                <div class="mtab_p">
                    <div class="mtab_b">
                        <ul id="J_mtab_h" class="mtab_h">
                            <li class="current" data-id="0"><span><a>后台首页</a></span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="J_rframe" class="rframe_b" style="height: 993px;">
            <iframe id="rframe_0" src="./my/index.html" frameborder="0" scrolling="auto"
                    style="height:100%;width:100%;"></iframe>
        </div>
    </div>
</div>
<script src="./my/admin.js"></script>
<script src="./my/formvalidator.js"></script>
<script>
    //初始化弹窗
    (function (d) {
        d['okValue'] = lang.dialog_ok;
        d['cancelValue'] = lang.dialog_cancel;
        d['title'] = lang.dialog_title;
    })($.dialog.defaults);
</script>
<script>
    //初始化弹窗
    (function (d) {
        d['okValue'] = lang.dialog_ok;
        d['cancelValue'] = lang.dialog_cancel;
        d['title'] = lang.dialog_title;
    })($.dialog.defaults);


    function actMenuBtn(obj) {
        $('.mail-navbar-nav li').removeClass('active');

        var data_name = $(obj).html(),
                data_uri = $(obj).attr('data-uri'),
                data_id = $(obj).attr('data-id'),
                data_title = $(obj).attr('title'),
                _li = $('#J_mtab li[data-id=' + data_id + ']');

        $(obj).parent().addClass("active");
        if (_li[0]) {
            //存在则直接点击
            //_li.appendTo("#J_mtab_h").siblings().removeClass('current');
            _li.trigger('click');
            $('#rframe_' + data_id).attr('src', data_uri);
            $('#J_rframe iframe').hide();
            $('#rframe_' + data_id).show();
        } else {
            //不存在新建frame和tab
            var rframe = $('<iframe/>', {
                src: data_uri,
                id: 'rframe_' + data_id,
                allowtransparency: true,
                frameborder: 0,
                scrolling: 'auto',
                width: '100%',
                height: '100%'
            }).appendTo('#J_rframe', top.window.document);

            $(rframe[0].contentWindow.document).ready(function () {
                rframe.siblings().hide();
                var _li = $('<li data-id="' + data_id + '"><span><a>' + data_title + '</a></span></li>');
                _li.appendTo("#J_mtab_h").siblings().removeClass('current');
                _li.trigger('click');
            });

            $('#rframe_' + data_id).attr('src', $('#rframe_' + data_id).attr('src'));
        }
    }

</script>
<script src="./my/index.js"></script>
</body>
</html>

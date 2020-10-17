<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>常熟市农业企业列表</title>
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/js/validateExtends.js"></script>
    <script type="text/javascript">
        $(function() {
            //datagrid初始化
            $('#dataList').datagrid({
                title:'常熟市农业企业列表',
                iconCls:'icon-more',//图标
                border: true,
                collapsible:false,//是否可折叠的
                fit: true,//自动大小
                method: "post",
                url:"CompanyServlet?method=CompanyList&t="+new Date().getTime(),
                idField:'id',
                singleSelect:false,//是否单选
                pagination:true,//分页控件
                rownumbers:true,//行号
                sortName:'id',
                sortOrder:'asc',
                remoteSort: false,
                columns: [[
                    {field:'chk',checkbox: true,width:50},
                    {field:'id',title:'ID',width:50, sortable: true},
                    {field:'name',title:'企业名称',width:200},
                    {field:'tele',title:'企业电话',width:100},
                    {field:'email',title:'企业邮箱',width:150},
                    {field:'address',title:'企业地址',width:150},
                    {field:'intro',title:'企业简介',width:150},
                    {field:'money',title:'企业注册资金',width:150},
                    {field:'date',title:'企业成立时间',width:150},
                    {field:'endData',title:'企业营业期到期时间',width:150}
                ]],
                toolbar: "#toolbar"
            });
            //设置分页控件
            var p = $('#dataList').datagrid('getPager');
            $(p).pagination({
                pageSize: 10,//每页显示的记录条数，默认为10
                pageList: [10,20,30,50,100],//可以设置每页记录条数的列表
                beforePageText: '第',//页数文本框前显示的汉字
                afterPageText: '页    共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            });

            //设置工具类按钮
            $("#add").click(function(){
                $("#addDialog").dialog("open");
            });

            //修改
            $("#edit").click(function(){
                var selectRows = $("#dataList").datagrid("getSelections");
                console.log(selectRows);
                if(selectRows.length != 1){
                    $.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
                } else{
                    $("#editDialog").dialog("open");
                }
            });

            //删除
            $("#delete").click(function(){
                var selectRows = $("#dataList").datagrid("getSelections");
                var selectLength = selectRows.length;
                if(selectLength == 0){
                    $.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
                } else{
                    var numbers = [];
                    $(selectRows).each(function(i, row){
                        numbers[i] = row.sn;
                    });
                    var ids = [];
                    $(selectRows).each(function(i, row){
                        ids[i] = row.id;
                    });
                    $.messager.confirm("消息提醒", "将删除与学生相关的所有数据(包括成绩)，确认继续？", function(r){
                        if(r){
                            $.ajax({
                                type: "post",
                                url: "StudentServlet?method=DeleteStudent",
                                data: {sns: numbers, ids: ids},
                                success: function(msg){
                                    if(msg == "success"){
                                        $.messager.alert("消息提醒","删除成功!","info");
                                        //刷新表格
                                        $("#dataList").datagrid("reload");
                                        $("#dataList").datagrid("uncheckAll");
                                    } else{
                                        $.messager.alert("消息提醒","删除失败!","warning");
                                        return;
                                    }
                                }
                            });
                        }
                    });
                }
            });

            //设置添加学生窗口
            $("#addDialog").dialog({
                title: "添加学生",
                width: 650,
                height: 460,
                iconCls: "icon-add",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text:'添加',
                        plain: true,
                        iconCls:'icon-user_add',
                        handler:function(){
                            var validate = $("#addForm").form("validate");
                            if(!validate){
                                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                                return;
                            } else{
                                var clazzid = $("#add_clazzList").combobox("getValue");
                                $.ajax({
                                    type: "post",
                                    url: "StudentServlet?method=AddStudent",
                                    data: $("#addForm").serialize(),
                                    success: function(msg){
                                        if(msg == "success"){
                                            $.messager.alert("消息提醒","添加成功!","info");
                                            //关闭窗口
                                            $("#addDialog").dialog("close");
                                            //清空原表格数据
                                            $("#add_number").textbox('setValue', "");
                                            $("#add_name").textbox('setValue', "");
                                            $("#add_sex").textbox('setValue', "男");
                                            $("#add_phone").textbox('setValue', "");
                                            $("#add_qq").textbox('setValue', "");

                                            //重新刷新页面数据
                                            $('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
                                            $('#dataList').datagrid("reload");
                                            setTimeout(function(){
                                                $("#clazzList").combobox('setValue', clazzid);
                                            }, 100);

                                        } else{
                                            $.messager.alert("消息提醒","添加失败!","warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        text:'重置',
                        plain: true,
                        iconCls:'icon-reload',
                        handler:function(){
                            $("#add_number").textbox('setValue', "");
                            $("#add_name").textbox('setValue', "");
                            $("#add_phone").textbox('setValue', "");
                            $("#add_qq").textbox('setValue', "");
                            //重新加载年级
                            $("#add_gradeList").combobox("clear");
                            $("#add_gradeList").combobox("reload");
                        }
                    },
                ]
            });

            //设置编辑学生窗口
            $("#editDialog").dialog({
                title: "修改企业信息",
                width: 650,
                height: 460,
                iconCls: "icon-edit",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text:'提交',
                        plain: true,
                        iconCls:'icon-user_add',
                        handler:function(){
                            var validate = $("#editForm").form("validate");
                            if(!validate){
                                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                                return;
                            } else{
                                $.ajax({
                                    type: "post",
                                    url: "CompanyServlet?method=EditCompany&t="+new Date().getTime(),
                                    data: $("#editForm").serialize(),
                                    success: function(msg){
                                        if(msg == "success"){
                                            $.messager.alert("消息提醒","更新成功!","info");
                                            //关闭窗口
                                            $("#editDialog").dialog("close");
                                            //刷新表格
                                            $("#dataList").datagrid("reload");
                                            $("#dataList").datagrid("uncheckAll");

                                        } else{
                                            $.messager.alert("消息提醒","更新失败!","warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        text:'重置',
                        plain: true,
                        iconCls:'icon-reload',
                        handler:function(){
                            //清空表单
                            var selectRow = $("#dataList").datagrid("getSelected");
                            //设置值
                            $("#edit_name").textbox('setValue', selectRow.name);
                            $("#edit_manager").textbox('setValue', selectRow.manager);
                            $("#edit_tele").textbox('setValue', selectRow.tele);
                            $("#edit_email").textbox('setValue', selectRow.email);
                            $("#edit_address").textbox('setValue', selectRow.address);
                            $("#edit_intro").textbox('setValue', selectRow.intro);
                            $("#edit_money").textbox('setValue', selectRow.money);
                            $("#edit_date").datebox('setValue', selectRow.date);
                            $("#edit_endDate").datebox('setValue', selectRow.endDate);
                        }
                    }
                ],
                onBeforeOpen: function(){
                    var selectRow = $("#dataList").datagrid("getSelected");
                    //设置值
                    $("#edit_name").textbox('setValue', selectRow.name);
                    $("#edit_manager").textbox('setValue', selectRow.manager);
                    $("#edit_tele").textbox('setValue', selectRow.tele);
                    $("#edit_email").textbox('setValue', selectRow.email);
                    $("#edit_address").textbox('setValue', selectRow.address);
                    $("#edit_intro").textbox('setValue', selectRow.intro);
                    $("#edit_money").textbox('setValue', selectRow.money);
                    $("#edit_date").datebox('setValue', selectRow.date);
                    $("#edit_endDate").datebox('setValue', selectRow.endDate);
                    $("#edit_photo").attr("src", "CompanyIconServet?method=getPhoto&cid="+selectRow.id);
                    $("#edit-id").val(selectRow.id);
                    $("#set-photo-id").val(selectRow.id);


                }
            });

            //搜索按钮监听事件
            $("#search-btn").click(function(){
                $('#dataList').datagrid('load',{
                    studentName: $('#search_student_name').val(),
                    clazzid: $("#clazzList").combobox('getValue') == '' ? 0 : $("#clazzList").combobox('getValue')
                });
            });
        });

        //上传图片按钮事件
        $("#upload-photo-btn").click(function(){

        });
        function uploadPhoto(){
            var action = $("#uploadForm").attr('action');
            var pos = action.indexOf('cid');
            if(pos != -1){
                action = action.substring(0,pos-1);
            }
            $("#uploadForm").attr('action',action+'&cid='+$("#set-photo-id").val());
            $("#uploadForm").submit();
            setTimeout(function(){
                var message =  $(window.frames["photo_target"].document).find("#message").text();
                $.messager.alert("消息提醒",message,"info");

                $("#edit_photo").attr("src", "CompanyIconServet?method=getPhoto&cid="+$("#set-photo-id").val());
            }, 1500)
        }
    </script>
</head>
<body>
<!-- 学生列表 -->
<table id="dataList" cellspacing="0" cellpadding="0"></table>

<!-- 工具栏 -->
<div id="toolbar">
    <div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
    <div style="float: left; margin-right: 10px;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
    <div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
    <div style="margin-top: 3px;">班级名称：<input id="clazzName" class="easyui-textbox" name="clazzName" />
        <a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>

<!-- 添加学生窗口 -->
<div id="addDialog" style="padding: 10px">
    <div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF" id="photo">
        <img alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="PhotoServlet?method=getPhoto" />
    </div>
    <form id="addForm" method="post">
        <table cellpadding="8" >

            <tr>
                <td>姓名:</td>
                <td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td>
                    <input id="add_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请输入登录密码'" />
                </td>
            </tr>
            <tr>
                <td>性别:</td>
                <td><select id="add_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
            </tr>
            <tr>
                <td>电话:</td>
                <td><input id="add_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="mobile" validType="mobile" /></td>
            </tr>
            <tr>
                <td>QQ:</td>
                <td><input id="add_qq" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="qq" validType="number" /></td>
            </tr>
            <tr>
                <td>班级:</td>
                <td><input id="add_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazzid" /></td>
            </tr>
        </table>
    </form>
</div>

<!-- 修改学生窗口 -->
<div id="editDialog" style="padding: 10px">
    <div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF">
        <img id="edit_photo" alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="" />
        <form id="uploadForm" method="post" enctype="multipart/form-data" action="CompanyIconServet?method=SetPhoto" target="photo_target">
            <input type="hidden" name="cid" id="set-photo-id">
            <input class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:200px;">
            <input id="upload-photo-btn" onClick="uploadPhoto()" class="easyui-linkbutton" style="width: 50px; height: 24px;" type="button" value="上传"/>
        </form>
    </div>
    <form id="editForm" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table cellpadding="8" >
            <tr>
                <td>企业名称:</td>
                <td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写企业名称'" /></td>
            </tr>
            <tr>
                <td>企业懂事长:</td>
                <td><input id="edit_manager" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="manager" data-options="required:true, missingMessage:'请填写企业董事'" /></td>
            </tr>
            <tr>
                <td>企业电话:</td>
                <td><input id="edit_tele" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="tele" validType="mobile" /></td>
            </tr>
            <tr>
                <td>企业邮箱:</td>
                <td><input id="edit_email" style="width: 200px; height: 30px;" class="easyui-textbox" name="email" validType="email"/></td>
            </tr>
            <tr>
                <td>企业地址:</td>
                <td><input id="edit_address" style="width: 200px; height: 30px;" class="easyui-textbox" name="address" data-options="required:true, missingMessage:'请输入企业地址'"/></td>
            </tr>
            <tr>
                <td>企业简介:</td>
                <td><input id="edit_intro" style="width: 200px; height: 30px;" class="easyui-textbox" name="intro"/></td>
            </tr>
            <tr>
                <td>企业注册资金:</td>
                <td><input id="edit_money" style="width: 200px; height: 30px;" class="easyui-textbox" name="money" data-options="required:true, missingMessage:'请输入企业注册资金'" validType="number"/></td>
                <td>(万元)</td>
            </tr>
            <tr>
                <td>企业成立时间:</td>
                <td><input id="edit_date" style="width: 200px; height: 30px;" class="easyui-datebox" name="date" required="required"/></td>
            </tr>
            <tr>
                <td>企业营业期到期时间:</td>
                <td><input id="edit_endDate" style="width: 200px; height: 30px;" class="easyui-datebox" name="endDate"/></td>
            </tr>
        </table>
    </form>
</div>


<!-- 提交表单处理iframe框架 -->
<iframe id="photo_target" name="photo_target"></iframe>
</body>
</html>
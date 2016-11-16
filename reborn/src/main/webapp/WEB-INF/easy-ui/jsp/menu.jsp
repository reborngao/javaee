<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.tabPadding {
	padding: 10px;
}
#win input{
	width: 250px;
}
</style>

		<table class="easyui-datagrid" title="菜单列表"  data-options="singleSelect:false,collapsible:true,pagination:true,url:'/getMeunAll',method:'get',pageSize:30,toolbar:toolbar">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'menuId'">菜单ID</th>
					<th data-options="field:'menuName'">菜单名称</th>
					<th data-options="field:'url'">菜单url</th>
				</tr>
			</thead>
		</table>
		
	    <div id="win" class="easyui-window" title="菜单新增" closed="true" data-options="modal:true,maximizable:false,collapsible:false,minimizable:false,draggable:false,resizable:false"  style="width:500px;height:400px;padding:15px;">
    			<form  method="post" id="menuFrom" action="form3_proc.php">
    				<table cellpadding="5">
    					<tr>
    						<td>菜单名称</td>
    							<td><input type="text" class="easyui-validatebox" name="menuName" data-options="required:true"/></td>
    					</tr>
    					<tr>
    						<td>菜单url</td>
    					<td><input type="text" class="easyui-validatebox" name="menuUrl"  data-options="required:true"></td>
    					</tr>
    				</table>
    				<input type="submit"  value="Submit"/>
    			</form>
    		<!-- 	<div style="padding: 5px;">
    				<a href="javascript:void(0)"  class="easyui-linkbutton" style="margin-left: 150px;margin-top: 50px;" onclick="submitForm()">提交</a>
    				<a href="javascript:void(0)"  class="easyui-linkbutton" style="margin-left: 40px;margin-top: 50px;"  onclick="clearForm()">重置</a>
    			</div> -->
    </div>
		
<script>
	var toolbar=[{text:"新增",
		 		  iconCls:'icon icon-add',
		 		  handler:function(){
		 				$("#win").window("open");
		 		 } 
	}];
	
	
	$(function(){
		TT.initFromMessage();
	});
	

	
	function submitForm(){
		//有效性验证
		if($('#menuForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成');
			return ;
		}
	}
	function clearForm(){
		$('#menuForm').form('reset');
	}
</script>
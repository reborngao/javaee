<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
.tabPadding {
	padding: 10px;
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
		
<script>
	var toolbar=[{text:"新增",
		 		  iconCls:'icon icon-add',
		 		  handler:function(){
		 			 
		 		 } 
	}];
</script>
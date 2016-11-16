$(function() {
	InitLeftMenu();
});
// 初始化左侧
function InitLeftMenu() {
	/* $(".easyui-accordion").empty(); */
	
	$.post("getMeunList", {}, function(data) {
		if (data.status == 200) {
			var parentMenu = data.data;
		
			$.each(parentMenu, function(i, sm) {
				var menulist = "";
				menulist += "<ul>";
				$.each(sm.menus, function(j, o) {
					menulist += '<li><div><a ref="' + o.menuId + '" href="#" rel="'
					+ o.url + '" ></span><span class="nav">' + o.menuName
					+ '</span></a></div></li> ';
				});
				menulist += '</ul>';
				$("#wnav").accordion("add", {
					title : sm.menuName,
					content : menulist,
					iconCls : 'icon icon-sys'
				});
			});
			var pp = $("#wnav").accordion("panels");
			var t = pp[0].panel('options').title;
			$("#wnav").accordion("select", t);
			$("#wnav li a").bind("click", function() {
				
				var tabTitle = $(this).children('.nav').text();
				var menuId = $(this).attr("ref");
				var url = $(this).attr("rel");
				addTab(tabTitle, url);
				$("#wnav li div").removenClass("selected");
				$(this).parent().addClass("selected");
			});
			hoverMenuItem();
		}
	});
}

/**
 * 菜单项鼠标Hover
 */
function  hoverMenuItem(){
	$(".easyui-accordion").find("a").hover(function() {
		$(this).parent().addClass("hover");
	},function (){
		$(this).parent().removeClass("hover");
	});
}
function addTab(tabTitle, url) {
	if (!$("#tabs").tabs("exists", tabTitle)){
		
		$("#tabs").tabs('add', {
			title : tabTitle,
			closable : true,
			href:url
		});
	}
	else{
		$("#tabs").tabs("select",tabTitle);
	}
}

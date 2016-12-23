/**
 * 重置窗口的大小
 */
window.onresize=function (){
	
};
function changeMainFrame(){
	var hmain=document.getElementById("mainFrame");
	var bheight =document.documentElement.clientHeight;  // 获取整个网页的高度
	hmain.style.width='100%';
	hmain.style.height=(bheight-49)+'px';
}
changeMainFrame();

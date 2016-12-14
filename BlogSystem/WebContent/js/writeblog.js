$(document).ready(function(){
	
	//获取url上的id
    function GetQueryString(name)
    {
        var reg= new RegExp("(^|&)"+name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null){

            return  unescape(r[2]);
        }
        return null;
    }
   
    var userid = GetQueryString("userid");
    
    //给页面附上userid
    $("a.index").attr("href","index.html?userid="+userid);
    $("a.self").attr("href","selfindex.html?userid="+userid);
    
    $(".btn").click(function(){
        var blogtitle = $(".bt input").val();
        var tagname = $(".select option:checked").val();
        var blogcontent = $(".bc").val();
        if (blogtitle.length<1||blogtitle.length>50||tagname.length<1||blogcontent.length<1||blogcontent.length>5000) {
            alert("请按照正确格式编辑!");
            return false;
        }else{
            var json = {
                        blogtitle:blogtitle,
                        tagname:tagname,
                        blogcontent:blogcontent
                        };
            json = JSON.stringify(json)
            $.post('../AddBlogServlet',{data:json}, function(res) {
                if(res.state===201){
                    alert("发表成功!")
                }else if(state===202){
                    alert("发表失败,请重试!");
                }

            },"json");
        }
    })
    
});
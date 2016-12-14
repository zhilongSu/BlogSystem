$(document).ready(function(){
     
    $(".btn").click(function(){
        var u = document.getElementById("u");
        var p = document.getElementById("p");
        if (u.value.length !==8||p.value.length!==8) {
                    alert("请输入正确格式的信息,长度为0-8");
                    return false;
        }else{
        	var json = {
        				userid:u.value,
        				password:p.value
                    	};
        	var json  = JSON.stringify(json);
        	
        	$.post("../LoginServlet",{data:json},function(res){
        		if(res.state===201){
        			window.location.href = "index.html?userid="+res.userid;
        		}else if(res.state===202){
        			alert("登陆失败，请检查信息！")
        		}
        	},'json');
        }

        
    });
});
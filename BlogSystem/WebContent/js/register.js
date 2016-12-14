$(document).ready(function(){
    
    $(".btn").click(function(){
        var name = document.getElementById("username");
        var pwd = document.getElementById("password");
        var repwd = document.getElementById("password1");
        if (name.value.length>8||name.value.length<1||pwd.value.length!==8||repwd.value.length!==8) {
            alert("请填写正确的完整信息,长度为0-8");
            return false;
        }else  if (pwd.value != repwd.value) {
            alert("两次输入的密码不同");
            return false;
        }else{

            var json = {
                        username:name.value,
                        password:pwd.value
                        };
            json = JSON.stringify(json)
            $.post('../RegisterUserServlet',{data:json}, function(res) {
                if(res.state===201){
                    var isGo = confirm("请记住您的账号"+res.userid+",是否前往登陆?");
                    if(isGo){
                        window.location.href="login.html";
                    }else{
                        return;
                    }
                }else if(state===202){
                    alert("注册失败,请重试!");
                }

            },"json");
        }
    })
        
});
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
    $("a.write").attr("href","writeblog.html?userid="+userid);
    
  //退出按钮
	$("a.index-exit").click(function(){
		var isExit = confirm("您想退出？");
		
		if(isExit){
			window.location.href = "login.html";
			$.get("../ExitServlet",function(){	
			},'json');
		}
	});
	
	function switchTag(tagname){
		var tag;
		switch (tagname){
			case "新闻":tag="news";
						break;
			case "财经":tag = "money";
						break;
			case "科技":tag="science";
						break;
			case "体育":tag="sport";
						break;
			case "其他":tag="other";
						break;
			case "娱乐":tag="fun";
						break;
			case "生活":tag="life";
						break;
			case "搜索结果":tag="searchbox"
			default:break;
				
		}
		return tag;
	}
	
	//显示博文的函数
    function showBlog(blogtitle,blogid,author,tagname,blogcontent){
    	var tag = switchTag(tagname);
        var content = document.querySelector("#"+tag);
        var div  = document.createElement("div");
        div.className = "post";
        div.setAttribute("blogid",blogid);
        div.innerHTML = '<h2 class="title">'+blogtitle+'</h2>'+
        '<p class="meta"><span class="author-name">Posted by '+author+'</span>'+
        '&nbsp&nbsp<span>'+tagname+'</span>'+
        '<div class="entry"><p>'+blogcontent+'</p></div>'+
        '<div class="operate"><a href="watchblog.html?blogid='+blogid+'">查看</a></div>';
        content.appendChild(div);
    }
    
    //获取页面内容的函数
    function showPage(tagname,userid){
		var tag = switchTag(tagname);
        var json = {tagname:tagname,
        			userid:userid
        			};
        json = JSON.stringify(json);
        $.post("../CheckMyBlogServlet",{data:json},function(res){
        	if(res.state===201){
        		for(var i = 0;i<res.blogs.length;i++){
        			if(res.blogs[i]!==null){
        				showBlog(res.blogs[i].blogtitle,res.blogs[i].blogid,res.blogs[i].username,res.blogs[i].tagname,res.blogs[i].blogcontent);	
        			}else{
        				if(i<2){
         					$("#"+tag).height("400");
         				}
        				break;
        			}
        		}
        		
        	}else if(res.state===202){
        		$("div#"+tag).html("此分类暂无内容...");
        		$("div#"+tag).height("400");
        	}
        },'json');
        
    }
    
    showPage("新闻",userid);
    
    $("#sidebar ul li ul li").click(function(){	
    	var tn = switchTag($(this).html());
    	$("div#"+tn).children().remove();
    	showPage($(this).html(),userid); 
    	$(this).addClass("curr").siblings().removeClass("curr");
    	$("div#"+tn).show().siblings().hide();
    });
    
  //搜索框搜索博文
    $("#search-submit").click(function(){
    	var blogtitle = $("#search-text").val();
    	if(blogtitle.length<1){
    		alert("请输入博文标题");
    		return false;
    	}else{
    		 var json = {blogtitle:blogtitle};
    	     json = JSON.stringify(json);
    		$.post("../CheckBlogTitleServlet",{data:json},function(res){
    			if(res.state===201){
    				
    				$("div#searchbox").show().siblings().hide();
            		for(var i = 0;i<res.blogs.length;i++){
            			if(res.blogs[i]!==null){
            				showBlog(res.blogs[i].blogtitle,res.blogs[i].blogid,res.blogs[i].username,"搜索结果",res.blogs[i].blogcontent);
            				$("#sidebar ul li ul li").removeClass("curr");
            			}else{
            				if(i<2){
            					$("#searchbox").height("400");
            				}
            				break;
            			}
            		}
            		
            	}else if(res.state===202){
            		$("div#searchbox").height("400").html("无搜索结果...").show().siblings().hide();
            		$("#sidebar ul li ul li").removeClass("curr");
            	}
    		},'json');
    	}
    })
})
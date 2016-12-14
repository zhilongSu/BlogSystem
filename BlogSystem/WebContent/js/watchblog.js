$(document).ready(function(){
    //显示博文的函数
    function showBlog(blogtitle,blogid,author,tagname,blogcontent,ismine){
        var content = document.querySelector("#content");
        var div  = document.createElement("div");
        div.className = "post";
        div.setAttribute("blogid",blogid);
        div.innerHTML = '<h2 class="title">'+blogtitle+'</h2>'+
        '<p class="meta"><span class="author-name">Posted by '+author+'</span>'+
        '&nbsp&nbsp<span>'+tagname+'</span>'+
        '<div class="entry"><p>'+blogcontent+'</p></div>'+
        '<div class="operate">'+(ismine?'<a href="#1" class="delete-click">删除</a>':
        '<input type="text" placeholder="评论内容..." class="comment-input"><input type="submit" value="评论" class="comment-btn"/>')+'</div> ';
        $(div).prependTo($(content));
        return div;
    }
    
    function showComment(commentid,commenter,commentcontent,isCan){
    	var commentbox = document.getElementById("comment-box");
    	var comment = document.createElement("p");
    	comment.setAttribute("commentid", commentid);
    	comment.innerHTML = '<strong class="commenter">'+commenter+'</strong>:&nbsp<span class="commentcontetn">'+commentcontent+'</span>'+
    	(isCan?'<a href="#1" class="comment-del">删除</a>':'');
    	$(commentbox).prepend(comment);
    }

    //获取url上的blogid
    function GetQueryString(name)
    {
        var reg= new RegExp("(^|&)"+name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null){

            return  unescape(r[2]);
        }
        return null;
    }

    var blogid = GetQueryString("blogid");
    
    var json = {blogid:blogid}
    json = JSON.stringify(json)
    var userid = 0;
    
    $.post("../CheckBlogIdServlet",{data:json},function(res){
        if(res.state===201){
            var content = showBlog(res.blog.blogtitle,blogid,res.blog.username,res.blog.tagname,res.blog.blogcontent,res.ismine).parentNode;
            var ismine = res.ismine;
            $("a.index").attr("href","index.html?userid="+res.userid);
            $("a.self").attr("href","index.html?userid="+res.userid);
            userid = res.userid;
            
            $.post("../CheckCommentServlet",{data:json},function(res){
                if(res.state===201){
                	for(var i = 0;i<res.comments.length;i++){
            			if(res.comments[i]!==null){
            				showComment(res.comments[i].commentid,res.comments[i].username,res.comments[i].commentcontent,ismine);
            			}else{
            				break;
            			}
                	}          
                }
                if($(content).height()<400){
                	$(content).height("400");
                }
            },'json');
        }else{
        	$("#content").height("400");
        	alert("博文已被删除，请返回!");
        }
    },'json');
    //删除博文
    $("div#content").on("click",".delete-click",function(event){
    	var isDelete = confirm("确定删除此博文吗？");
    	var target = event.target;
    	var post = target.parentNode.parentNode;
    	var content = target.parentNode.parentNode.parentNode;
    	if(isDelete){
    		
    		$.post("../DeleteBlogServlet",{data:json},function(res){
    			if(res.state==201){
    				$(content).children().remove();
    			}else{
    				alert("删除失败");
    			}
    		},"json");
    	}
    });
    //删除博文
    $("div#comment-box").on("click",".comment-del",function(event){
    	var isDelete = confirm("确定删除此评论？");
    	var target = event.target;
    	var p = target.parentNode;
    	var commentbox = target.parentNode.parentNode;
    	var commentid = $(p).attr("commentid");
    	var json = {commentid:commentid};
        json = JSON.stringify(json);
    	
    	if(isDelete){
    		$.post("../DeleteCommentServlet",{data:json},function(res){
    			if(res.state==201){
    				commentbox.removeChild(p);
    			}else{
    				alert("删除失败");
    			}
    		},"json");
    	}
    });
    //发表评论
    $("#content").on("click",".comment-btn",function(event){
    	var commenttext = $(".comment-input").val();
    	if(commenttext.length<1){
    		return false;
    	}else{
    		var json = {blogid:blogid,
    					commentcontent:commenttext};
            json = JSON.stringify(json)
    		$.post("../AddCommentServlet",{data:json},function(res){
    			if(res.state===201){
    				showComment(0,res.username,commenttext,false);
    			}else{
    				alert("评论失败，请重试!");
    			}
    		},'json');
    	}
    });

});
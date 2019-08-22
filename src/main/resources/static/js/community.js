//发表回复
function post() {
    var questionId = $("#questionId").val();
    var comment_content = $("#comment_content").val()
    if (comment_content.length == 0) {
        alert("评论不能为空")
    } else {
        comment(questionId, 1, comment_content);
    }
}

//回复的回复
function callase(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-show");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-show");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-show", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/getComment/" + id, function (data) {
                $.each(data.content.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.login
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }


    }


function comment(targetId, type, content) {
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (responese) {
            if (responese.code == 2004) {
                // $("commentDiv").hide();
                window.location.reload();
            } else if (responese.code == 2003) {
                var result = confirm(responese.message);
                if (result == true) {
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.6e9bde8ffa4f09e4&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                    window.localStorage.setItem("closable", true);
                }
            }
            else {
                alert(responese.message);
            }
        },
        dataType: "json"
    })
}
//评论的回复
function reply(e) {
    var id = e.getAttribute("data-id");
    var reply = $("#reply-" + id);
    var value = reply[0].value;
    if(value.length == 0){
        alert("回复不能为空");
    }else{
        comment(id,2,value);
    }
    
}

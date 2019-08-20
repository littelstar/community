function post() {
    var questionId=$("#questionId").val();
    var comment_content=$("#comment_content").val()
    alert(questionId)
    $.ajax({
        type:"post",
        url:"/comment",
        contentType: 'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "content":comment_content,
            "type":1

        }),
        success:function () {
            console("评论成功")
        },
        dataType:"json"
    })
}
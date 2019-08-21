function post() {
    var questionId=$("#questionId").val();
    var comment_content=$("#comment_content").val()
    $.ajax({
        type:"post",
        url:"/comment",
        contentType: 'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "content":comment_content,
            "type":1
        }),
        success:function (responese) {
            if(responese.code == 2004){
                $("commentDiv").hide();
            } else if(responese.code == 2003){
                var result = confirm(responese.message);
                if(result == true){
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.6e9bde8ffa4f09e4&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                    window.localStorage.setItem("closable",true);
                }
            }
            else{
                alert(responese.message);
            }
        },
        dataType:"json"
    })
}
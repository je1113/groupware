window.onload = function(){
    const btn = document.getElementById('loginBtn');
    btn.addEventListener('click',send_it);
    const user_id = document.querySelector('#user_id');
    const user_pw = document.querySelector('#user_pw');
    user_id.addEventListener("keypress",function(event){
        if(event.key === "Enter"){
        event.preventDefault();
        send_it()
        }
    })
    user_pw.addEventListener("keypress",function(event){
        if(event.key === "Enter"){
        event.preventDefault();
        send_it()
        }
    })
}

function send_it(){
    const user_id = document.getElementById('user_id');
    const user_pw = document.getElementById('user_pw');

    if(user_id.value==''){
        alert('아이디를 입력하세요');
        user_id.focus()
        return false;
    }

    if(user_pw.value ==''){
        alert('비밀번호 입력하세요');
        user_pw.focus()
        return false;
    }
    document.getElementById('frm').submit();
}

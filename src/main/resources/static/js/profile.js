
const btn = document.querySelector('.edit-pw');
btn.addEventListener('click',send_it);
const pw = document.querySelector('.pw');
const pw_new1 = document.querySelector('.pw_new1');
const pw_new2 = document.querySelector('.pw_new2');
pw.addEventListener("keypress",function(event){
    if(event.key === "Enter"){
    event.preventDefault();
    send_it()
    }
})
pw_new1.addEventListener("keypress",function(event){
    if(event.key === "Enter"){
    event.preventDefault();
    send_it()
    }
})
pw_new1.addEventListener("keypress", function(event){
    if(event.key === "Enter"){
    event.preventDefault();
    send_it()
    }
})


function send_it(){
    const pw = document.querySelector('.pw');
    const pw_new1 = document.querySelector('.pw_new1');
    const pw_new2 = document.querySelector('.pw_new2');

    if (pw.value === '') {
        alert('기존 비밀번호를 입력하세요');
        pw.focus();
        return;
    }
    if (pw_new1.value === '') {
        alert('변경할 비밀번호를 입력하세요');
        pw_new1.focus();
        return;
    }
    if (pw_new2.value === '') {
        alert('비밀번호 확인을 입력하세요');
        pw_new2.focus();
        return;
    }

    if (pw_new1.value !== pw_new2.value) {
        alert('변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다');
        pw_new2.focus();
        return;
    }

    // 서버로 비밀번호 변경 요청 보내는 로직을 추가해야 합니다.
    fetch('api/member/change-pw', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            pw: pw.value,
            pwNew: pw_new1.value
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        if(data.resultCode=="ERROR"){
             alert(data.description);
        }else{
            alert("비밀번호 변경되었습니다.")
        }
         location.reload()
    })
    .catch(error => {
        console.error(error);
        alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요');
    });
}

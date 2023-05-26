document.querySelector('#submit').addEventListener('click',send_it)

function send_it(){
    const emailInput = document.getElementById('email');
    const email = emailInput.value;

    if(email===''){
        alert('이메일을 입력하세요.')
        emailInput.focus();
        return;
    }
    const data = { email: email };
    fetch('api/member/forgot-password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
      .then(response => response.json())
      .then(data => {
        if (data.resultCode=='OK') {
          alert('비밀번호 초기화 이메일이 전송되었습니다.');
          location.href='login'
        } else {
          alert('이메일을 찾을 수 없습니다.');
        }
      })
      .catch(error => {
        console.error('Error:', error);
      });
}
function pop_project_register(){
    document.querySelector(".layer_project_register").style.display = "block";
    const btn_save = document.querySelector('.btn_save');
    btn_save.addEventListener('click',send_create);
}
function close_project_register() {
    document.querySelector(".layer_project_register").style.display = "none";

}
function send_create() {
    //request로 필요한 DOM 객체 선택
    const name = document.getElementById('name_input');
    const startDate = document.getElementById('startDate_input');
    const endDate = document.getElementById('endDate_input');
    const input0 = document.getElementById('input0');

    fetch('/api/project', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            //우리가 만든데이터
            "transaction_time":`${new Date()}`,
            "resultCode":"ok",
            "description":"정상",
            "data":{
                "name":`${name.value}`,
                "startDate":`${startDate.value}`,
                "endDate":`${endDate.value}`,
                "isUse":`${input0.checked}`
            }
        }),
    })
        .then((res) => {
            alert('등록성공')
            location.reload();
            return; //리턴을 걸어서 진행하는 것을 막는다!
        })
        .then((data) => {
            console.log(data);
            return;
        })
        .catch((err)=>{
            alert(err);
        })
}
// 프로젝트 이름 정규 표현식
function validateName(strName){
    const reg_name = /^[0-9a-zA-Z가-힣_\s]+$/g;
    if(!reg_name.test(''+strName)){
        return false;
    }
    return true;
}

// 이름 유효성 검사
document.querySelector('#name_input').addEventListener('input', e=>{
    strName=e.target.value;
    let errorMsg='';
    if(!validateName(strName)){
        errorMsg='2글자 이상 입력해주세요';
        document.querySelector('#name_input_box').className='has_button input_box has_error';
        document.querySelector('#name_input').setAttribute('validateresult',false);
    } else {
        document.querySelector('#name_input_box').className='has_button input_box fill';
        document.querySelector('#name_input').setAttribute('validateresult',true);
    }
    document.querySelector('#name_input_error').innerHTML=errorMsg;
});

// 버튼 활성화

let strName
const startDate_input = document.querySelector('#startDate_input')
const endDate_input = document.querySelector('#endDate_input')
function btn_active(){
    if((validateName(strName))&&(startDate_input.value !='')&&(endDate_input.value !='')){
        $(".layer_project_register .btn_save").addClass("active");
        $(".layer_project_register .btn_save").removeClass("disabled")
    }else{
        $(".layer_project_register .btn_save").removeClass("active");
        $(".layer_project_register .btn_save").addClass("disabled")
    }
}
document.querySelector('#name_input').addEventListener('input', btn_active)
document.querySelector('#startDate_input').addEventListener('change', btn_active)
document.querySelector('#endDate_input').addEventListener('change', btn_active )



//edit 시작 ✅✅✅✅✅✅
let edit_idx; // 이벤트 이중으로 걸리지 안도록 익명함수를 회피하기 위함
function pop_project_edit(idx){
    edit_idx=idx;
    document.querySelector(".layer_project_edit").style.display = "block";
    //기존 정보를 가져오기 위해 idx를 통해 데이터를 불러온다.
    const edit_name = document.getElementById('name_edit_input');
    const edit_startDate = document.getElementById('startDate_edit_input');
    const edit_endDate = document.getElementById('endDate_edit_input');
    const edit_input = document.getElementById('edit_input');
    fetch('/api/project/'+idx)
        .then((response) => response.json())
        .then((data) => {
            edit_name.value=data.name;
            edit_startDate.value=data.startDate;
            edit_endDate.value=data.endDate;
            if(data.isUse){
                edit_input.checked = true
            }else{
                edit_input.checked = false
            }
        })
    const btn_edit = document.querySelector('.btn_edit');
    btn_edit.addEventListener('click',edit_event);
}
function edit_event(){
    send_edit(edit_idx)
}
function close_project_edit(){
    $(".layer_project_edit .btn_edit").removeClass("active");
    $(".layer_project_edit .btn_edit").addClass("disabled")
    document.querySelector(".layer_project_edit").style.display = "none";
}
function send_edit(idx) {
    //request로 필요한 DOM 객체 선택
    const edit_name = document.getElementById('name_edit_input');
    const edit_startDate = document.getElementById('startDate_edit_input');
    const edit_endDate = document.getElementById('endDate_edit_input');
    const edit_input = document.getElementById('edit_input');

    fetch('/api/project/'+idx, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            //우리가 만든데이터
            "transaction_time":`${new Date()}`,
            "resultCode":"ok",
            "description":"정상",
            "data":{
                "name" : `${edit_name.value}`,
                "startDate":`${edit_startDate.value}`,
                "endDate":`${edit_endDate.value}`,
                "isUse":`${edit_input.checked}`
            }
        }),
    })
        .then((res) => {
            alert('수정성공')
            location.reload();
            return;
        })
        .then((data) => {
            console.log(data);
            return;
        })
        .catch((err)=>{
            alert(err);
        })
}

let edit_strName
// 이름 유효성 검사
document.querySelector('#name_edit_input').addEventListener('input', e=>{
    edit_strName=e.target.value;
    let errorMsg='';
    if(!validateName(edit_strName)){
        errorMsg='2글자 이상 입력해주세요';
        document.querySelector('#name_edit_input_box').className='has_button input_box has_error';
        document.querySelector('#name_edit_input').setAttribute('validateresult',false);
    } else {
        document.querySelector('#name_edit_input_box').className='has_button input_box fill';
        document.querySelector('#name_edit_input').setAttribute('validateresult',true);
    }
    document.querySelector('#name_edit_input_error').innerHTML=errorMsg;
});

// 버튼 활성화


function edit_btn_active(){
    const edit_startDate = document.getElementById('startDate_edit_input');
    const edit_endDate = document.getElementById('endDate_edit_input');
    if((validateName(edit_strName))&&(edit_startDate.value !='')&&(edit_endDate.value !='')){
        $(".layer_project_edit .btn_edit").addClass("active");
        $(".layer_project_edit .btn_edit").removeClass("disabled")
    }else{
        $(".layer_project_edit .btn_edit").removeClass("active");
        $(".layer_project_edit .btn_edit").addClass("disabled")
    }
}
document.querySelector('#name_edit_input').addEventListener('input', edit_btn_active)
document.querySelector('#startDate_edit_input').addEventListener('change', edit_btn_active)
document.querySelector('#endDate_edit_input').addEventListener('change', edit_btn_active )


////관리자 조회👀👀👀👀👀👀👀👀
function pop_admin_view(idx){

    fetch('/api/project/'+idx)
        .then((response) => response.json())
        .then((data) => {
            console.log(data)
        })
    document.querySelector(".layer_admin_view").style.display = "block";
}
function close_admin_view(){
    document.querySelector(".layer_admin_view").style.display = "none";
}

///삭제 시작!❗❗❌❌❌❗❗❌❌❌
function pop_project_delete(idx){
    document.querySelector(".layer_project_delete").style.display = "block";
    const btn_delete = document.querySelector('.btn_delete');
    btn_delete.addEventListener('click',()=>{
        project_delete(idx)
    });
}
function project_delete(idx){
    fetch('/api/project/'+idx, {
        method: "DELETE",

    })
        .then((res) => {
            alert('삭제 완료')
            location.reload();
            return;
        })
        .then((data) => {
            console.log(data);
            return;
        })
        .catch((err)=>{
            alert(err);
        })
}
function close_admin_delete(){
    document.querySelector(".layer_project_delete").style.display = "none";
}

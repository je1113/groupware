function pop_work_register(){
    document.querySelector(".layer_date").style.display="block"
    document.querySelector(".btn_date").addEventListener("click",check_work)
}
function close_date(){
    document.querySelector(".layer_date").style.display="none"
}

//로그인 정보로 PIC를 통해 담당 프로젝트 정보 가져온 후
//해당 날짜에 공수가 등록되어 있는지 확인.
function check_work(){
    let year = document.querySelector(".layer_date .year select").value
    let month = document.querySelector(".layer_date .month select").value
    let week = document.querySelector(".layer_date .week select").value

    fetch('api/work/'+year+"/"+month+"/"+week)
        .then((res) => {
            console.log(res)
            res.json()
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
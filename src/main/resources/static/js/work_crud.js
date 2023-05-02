// 현재 날짜 생성
const today = new Date();
// 현재 년도 구하기
const currentYear = today.getFullYear();
// 현재 달 구하기 (0부터 시작하기 때문에 1을 더해줌)
const currentMonth = today.getMonth() + 1;
// 현재 주차 구하기
const currentWeek = Math.ceil(today.getDate() / 7);
let year = currentYear;
let month = currentMonth;
let week = currentWeek;

function pop_work_date(){
    document.querySelector(".layer_date").style.display="block"
    document.querySelector(".btn_date").addEventListener("click",check_work)

    document.querySelector(".layer_date .year ").value = year
    document.querySelector(".layer_date .month ").value = month
    document.querySelector(".layer_date .week ").value = week
}
function close_date(){
    document.querySelector(".layer_date").style.display="none"
}

//로그인 정보로 PIC를 통해 담당 프로젝트 정보 가져온 후
//해당 날짜에 공수가 등록되어 있는지 확인.
function check_work(){
    year = document.querySelector(".layer_date .year ").value
    month = document.querySelector(".layer_date .month ").value
    week = document.querySelector(".layer_date .week ").value

    fetch('api/work/'+year+"/"+month+"/"+week)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            console.log(data.length);
            if(data.length === 0){
                // 등록 모드
                close_date()
                pop_work_register()
            }else{
                alert("이미 등록된 기록이 있습니다. 수정으로 전환합니다.")
                // 수정 모드
                // 수정 레이어 띄우기
                    // data 내용 반복문으로채워 넣기
                    // 정규식 1을 넘으면 안된다! type은 빈값이 아니다.
                    // 버튼 리스터, 수정요청 개수만큼 보내기
            }
            return;
        })
        .catch((err)=>{
            alert(err);
        })
}


function pop_work_register(){
    document.querySelector(".layer_work_register").style.display="block"
    pic_list()

    document.querySelector(".btn_save").addEventListener("click",register_work)
}
function close_work_register(){
    document.querySelector(".layer_work_register").style.display="none"
}

async function pic_list(){
    let picList="";
    try{
        const response = await fetch('api/pic/member');
        const data = await response.json();
        console.log(data);
        if(data.length ===0){
            picList = `<p style="color:red; float: left;">아직 담당자가 등록되지 않았습니다.</p>`
            document.querySelector("#pic_list").innerHTML=picList
            return
        }
        data.forEach(pic=>{
            picList+=`
                <div class="input_box" style="display: flex; align-items: center;">
                    <h4 class="input_title" style=" margin-right: 10px; width: 100%;">${pic.projectName}</h4>
                    <input type="hidden" class="project_idx_input" value="${pic.projectIdx}">
                    <select class="cost_type_input custom-select">
                        <option value="0">판관비</option>
                        <option value="1">연구비</option>
                        <option value="2">제조원가</option>
                    </select>
                    <input type="number" autocomplete="off" placeholder="공수를 입력해주세요" class="form-control gong_soo_input" >
                </div>`
        })
        document.querySelector("#pic_list").innerHTML=picList


        document.querySelectorAll('.gong_soo_input').forEach(input =>{
            input.addEventListener('input', save_btn_active )
        })
    }catch(error){
        console.log(error)
    }
}

function save_btn_active(){
    const inputs = document.querySelectorAll('.gong_soo_input');
    total = 0
    let allInputsFilled = true;
    for (let i = 0; i < inputs.length; i++) {
        const inputVal = Number(inputs[i].value);
        // 0~1사이가 아닐경우 false
        if (inputVal <= 0 || inputVal > 1) {
           $(".layer_work_register .btn_save").removeClass("active");
           $(".layer_work_register .btn_save").addClass("disabled")
          return;
        }
        total += inputVal;
    }
    if (total <= 1 && allInputsFilled) {
        $(".layer_work_register .btn_save").addClass("active");
        $(".layer_work_register .btn_save").removeClass("disabled")
    } else {
         $(".layer_work_register .btn_save").removeClass("active");
         $(".layer_work_register .btn_save").addClass("disabled")
    }
}


function register_work(){
    const works = [];
    const inputs = document.querySelectorAll('.project_idx_input,.cost_type_input,.gong_soo_input');


    for(let i =0; i<inputs.length; i+=3){
        const project_idx = inputs[i].value;
        const cost_type = inputs[i+1].value;
        const gong_soo = inputs[i+2].value;

        const work = {
            "projectIdx": project_idx,
            "gongSoo": gong_soo,
            "costType":cost_type,
            "year":year,
            "month":month,
            "week":week
            };
        works.push(work)
    }
    fetch('/api/work', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(works)
      })
        .then(response => {
        location.reload()
      })
        .catch(error => {
        console.log(error)
      });

}
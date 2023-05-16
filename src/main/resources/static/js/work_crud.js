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
                alert("이미 등록된 기록이 있습니다.")
                close_date()
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
    const inputs = document.querySelectorAll('.layer_work_register .gong_soo_input');
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
/**
수정 시작!!
*/
function pop_work_edit(idx){
    document.querySelector(".layer_work_edit").style.display="block"
    work_list(idx)
    document.querySelector(".btn_edit").addEventListener("click",edit_work)
}
function close_work_edit(){
    document.querySelector(".layer_work_edit").style.display="none"
}

async function work_list(idx){
    let pic_edit_list="";
        try{
            const response = await fetch('api/work/week/'+idx);
            const data = await response.json();
            console.log(data);
            if(data.length ===0){
                pic_edit_list = `<p style="color:red; float: left;">데이터를 가져오는데 실패했습니다.</p>`
                document.querySelector("#pic_edit_list").innerHTML=pic_edit_list
                return
            }
            data.forEach(work =>{
                pic_edit_list+=`
                    <div class="input_box" style="display: flex; align-items: center;">
                        <h4 class="input_title" style=" margin-right: 10px; width: 100%;">${work.projectName}</h4>
                        <input type="hidden" class="work_idx_edit_input" value="${work.idx}">
                        <input type="hidden" class="project_idx_edit_input" value="${work.projectIdx}">
                        <select class="cost_type_edit_input custom-select">
                            <option value="0" ${work.costType == "판관비"? 'selected' :''}>판관비</option>
                            <option value="1" ${work.costType == "연구비"? 'selected' :''}>연구비</option>
                            <option value="2" ${work.costType == "제조원가"? 'selected' :''}>제조원가</option>
                        </select>
                        <input type="number" autocomplete="off" value="${work.gongSoo}" placeholder="공수를 입력해주세요" class="form-control gong_soo_edit_input" >
                    </div>`
            })
            document.querySelector("#pic_edit_list").innerHTML=pic_edit_list
            year =data[0].year
            month =data[0].month
            week =data[0].week

            document.querySelectorAll('.layer_work_edit .gong_soo_edit_input').forEach(input =>{
                input.addEventListener('input', edit_btn_active )
            })
        }catch(error){
            console.log(error)
        }
}


function edit_btn_active(){
    const inputs = document.querySelectorAll('.layer_work_edit .gong_soo_edit_input');
    total = 0
    let allInputsFilled = true;
    for (let i = 0; i < inputs.length; i++) {
        const inputVal = Number(inputs[i].value);
        // 0~1사이가 아닐경우 false
        if (inputVal <= 0 || inputVal > 1) {
           $(".layer_work_edit .btn_edit").removeClass("active");
           $(".layer_work_edit .btn_edit").addClass("disabled")
          return;
        }
        total += inputVal;
    }
    if (total <= 1 && allInputsFilled) {
        $(".layer_work_edit .btn_edit").addClass("active");
        $(".layer_work_edit .btn_edit").removeClass("disabled")
    } else {
         $(".layer_work_edit .btn_edit").removeClass("active");
         $(".layer_work_edit .btn_edit").addClass("disabled")
    }
}

function edit_work(){
    const works = [];
    const inputs = document.querySelectorAll('.work_idx_edit_input,.project_idx_edit_input,.cost_type_edit_input,.gong_soo_edit_input');
    console.log(year)
    console.log(inputs)
    for(let i =0; i<inputs.length; i+=4){
        const work_idx = inputs[i].value;
        const project_idx = inputs[i+1].value;
        const cost_type = inputs[i+2].value;
        const gong_soo = inputs[i+3].value;


        const work = {
            "idx": work_idx,
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
        method: 'PUT',
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
/**
삭제기능 시작!
*/
function pop_work_delete_list(idx){
    document.querySelector(".layer_work_delete_list").style.display="block"
    work_delete_list(idx)
    document.querySelector(".btn_delete_all").addEventListener("click",pop_work_delete)
}
function close_work_delete_list(){
    document.querySelector(".layer_work_delete_list").style.display="none"
}

async function work_delete_list(idx){
    let pic_delete_list="";
    try{
        const response = await fetch('api/work/week/'+idx);
        const data = await response.json();
        console.log(data);
        if(data.length ===0){
            pic_delete_list = `<p style="color:red; float: left;">데이터를 가져오는데 실패했습니다.</p>`
            document.querySelector("#pic_delete_list").innerHTML=pic_delete_list
            return
        }
        data.forEach(work =>{
            pic_delete_list+=`
                <div class="input_box" style="display: flex; align-items: center;">
                    <h4 class="input_title" style=" margin-right: 10px; width: 100%;">${work.projectName}</h4>
                    <input type="hidden" class="work_idx_delete_input" value="${work.idx}">
                    <div class="input_title" style=" margin-right: 10px; width: 100%;">${work.gongSo}(${work.costType})</div>
                    <div class="table-actions">
                        <a href="#" data-color="#e95959" onclick="work_delete(${work.idx})" style="color: rgb(233, 89, 89);">
                            <i class="icon-copy dw dw-delete-3"></i>
                        </a>
                    </div>
                </div><br>`
        })
        document.querySelector("#pic_delete_list").innerHTML=pic_delete_list


    }catch(error){
        console.log(error)
    }
}

function work_delete(idx){
    fetch('/api/work/'+idx, {
            method: "DELETE",

        })
        .then((res) => {
            console.log(res)
            location.reload()
            return;
        })
        .catch((err)=>{
            alert(err);
        })
}

function pop_work_delete(){
    document.querySelector(".layer_work_delete").style.display="block"
    document.querySelector(".btn_delete").addEventListener('click',work_all_delete)
}
function close_work_delete(){
    document.querySelector(".layer_work_delete").style.display="none"
}
function work_all_delete(){
    const works = [];
    document.querySelectorAll(".work_idx_delete_input").forEach(work =>{
        const work_dto = {
            "idx": work.value
        };
        works.push(work_dto)
    })
    fetch('/api/work', {
        method: "DELETE",
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(works)

    })
    .then((res) => {
        console.log(res)
        location.reload()
        return;
    })
    .catch((err)=>{
        alert(err);
    })
}

document.querySelector(".buttons-excel").addEventListener('click',downloadExcelFile)
function downloadExcelFile() {
  fetch("api/work/export", {
    method: "GET",
    responseType: "blob"
  })
    .then(response => {
      if (response.ok) {
        return response.blob();
      } else {
        throw new Error("API 요청이 실패했습니다.");
      }
    })
    .then(blob => {
      // 응답으로 받은 Blob 데이터를 파일로 변환하여 다운로드
      var url = window.URL.createObjectURL(blob);
      var link = document.createElement("a");
      link.href = url;
      link.download = "work_data.xls";
      link.click();
      window.URL.revokeObjectURL(url);
    })
    .catch(error => {
      // 오류 처리 로직
      console.error("다운로드 오류:", error);
    });
}
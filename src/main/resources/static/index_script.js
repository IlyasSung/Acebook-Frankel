
const inputBox = document.getElementsByClassName("main-input")[0];

const mainPostBtn = document.getElementsByClassName("main-btn")[0];

mainPostBtn.style.background = "buttonface";
mainPostBtn.disabled = 'true';


inputBox.addEventListener('input', output);

function output(e) {
    console.log(mainPostBtn);
    if (e.target.value == "") {
        mainPostBtn.style.background = "buttonface";
        mainPostBtn.disabled = 'true';
    }else {
        mainPostBtn.style.background = "buttonface";
        mainPostBtn.removeAttribute("disabled");
    }

}


const replybuttons = document.getElementsByClassName("reply-btn")
//[0].parentElement.parentElement.parentElement.parentElement.getElementsByClassName("reply")[0].style.display = 'flex';

for (let i =0; i < replybuttons.length;i++) {
    current_btn = replybuttons[i];
    current_btn.addEventListener("click", reply_btn);
}

function reply_btn(e) {
    e.currentTarget.parentElement.parentElement.parentElement.parentElement.getElementsByClassName("reply")[0].style.display = 'flex';
}

//console.log(replybuttons);
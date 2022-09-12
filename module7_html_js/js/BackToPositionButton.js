var to_top_button = document.getElementById("to-top-button");
var to_prev_pos_button = document.getElementById("to-prev-pos-button");
var prevPosY = 0, currPosY = 0;


function debounce(f, ms) {
  let isCooldown = false;
  return function() {
    if (isCooldown) return;
    f.apply(this, arguments);
    isCooldown = true;
    setTimeout(() => isCooldown = false, ms);
  };
}

let prevPos = debounce(setPrevPos, 1500);

window.onscroll = function() { 
    prevPos();
    scrollFunction(); 
};

function scrollFunction() {    
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    to_top_button.style.display = "block";
    to_prev_pos_button.style.display = "block";    
  } else {
    to_top_button.style.display = "none";
    to_prev_pos_button.style.display = "none";
  }
}

function setPrevPos() {
    prevPosY = currPosY;
    currPosY = window.pageYOffset || document.documentElement.scrollTop;
}


function goToTopFunc() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}


function goToPrevPosFunc() {
  document.body.scrollTop = prevPosY;
  document.documentElement.scrollTop = prevPosY;
}






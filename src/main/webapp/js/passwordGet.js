/**
 * Created by zhanghongwei on 18/02/2017.
 */
function checkPassword() {
    passwordValue = document.getElementsByTagName("input")[4].value


    var modes = 0;
    //正则表达式验证符合要求的
    if (passwordValue.length >6 & // length
    /\d/.test(passwordValue)&     //number
    /[a-z]/.test(passwordValue)&  // smaller case
    /[A-Z]/.test(passwordValue)&  // capital case
    /\W/.test(passwordValue)){    // special letters
        return true
    } else {
        alert("pleae double check your password!!");
        return false
    }
}

function CheckIntensity(pwd){
    var Mcolor,Wcolor,Scolor,Color_Html;
    var m=0;
    var Modes=0;
    for(i=0; i<pwd.length; i++){
        var charType=0;
        var t=pwd.charCodeAt(i);
        if(t>=48 && t <=57){charType=1;}
        else if(t>=65 && t <=90){charType=2;}
        else if(t>=97 && t <=122){charType=4;}
        else{charType=4;}
        Modes |= charType;
    }
    for(i=0;i<4;i++){
        if(Modes & 1){m++;}
        Modes>>>=1;
    }
    if(pwd.length<=4){m=1;}
    if(pwd.length<=0){m=0;}
    switch(m){
        case 1 :
            Wcolor="pwd pwd_Weak_c";
            Mcolor="pwd pwd_c";
            Scolor="pwd pwd_c pwd_c_r";
            Color_Html="弱";
            break;
        case 2 :
            Wcolor="pwd pwd_Medium_c";
            Mcolor="pwd pwd_Medium_c";
            Scolor="pwd pwd_c pwd_c_r";
            Color_Html="中";
            break;
        case 3 :
            Wcolor="pwd pwd_Strong_c";
            Mcolor="pwd pwd_Strong_c";
            Scolor="pwd pwd_Strong_c pwd_Strong_c_r";
            Color_Html="强";
            break;
        default :
            Wcolor="pwd pwd_c";
            Mcolor="pwd pwd_c pwd_f";
            Scolor="pwd pwd_c pwd_c_r";
            Color_Html="无";
            break;
    }
    document.getElementById('pwd_Weak').className=Wcolor;
    document.getElementById('pwd_Medium').className=Mcolor;
    document.getElementById('pwd_Strong').className=Scolor;
    document.getElementById('pwd_Medium').innerHTML=Color_Html;
}


//////////////////////////////////////////////////////////////////////
//密码检测密码强度
//////////////////////////////////////////////////////////////////////
function checkStrong(sValue) {
    var modes = 0;
    //正则表达式验证符合要求的
    if (sValue.length < 1) return modes;
    if (/\d/.test(sValue)) modes++; //数字
    if (/[a-z]/.test(sValue)) modes++; //小写
    if (/[A-Z]/.test(sValue)) modes++; //大写
    if (/\W/.test(sValue)) modes++; //特殊字符

    //逻辑处理
    switch (modes) {
        case 1:
            return 1;
            break;
        case 2:
            return 2;
        case 3:
        case 4:
            return sValue.length < 12 ? 3 : 4
            break;
    }
}

// <style type="text/css">
// .pwd{width:40px;height:20px;line-height:14px;padding-top:2px;}
// .pwd_f{color:#BBBBBB;}
// .pwd_c{background-color:#F3F3F3;border-top:1px solid #D0D0D0;border-bottom:1px solid #D0D0D0;border-left:1px solid #D0D0D0;}
// .pwd_Weak_c{background-color:#FF4545;border-top:1px solid #BB2B2B;border-bottom:1px solid #BB2B2B;border-left:1px solid #BB2B2B;}
// .pwd_Medium_c{background-color:#FFD35E;border-top:1px solid #E9AE10;border-bottom:1px solid #E9AE10;border-left:1px solid #E9AE10;}
// .pwd_Strong_c{background-color:#3ABB1C;border-top:1px solid #267A12;border-bottom:1px solid #267A12;border-left:1px solid #267A12;}
// .pwd_c_r{border-right:1px solid #D0D0D0;}
// .pwd_Weak_c_r{border-right:1px solid #BB2B2B;}
// .pwd_Medium_c_r{border-right:1px solid #E9AE10;}
// .pwd_Strong_c_r{border-right:1px solid #267A12;}
// </style>
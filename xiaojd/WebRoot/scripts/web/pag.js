    /*  window.alert = function(str)
         {
             var shield = document.createElement("DIV");
             shield.id = "shield";
             shield.style.position = "absolute";
             shield.style.left = "0px";
             shield.style.top = "0px";

             shield.style.width = "100%";
             shield.style.height = document.body.scrollHeight + "px";
             //弹出对话框时的背景颜色
             shield.style.background = "#fff";
             shield.style.textAlign = "center";
             shield.style.zIndex = "25";
             //背景透明 IE有效
             shield.style.filter = "alpha(opacity=0)";
             var alertFram = document.createElement("DIV");
             alertFram.id="alertFram";
             alertFram.style.position = "absolute";
             alertFram.style.left = "50%";
             alertFram.style.top = "50%";
             alertFram.style.marginLeft = "-225px";
             alertFram.style.marginTop = "-75px";
             alertFram.style.width = "250px";
             alertFram.style.height = "100px";
             alertFram.style.background = "#67b7b5;";
             alertFram.style.textAlign = "center";
             alertFram.style.lineHeight = "150px";
             alertFram.style.zIndex = "300";
             strHtml = "<ul style=\"list-style:none;margin:0px;padding:0px;width:100%\">\n";
             strHtml += " <li style=\"background:#67b7b5;;text-align:left;padding-left:20px;font-size:14px;height:25px;line-height:25px;border:1px solid #67b7b5;\">系统消息:</li>\n";
             strHtml += " <li style=\"background:#fff;text-align:center;font-size:12px;height:80px;line-height:80px;border-left:1px solid #67b7b5;border-right:1px solid #67b7b5;\">"+str+"</li>\n";
             strHtml += " <li style=\"background:#67b7b5;text-align:center;font-weight:bold;height:25px;line-height:25px; border:1px solid #67b7b5;\"><a href='#' onclick=\"doOk()\" />确定<a></li>\n";
             strHtml += "</ul>\n";
             alertFram.innerHTML = strHtml;
             document.body.appendChild(alertFram);
             document.body.appendChild(shield);
             this.doOk = function(){
                 alertFram.style.display = "none";
                 shield.style.display = "none";
             }
             alertFram.focus();
             document.body.onselectstart = function(){return false;};
         }*/
/**
 * 
 */
function addOptionInput() {
            var container = document.getElementById("options-container");
            var newInput = document.createElement("input");
            newInput.type = "text";
            newInput.name = "options";
            newInput.placeholder = "선택지 입력";
            container.appendChild(newInput);
        }
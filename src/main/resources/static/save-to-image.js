document.getElementById("exportChart").addEventListener("click", function() {

    var authorElement = document.createElement("div");
    authorElement.style.fontFamily = 'fantasy';
    authorElement.style.width = '';
    authorElement.style.height = '370px';
    authorElement.style.position = 'absolute';
    authorElement.style.display = 'flex';
    authorElement.style.fontSize = '5em';
    authorElement.style.opacity = '0.1';
    authorElement.style.zIndex = '999';
    authorElement.style.justifyContent = 'center';
    authorElement.style.alignItems = 'center';
    authorElement.style.textAlign = 'center';

    authorElement.appendChild(document.createTextNode("Александр Слотин\r\nИКБО-10-20"));

    let toImageElement = document.createElement("div");
    toImageElement.setAttribute("id", "to-image");
    toImageElement.style.height = '320px';

    let imgElement = document.createElement("img");
    imgElement.style.position = 'absolute';
    imgElement.src = document.getElementsByClassName("canvasjs-chart-canvas").item(0).toDataURL("image/jpg");

    toImageElement.appendChild(imgElement);
    toImageElement.appendChild(authorElement);

    document.body.appendChild(toImageElement);

    let toImageElement1 = document.createElement("div");
    toImageElement1.style.height = '320px';
    document.body.appendChild(toImageElement1);

    html2canvas(toImageElement).then(function(canvas) {
        var anchorTag = document.createElement("a");
        document.body.appendChild(anchorTag);
        anchorTag.download = "filename.jpg";
        anchorTag.href = canvas.toDataURL("image/jpg");
        anchorTag.target = '_blank';
        anchorTag.click();
    });

    toImageElement.remove();
});
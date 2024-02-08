/**
 * 
 */
function printPayDate()  {
  const name = document.getElementById('payDate');
  //console.log(name);
  document.getElementById("docRresultDate").innerText = name.value;
}
function printPayCate()  {
  const name = document.getElementById('payCate');
  console.log(name);
  document.getElementById("docRresultCate").innerText = name.value;
}
function printPayStartDate()  {
  const name = document.getElementById('payStartDate');
  //console.log(name);
  document.getElementById("docRresultStartDate").innerText = name.value;
}
function printPayEndDate()  {
  const name = document.getElementById('payEndDate');
  //console.log(name);
  document.getElementById("docRresultEndDate").innerText = name.value;
}
function printPayName()  {
  const name = document.getElementById('payName').value;
  document.getElementById("docRresultName").innerText = name;
  console.log(name)
}
function printPayContent()  {
  const name = document.getElementById('payContent').value;
  document.getElementById("docRresultContent").innerText = name;
}
function printPayTitle()  {
  const name = document.getElementById('payTitle').value;
  document.getElementById("docRresultTitle").innerText = name;
}
function openPreview(){
	document.getElementById('preview').style.display = "block";
}
function closePreview(){
	document.getElementById('preview').style.display = "none";
}
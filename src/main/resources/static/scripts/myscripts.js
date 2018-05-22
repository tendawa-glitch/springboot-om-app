function start() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("demo").innerHTML = this.responseText;
		}
	};
	var ip = location.host;
	xhttp.open("POST", "http://" + location.hostname + ":"+location.port+"/omweb-config/controller/start",
			true);
	xhttp.send();
	var x = document.getElementById("snackbar1")
	x.className = "snackbar show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);

}

function stop() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("demo").innerHTML = this.responseText;
		}
	};
	var url = "http://" + location.hostname + ":"+location.port+"/omweb-config/controller/stop";
	console.log("url ..." + url);
	xhttp.open("POST",url, true);
	xhttp.send();
	var x = document.getElementById("snackbar2")
	x.className = "snackbar show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);
}
function downloadLogs() {
	var form = document.createElement("form");
	form.method = "get";
	var ip = location.host;
	form.action = "http://" + location.hostname + ":"+location.port+"/omweb-config/controller/applicationLog";
	document.body.appendChild(form);
	form.submit();
	document.body.removeChild(form);
	var x = document.getElementById("snackbar3")
	x.className = "snackbar show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);
}
function nohubLogs() {
	var form = document.createElement("form");
	form.method = "get";
	var ip = location.host;
	form.action = "http://" + location.hostname + ":"+location.port+"/omweb-config/controller/nohupLog";
	document.body.appendChild(form);
	form.submit();
	document.body.removeChild(form);
	var x = document.getElementById("snackbar4")
	x.className = "snackbar show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);
}

function clearLogs() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("demo").innerHTML = this.responseText;
		}
	};
	var ip = location.host;
	xhttp.open("POST", "http://" + location.hostname + ":"+location.port+"/omweb-config/controller/clearLogs", true);
	xhttp.send();
	var x = document.getElementById("snackbar6")
	x.className = "snackbar show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);
}
function checkmemory() {
	var form = document.createElement("form");
	form.method = "get";
	var ip = location.host;
	form.action = "http://" + location.hostname + ":"+location.port+"/omweb-config/controller/freeMemory";
	document.body.appendChild(form);
	form.submit();
	document.body.removeChild(form);
	var x = document.getElementById("snackbar5")
	x.className = "snackbar show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);
}
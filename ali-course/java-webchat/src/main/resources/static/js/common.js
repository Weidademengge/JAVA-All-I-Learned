var ws = new WebSocket("ws://localhost:9875/ws");
	ws.onopen = function()
	{
	  console.log("连接成功");
	};
	ws.onmessage = function(evt)
	{
		var data = JSON.parse(evt.data);
		for(var i in protocol){
			if(protocol[i].code == data.code){
				protocol[i].handler(data);
			}
		}
	};
	ws.onclose = function(evt)
	{
	  console.log("WebSocketClosed!");
	};
	ws.onerror = function(evt)
	{
	  console.log("WebSocketError!");
	};

/** 发送消息 */
function send(code,params){
	var obj = new Object();
	obj.code = code;
	obj.params = params;
	var json = JSON.stringify(obj);
	ws.send(json);
}

/** 协议封装 */
function protocolHandler(code,handler){
	var obj = {
		code: code,
		handler: handler
	}
	return obj;
}

var joinRoom = function(data){
	$("#first").hide();
	$("#second").hide();
	$("#room").show();
	$("#roomCode").text(data.params.code);
}

var protocol = new Object();
//登录返回
protocol.login = protocolHandler(100,function(data){
	$("#first").hide();
	$("#second").show();
	$("#room").hide();
	$("#username").text(data.params.name);
	$("#user").text(data.params.name);
})
//发送消息到所有
protocol.msgAll = protocolHandler(102,function(data){
	$("#allTable").append("<tr class='user'>" +
			"<td style='text-align: right'>" + data.params.username + "：</td>" +
			"<td style='text-align: left'>" + data.params.msg + "</td></tr>");
})
//私聊
protocol.msgOne = protocolHandler(103,function(data){
	$("#oneTable").append("<tr class='user'>" +
			"<td style='text-align: right'>" + data.params.username + "：</td>" +
			"<td style='text-align: left'>" + data.params.msg + "</td></tr>");
})
//加入房间
protocol.joinRoomById = protocolHandler(104,joinRoom);
protocol.joinRoomRandom = protocolHandler(105,joinRoom);
protocol.createRoom = protocolHandler(106,joinRoom);
//离开房间
protocol.leaveRoom = protocolHandler(107,function(data){
	$("#first").hide();
	$("#second").show();
	$("#room").hide();
	$("#allTable").empty();
})
//用户加入
protocol.userJoinRoom = protocolHandler(-101,function(data){
	$("#allTable").append("<tr class='system-msg'>" +
			"<td style='text-align: right'>系统消息：</td>" +
			"<td style='text-align: left'><" + data.params.username + ">加入房间</td></tr>");
})
//用户离开
protocol.userLeaveRoom = protocolHandler(-102,function(data){
	$("#allTable").append("<tr class='system-msg'>" +
			"<td style='text-align: right'>系统消息：</td>" +
			"<td style='text-align: left'><" + data.params.username + ">离开房间</td></tr>");
})
//服务器主动推送
protocol.serverPush = protocolHandler(-200,function(data){
	
})
//错误信息
protocol.errorMsg = protocolHandler(-400,function(data){
	layer.msg(data.msg);
})







